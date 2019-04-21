package com.hh.nosql.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ZParams;
import redis.clients.jedis.exceptions.JedisDataException;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author HaoHao
 * @date 2018/7/17下午9:14
 */
public class RedisUtil {

    private static JedisPool jedisPool;

    private static Jedis jedis;

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(1024);
        jedisPool = new JedisPool("127.0.0.1", 6379);
        jedis = jedisPool.getResource();
    }

    public static Jedis getJedis() {
        return jedisPool.getResource();
    }

    public static void set(String key, String value) {
        jedis.set(key, value);
    }

    public static String get(String key) {
        return jedis.get(key);
    }

    public static void setList(String key, List<String> list) {
        jedis.lpush(key, list.get(0), list.get(1));
    }

    public static void remove(String key) {
        jedis.del(key);
    }

    //***************************** String start *********************************************8

    /**
     * String 自增1
     *
     * @param key
     */
    public static void incr(String key) {
        try {
            jedis.incr(key);
        } catch (JedisDataException e) {
            System.out.println("非数值类型");
            e.printStackTrace();
        }
    }

    public static void incrBy(String key, int i) {
        try {
            jedis.incrBy(key, i);
        } catch (JedisDataException e) {
            System.out.println("非数值类型");
            e.printStackTrace();
        }
    }

    public static void decr(String key) {
        try {
            jedis.decr(key);
        } catch (JedisDataException e) {
            System.out.println("非数值类型");
            e.printStackTrace();
        }
    }

    public static void decr(String key, int i) {
        try {
            jedis.decrBy(key, i);
        } catch (JedisDataException e) {
            System.out.println("非数值类型");
            e.printStackTrace();
        }
    }

    public static void watch() {
//        jedis.watch()
    }

    @Test
    public void getRange() {
        // 获取字符串的指定范围
        String v = jedis.getrange("k1", 0, 1);
        jedis.setrange("k1", 0, "000");
    }

    @Test
    public void setRange() {
        // 从offset 开始替换
        // 返回被修改后的字符串长度
        Long k1 = jedis.setrange("k1", 1, "2222");

        System.out.println(k1);
    }

    //***************************** String end *********************************************8


    //***************************** List start *********************************************8
    @Test
    public void lPush() {
        // 头部插入
        // 返回当前list size
        Long v = jedis.lpush("LKey", "v1", "v2");
        System.out.println(v);
    }

    @Test
    public void rPush() {
        // 返回当前list size
        Long v = jedis.rpush("LKey", "v3");
        System.out.println(v);
    }

    @Test
    public void lRange() {
        // 返回指定范围的列表
        List<String> list = jedis.lrange("LKey", 1, 2);
        System.out.println(list);
    }

    @Test
    public void lDelete() {
        String headV = jedis.lpop("LKey");
        String lastV = jedis.rpop("LKey");
    }

    //***************************** List end *********************************************8

    //***************************** Set Start *********************************************8
    @Test
    public void sAdd() {
        // 返回成功添加的个数
        Long size = jedis.sadd("SKey", "v2", "v3");
        System.out.println(size);
    }

    @Test
    public void sUnion() {
        // 并集
        Set<String> sunion = jedis.sunion("SKey", "Skey");
    }

    @Test
    public void sInter() {
        // 交集
        jedis.sinterstore("SKey", "Skey", "SKey2");
    }

    //***************************** Set end *********************************************8

    //***************************** Hash Start *********************************************8
    @Test
    public void hSet() {
        jedis.hset("user", "name", "Jack");
        jedis.hset("user", "age", "18");
        Map<String, String> user = jedis.hgetAll("user");
    }


    //***************************** Hash end *********************************************8


    //***************************** zset start *********************************************8
    @Test
    public void zSet() {
        jedis.zadd("zk", 1.0, "v1");
        jedis.zadd("zk", 1.2, "v2");
        jedis.zadd("zk", 1.1, "v3");
    }
    @Test
    public void zSet2() {
        jedis.zadd("zk1", 1.0, "v1");
        jedis.zadd("zk1", 1.2, "v2");
    }

    @Test
    public void inter(){
        ZParams zParams = new ZParams();
        // 设置取交集是 score 的比重
        zParams.weightsByDouble(0, 1);
        jedis.zinterstore("zk1", zParams,"zk", "zk1");

    }

    //***************************** zset end *********************************************8


    @Test
    public void zInterStore() {
//        jedis.zadd("key1", 1, "a");
//        jedis.zadd("key1", 2, "b");
//        jedis.zadd("key2", 3, "a");
//        jedis.zadd("key2", 4, "b");

        // key1 key2 取交集 score 相加
//        jedis.zinterstore("key2", "key1", "key2");

        ZParams zParams = new ZParams();
        // 取最大的score
//        zParams.aggregate(ZParams.Aggregate.MAX);
        // 取最小的score
//        zParams.aggregate(ZParams.Aggregate.MIN);
        // 取和(默认)
        zParams.aggregate(ZParams.Aggregate.SUM);
        // 设置key1 key2 的权重  计算分数时乘以权重
        zParams.weightsByDouble(1, 2);
        jedis.zinterstore("key2", zParams, "key1", "key2");

    }

    public static void main(String[] args) {
//        jedis.set("count", "0");
//        incr("test");
        System.out.println(jedis.get("count"));
    }
}
