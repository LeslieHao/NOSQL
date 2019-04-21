package com.hh.nosql.redis.semaphore;

import com.hh.nosql.redis.RedisUtil;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * @author HaoHao
 * @date 2018/10/11下午3:55
 */
public class TestDemo {

    private static final String KEY_TIMEOUT = "timeout_semaphore";

    private static final String KEY_COUNT = "count_semaphore";

    private static final String KEY_STRING = "key_string";

    // 一个商户+一个接口 对应一个zset 商户维度限流
    private static void testSemaphore() throws Exception {
        for (int i = 0; i < 1000; i++) {
            Jedis jedis = RedisUtil.getJedis();
            Semaphore.acquireSemaphore(jedis, KEY_TIMEOUT);
            Thread.sleep(100);
            jedis.close();
        }
    }


    @Test
    public void testFairSemaphore() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Jedis jedis = RedisUtil.getJedis();
            Semaphore.fairSemaphore(jedis, KEY_TIMEOUT, KEY_COUNT, KEY_STRING);
            jedis.close();
            Thread.sleep(10);
        }

    }

    public static void main(String[] args) throws Exception {
        testSemaphore();
    }
}
