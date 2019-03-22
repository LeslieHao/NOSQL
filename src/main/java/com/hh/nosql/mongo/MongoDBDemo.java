package com.hh.nosql.mongo;

import com.hh.nosql.vo.UserVO;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author HaoHao
 * @date 2019/3/21下午2:52
 */


public class MongoDBDemo {

    private static MongoClient mongoClient = new MongoClient("127.0.0.1", 27017);

    private MongoCollection<Document> userCollection;

    @Before
    public void before() {
        MongoDatabase db = mongoClient.getDatabase("mongo_demo");
        userCollection = db.getCollection("user");
    }


    @Test
    public void testInsert() {
        // 如果不存在collection 会在插入文档时候创建collection
        // bson 文档对象
        // 插入文档
        // 如果没有指定_id mongodb 会自动生成objectId 时间戳+机器号+进程号+随机数
        Document document = new Document("name", "张22").append("sex", "女").append("age", "28");
        userCollection.insertOne(document);
    }

    @Test
    public void insertMany() {
        //// 批量插入文档
        List<Document> list = new ArrayList<>();
        Document document1 = new Document("name", "张22").append("sex", "女").append("age", "28");
        Document document2 = new Document("name", "张22").append("sex", "女").append("age", "28");
        list.add(document1);
        list.add(document2);
        userCollection.insertMany(list);
    }

    @Test
    public void delete() {
        // 条件
        Bson filter = Filters.eq("name", "张22");
        userCollection.deleteOne(filter);
    }

    @Test
    public void deleteMany() {
        // 条件
        Bson filter = Filters.eq("name", "张22");
        userCollection.deleteMany(filter);
    }

    @Test
    public void update() {
        Bson filter = Filters.eq("name", "张11");
        Bson update = new Document("$set", new Document("sex", "男").append("aget", "18"));
        userCollection.updateOne(filter, update);
    }

    @Test
    public void updateMany() {
        Bson filter = Filters.eq("name", "张11");
        Bson update = new Document("$set", new Document("sex", "男").append("aget", "18"));
        userCollection.updateMany(filter, update);
    }


    @Test
    public void find() {
        // 查询所有
        FindIterable<Document> findIterable = userCollection.find();
        findIterable.forEach((Consumer<Document>) System.out::println);
    }

    @Test
    public void findOne(){
        // 条件查询
        Bson filter = Filters.eq("age", "28");
        FindIterable<Document> findIterable = userCollection.find(filter);
        findIterable.forEach((Consumer<Document>) System.out::println);
    }

    @Test
    public void findClass(){
        Bson filter = Filters.eq("age", "28");
        FindIterable<UserVO> userVOS = userCollection.find(filter, UserVO.class);
        userVOS.forEach((Consumer<UserVO>) System.out::println);
    }
}
