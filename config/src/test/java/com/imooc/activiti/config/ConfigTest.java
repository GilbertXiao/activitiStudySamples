package com.imooc.activiti.config;

import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigTest {
     private static final Logger logger =LoggerFactory.getLogger(ConfigTest.class);

    @Test
    public void testConfig1(){
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault();
        logger.info("configuration={}",configuration);

    }

}
