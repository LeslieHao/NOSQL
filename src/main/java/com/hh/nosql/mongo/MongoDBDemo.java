package com.hh.nosql.mongo;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
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


    @Test
    public void test() throws InterruptedException {
        MongoDatabase db = mongoClient.getDatabase("mongo_demo");
        // 如果不存在collection 会在插入文档时候创建collection
        MongoCollection<Document> userCollection = db.getCollection("user");
        // bson 文档对象
        // 插入文档
        //userCollection.insertOne(document);
        //userCollection.insertOne(document);
        //// 批量插入文档
        //List<Document> list = new ArrayList<>();
        //Document document1 = new Document("name", "张22").append("sex", "女").append("age", "28");
        //Document document2 = new Document("name", "张22").append("sex", "女").append("age", "28");
        //list.add(document1);
        //list.add(document2);
        //userCollection.insertMany(list);

        // 删除匹配的单个文档
        Bson bson = Filters.eq("name", "张一一");
        //userCollection.deleteOne(bson);
        // 删除所有匹配文档
        //DeleteResult deleteResult = userCollection.deleteMany(bson);

        // 修改文档
        userCollection.updateMany(bson, new Document("$set", new Document("age", "100")));
        //userCollection.updateOne(bson, new Document());
        // 查询集合所有文档
        FindIterable<Document> findIterable = userCollection.find();

        // 过滤查询
        //FindIterable<Document> findIterable = userCollection.find(bson);
        findIterable.forEach(new Consumer<Document>() {
            @Override
            public void accept(Document document) {
                System.out.println(document);
            }
        });

    }

    @Test
    public void test2() {

    }
}
