package org.seckill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by fx on 2017/11/22.
 */
public class RedisDao  {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final JedisPool jedisPool;

    public RedisDao(String ip,int port){
        jedisPool = new JedisPool(ip,port);
    }

    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

    public Seckill getSeckill(long seckillId){
        //redis操作逻辑
        try{
            Jedis jedis = jedisPool.getResource();
            try{
                String key = "seckill:"+seckillId;
                //bytes[]--->翻序列化----->对象
                //并没有实现序列化操作
                //采用自定义序列化， protostuff：pojo
                byte[] bytes = jedis.get(key.getBytes());
                //缓存中获取到
                if (bytes != null){
                    //空对象
                    Seckill seckill =schema.newMessage();
                    ProtobufIOUtil.mergeFrom(bytes,seckill,schema);//会把数据传到空对象里
                    //seckill 被反序列化
                    return seckill;
                }

            }finally {
                jedis.close();
            }

        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }
    public String putSeckill(Seckill seckill){
        //对象----》序列化-----》bytes[]
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:"+seckill.getSeckillId();
                byte[] bytes = ProtobufIOUtil.toByteArray(seckill,schema,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));//进行序列化
                //超时缓存
                int timeout = 60 * 60;
                String result = jedis.setex(key.getBytes(),timeout,bytes);//写入redis
                return result;
            } finally {
                jedis.close();
            }
        } catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }
}
