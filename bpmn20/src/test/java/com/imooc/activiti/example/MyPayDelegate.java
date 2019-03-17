package com.imooc.activiti.example;

import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Objects;

/**
 * @program: activiti6-sample
 * @description: test
 * @author: GilbertXiao
 * @create: 2019-03-02 22:40
 **/
public class MyPayDelegate implements JavaDelegate, Serializable {

    private static final Logger LOGGER= LoggerFactory.getLogger(MyPayDelegate.class);

    @Override
    public void execute(DelegateExecution delegateExecution) {
        LOGGER.info("run my java PayDelegate {}",this);
        LOGGER.info("variables={}",delegateExecution.getVariables());
        Object errorFlag = delegateExecution.getVariable("errorFlag");
        delegateExecution.getParent().setVariableLocal("key1","valuePar111111111111");
        delegateExecution.setVariable("key1","valueSub111111111111");
        delegateExecution.setVariable("key2","valueSub222222222222");
        if (Objects.equals(errorFlag,true)){
            throw new BpmnError("bpmnError");
        }



    }
}
