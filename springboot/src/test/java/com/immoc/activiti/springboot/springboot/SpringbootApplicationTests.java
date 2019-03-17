package com.immoc.activiti.springboot.springboot;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootApplicationTests {

    private static final Logger LOGGER= LoggerFactory.getLogger(SpringbootApplicationTests.class);
    @Autowired
    private RuntimeService runtimeService;
    @Test
    public void contextLoads() {
        ProcessInstance second_approve = runtimeService.startProcessInstanceByKey("second_approve");

        LOGGER.info("processId={}",second_approve);
    }

}
