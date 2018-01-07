-- 秒杀执行存储过程
-- ;
DELIMITER $$ --  ; 用于代表存储过程的结束
-- 参数 in 输入参数 out 输出参数
-- row-count 表示上一条修改类型语句的影响行数
-- row-count = 0：未修改行数；>0 表示修改的行数 <0:sql错误
-- 定义存储过程
CREATE PROCEDURE execute_seckill
  (in v_seckill_id int,in v_phone int,
    in v_kill_time timestamp,out r_result int)
  BEGIN
    DECLARE insert_count int DEFAULT 0;
    START TRANSACTION ;
    INSERT IGNORE into success_killed
    (seckill_id,user_phone,state)
      VALUES (v_seckill_id,v_phone,0);
    SELECT row_count() INTO insert_count;
    IF (insert_count = 0) THEN
      ROLLBACK ;
      SET r_result = -1;
    ELSEIF (insert_count<0) THEN
      ROLLBACK ;
      SET r_result = -2;
    ELSE
      UPDATE seckill
      SET number = number - 1
      where seckill_id = v_seckill_id
      AND end_time > v_kill_time
      AND start_time < v_kill_time
      AND number > 0;
      SELECT row_count() INTO insert_count;
      IF (insert_count = 0) THEN
        ROLLBACK ;
        SET r_result = 0;
      ELSEIF (insert_count < 0) THEN
        ROLLBACK ;
        SET r_result = -2;
      ELSE
        COMMIT ;
        SET r_result = 1;
      END IF ;
    END IF;
  END ;
$$

DELIMITER ;
-- 控制台执行存储过程
set @r_result=-3;
-- 执行存储过程
call execute_seckill(1003,12374863542,now(),@r_result);
-- 获取结果
select @r_result;