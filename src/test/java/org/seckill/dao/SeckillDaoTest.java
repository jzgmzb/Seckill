package org.seckill.dao;

import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by fx on 2017/11/15.
 * spring与junit整合，junit启动时加载springIOC容器
 * spring-test,junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath*:spring/spring-dao.xml"})

public class SeckillDaoTest {


    //注入依赖
    @Resource
    private SeckillDao seckillDao;

    @org.junit.Test
    public void testQueryById() throws Exception {
        long id = 1000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
    }

    @org.junit.Test
    public void testQueryAll() throws Exception {
        //java没有保存形参的记录，相当于queryALl(arg0,arg1),两个参数一起mybatis分辨不出来
        List<Seckill> seckillList = seckillDao.queryAll(0,100);
        for (Seckill seckill:seckillList
             ) {
            System.out.println(seckill);
        }

    }

    @org.junit.Test
    public void testReduceNumber() throws Exception {
        Date killTime = new Date();
        int updateCount = seckillDao.reduceNumber(1000,killTime);
        System.out.println(updateCount);
    }
}