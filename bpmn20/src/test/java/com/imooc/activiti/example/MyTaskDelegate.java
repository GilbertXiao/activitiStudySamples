package com.imooc.activiti.example;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * @program: activiti6-sample
 * @description: test
 * @author: GilbertXiao
 * @create: 2019-03-02 22:40
 **/
public class MyTaskDelegate implements JavaDelegate, Serializable {

    private static final Logger LOGGER= LoggerFactory.getLogger(MyTaskDelegate.class);

    @Override
    public void execute(DelegateExecution delegateExecution) {
        LOGGER.info("run my java TaskDelegate {}",this);
    }
}
