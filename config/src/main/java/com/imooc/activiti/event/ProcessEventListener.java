package com.imooc.activiti.event;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @program: activiti6-sample
 * @description: 流程event
 * @author: GilbertXiao
 * @create: 2019-01-02 22:18
 **/
public class ProcessEventListener implements ActivitiEventListener {
    
    private static final Logger LOGGER= LoggerFactory.getLogger(ProcessEventListener.class);
    
    @Override
    public void onEvent(ActivitiEvent activitiEvent) {
        ActivitiEventType type = activitiEvent.getType();
        if(ActivitiEventType.PROCESS_STARTED.equals(type)){
            LOGGER.info("流程启动{} \t {}",type,activitiEvent.getProcessInstanceId());
        }else if(ActivitiEventType.PROCESS_COMPLETED.equals(type)){
            LOGGER.info("流程结束{} \t {}",type,activitiEvent.getProcessInstanceId());
        }
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }
}
