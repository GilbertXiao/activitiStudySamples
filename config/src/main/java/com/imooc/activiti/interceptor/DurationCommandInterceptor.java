package com.imooc.activiti.interceptor;

import org.activiti.engine.impl.interceptor.AbstractCommandInterceptor;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: activiti6-sample
 * @description: 执行时间
 * @author: GilbertXiao
 * @create: 2019-01-04 22:19
 **/
public class DurationCommandInterceptor extends AbstractCommandInterceptor {

    private static final Logger LOGGER= LoggerFactory.getLogger(DurationCommandInterceptor.class);
    @Override
    public <T> T execute(CommandConfig commandConfig, Command<T> command) {
        long startTime = System.currentTimeMillis();
        try {
           return this.getNext().execute(commandConfig,command);
        } finally {
            long duration=System.currentTimeMillis()-startTime;
            LOGGER.info("{}执行时间{}毫秒",command.getClass().getSimpleName(),duration);
        }


    }
}
