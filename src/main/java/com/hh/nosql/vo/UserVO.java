package com.hh.nosql.vo;

import com.alibaba.fastjson.JSON;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

/**
 * @author HaoHao
 * @date 2019/3/22下午4:21
 */

public class UserVO implements Codec<UserVO> {

    private ObjectId _id;

    private String name;

    private Integer age;

    private Integer sex;

    public UserVO() {
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }


    @Override
    public UserVO decode(BsonReader reader, DecoderContext decoderContext) {
        UserVO userVO = new UserVO();
        reader.readStartDocument();
        userVO.set_id(reader.readObjectId("_id"));
        userVO.setName(reader.readString("name"));
        userVO.setAge(reader.readInt32("age"));
        userVO.setSex(reader.readInt32("sex"));
        reader.readEndDocument();
        return userVO;
    }

    @Override
    public void encode(BsonWriter writer, UserVO value, EncoderContext encoderContext) {
        writer.writeStartDocument();
        writer.writeString("name", value.getName());
        writer.writeInt32("age",value.getAge());
        writer.writeInt32("sex", value.getSex());
        writer.writeEndDocument();
    }

    @Override
    public Class<UserVO> getEncoderClass() {
        return UserVO.class;
    }

}
