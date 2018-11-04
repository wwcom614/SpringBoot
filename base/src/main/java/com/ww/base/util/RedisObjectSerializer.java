package com.ww.base.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.lang.Nullable;


public class RedisObjectSerializer implements RedisSerializer<Object> {

    //对象Object与数组byte[]的转换，首先应准备2个转换器
    private Converter<Object, byte[]> serializingConverter = new SerializingConverter();
    private Converter<byte[], Object> deSerializingConverter = new DeserializingConverter();
    //定义一个常量EMPTY_BYTE_ARRAY，如果Object转byte[]时，Object为null，转成byte[0]
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

    @Nullable
    @Override
    public byte[] serialize(@Nullable Object obj) throws SerializationException {
        if(obj == null){
            return EMPTY_BYTE_ARRAY;
        }else {
            return serializingConverter.convert(obj);
        }
    }

    @Nullable
    @Override
    public Object deserialize(@Nullable byte[] bytes) throws SerializationException {
        if(bytes == null || bytes.length == 0){
            return null;
        }else {
            return deSerializingConverter.convert(bytes);
        }
    }
}
