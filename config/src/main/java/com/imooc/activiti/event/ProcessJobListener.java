package com.imooc.activiti.event;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: activiti6-sample
 * @description: 流程event
 * @author: GilbertXiao
 * @create: 2019-01-02 22:18
 **/
public class ProcessJobListener implements ActivitiEventListener {
    
    private static final Logger LOGGER= LoggerFactory.getLogger(ProcessJobListener.class);
    
    @Override
    public void onEvent(ActivitiEvent activitiEvent) {
        ActivitiEventType type = activitiEvent.getType();
        String name = type.name();

        if(name.startsWith("TIMER")||name.startsWith("JOB")){
            LOGGER.info("监听到的自定义事件{}\t{}",type,activitiEvent.getProcessInstanceId());
        }
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }
}
