package com.imooc.activiti.config;

import org.activiti.engine.event.EventLogEntry;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @program: activiti6-sample
 * @description: MDCTest
 * @author: GilbertXiao
 * @create: 2018-12-27 23:45
 **/
public class ConfigEventTest {

    private static final Logger LOGGER= LoggerFactory.getLogger(ConfigEventTest.class);
    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti_eventlog.cfg.xml");

    @Test
    @Deployment(resources = {"com/imooc/activiti/my-process.bpmn20.xml"})
    public void test() {
        ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("my-process");
        assertNotNull(processInstance);

        Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
        assertEquals("Activiti is awesome!", task.getName());
        activitiRule.getTaskService().complete(task.getId());

        List<EventLogEntry> eventLogEntries = activitiRule.getManagementService()
                .getEventLogEntriesByProcessInstanceId(processInstance.getProcessInstanceId());

        for (EventLogEntry eventLogEntry : eventLogEntries) {

            LOGGER.info("eventLog.type ={},eventLLog.data={}",eventLogEntry.getType(),new String(eventLogEntry.getData()));


        }
        LOGGER.info("eventLogEntries.size={}",eventLogEntries.size());
    }

}
