package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exporse;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by fx on 2017/11/18.
 */

@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"
                        ,"classpath:spring/spring-service.xml"})
public class SeckillServiceTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void testGetSeckillList() throws Exception {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list={}",list);
    }

    @Test
    public void testGetById() throws Exception {
        long id = 1000;
        Seckill seckill = seckillService.getById(id);
        logger.info("seckill={}",seckill);
    }

    //集成测试的完整性
    /**@Test
    public void testExportSeckillUrl() throws Exception {
        long id = 1000;
        Exporse exposer = seckillService.exportSeckillUrl(id);
        if(exposer.isExposed()){
            logger.info("exposer={}",exposer);
            try {
                long userPhone = 1377834348783l;
                String md5 = exposer.getMd5();
                SeckillExecution seckillExecution = seckillService.executeSeckill(id,userPhone,md5);
                logger.info("seckillExecution={}",seckillExecution);
            } catch (RepeatKillException e){
                logger.error(e.getMessage());
            } catch (SeckillCloseException e1){
                logger.error(e1.getMessage());
            }
        } else{
            logger.warn("exposer={}",exposer);
        }
    }

    @Test
    public void testExecuteSeckill() throws Exception {
        long id = 1000;
        long userPhone = 13778348783l;
        String md5 = "e1161847d3a8cc95d568ce96c7397157";
        SeckillExecution seckillExecution = seckillService.executeSeckill(id,userPhone,md5);
        logger.info("seckillExecution={}",seckillExecution);
    }
     */

    @Test
    public void executeSeckillProcedure(){
        long seckillId = 1001;
        long phone = 1223343;
        Exporse exporse = seckillService.exportSeckillUrl(seckillId);
        if (exporse.isExposed()){
            String md5 = exporse.getMd5();
            SeckillExecution execution =  seckillService.executeSeckillProcedure(seckillId,phone,md5);
            logger.info(execution.getStateInfo());
        }
    }
}