package com.example.shiro.shiro.session;

import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.crazycake.shiro.SerializeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class RedisSessionDao extends EnterpriseCacheSessionDAO {
    // Session超时时间，单位为毫秒
    @Value("${shiro.session.expireTime}")
    private long expireTime;
    @Autowired
    RedisTemplate redisTemplate;
   public volatile Map<Serializable, java.lang.String> map=new HashedMap();
    public RedisSessionDao(){
        super();
    }
    public RedisSessionDao(long expireTime, RedisTemplate redisTemplate) {
        super();
        this.expireTime = expireTime;
        this.redisTemplate = redisTemplate;
    }

    //创建session
    @Override
    protected Serializable doCreate(Session session) {
        System.out.println("===============doCreate================");
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        System.out.println("----------------id"+sessionId+"--Session"+ SerializeUtils.serialize(session) +"-----------------");
        map.put(sessionId,SerializableUtils.serialize(session));
        //redisTemplate.opsForValue().set(session.getId(), session, expireTime, TimeUnit.MILLISECONDS);
        return sessionId;

    }
    //读取session
    @Override
    protected Session doReadSession(Serializable sessionId) {
        System.out.println("==============doReadSession=================");
      /*  if (sessionId == null) {
            return null;
        }*/
        String s = map.get(sessionId);
        if (s==null||s.isEmpty()){
            return null;
        }
        System.out.println("---------"+sessionId+"-------------");
        return SerializableUtils.deserialize(s);
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        System.out.println("===============update================");
        if (session == null || session.getId() == null) {
            return;
        }
        session.setTimeout(expireTime);
     //   redisTemplate.opsForValue().set(session.getId(), session, expireTime, TimeUnit.MILLISECONDS);
        System.out.println("----------"+session.getId()+"------------");
        map.put(session.getId(),SerializableUtils.serialize(session));

    }

    @Override
    public void delete(Session session) {
        System.out.println("===============delete================");
        if (null == session) {
            return;
        }
        map.remove(session.getId());
      //  redisTemplate.opsForValue().getOperations().delete(session.getId());

    }
// 获取活跃的session，可以用来统计在线人数，如果要实现这个功能，
// 可以在将session加入redis时指定一个session前缀，
// 统计的时候则使用keys("session-prefix*")的方式来模糊查找redis中所有的session集合
    @Override
    public Collection<Session> getActiveSessions() {
        System.out.println("==============getActiveSessions=================");
    //   return redisTemplate.keys("*");



        return  new ArrayList<>();
    }
    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;

    }

}
