package com.ww.base.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.lang.Nullable;

import java.nio.charset.Charset;

//自定义一个Redis通用序列化方式的类
public class FastJsonRedisSerializer<t> implements RedisSerializer<t> {

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private Class<t> clazz;

    public FastJsonRedisSerializer(Class<t> clazz) {
        super();
        this.clazz = clazz;
    }

    @Nullable
    @Override
    public byte[] serialize(@Nullable t t) throws SerializationException {
        if (null == t) {
            return new byte[0];
        }
        return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }

    @Override
    public t deserialize(byte[] bytes) throws SerializationException {
        if (null == bytes || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);
        return (t) JSON.parseObject(str, clazz);
    }
}
