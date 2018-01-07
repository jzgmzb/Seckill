package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.seckill.entity.Successkilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by fx on 2017/11/15.
 */

@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath*:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {

    @Resource
    private SuccessKilledDao successKilledDao;
    @Test
    public void testInsertSuccessKilled() throws Exception {
        long id = 1000;
        long phone = 1358803435;
        int insertNumber = successKilledDao.insertSuccessKilled(id,phone);
        System.out.println(insertNumber);
    }

    @Test
    public void testQueryByIdWithSeckill() throws Exception {
        long id = 1000;
        long phone = 1358803435;
        Successkilled successKilled = successKilledDao.queryByIdWithSeckill(id,phone);
        System.out.println(successKilled);
    }
}