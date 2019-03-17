package com.imooc.activiti.config;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @program: activiti6-sample
 * @description: MDCTest
 * @author: GilbertXiao
 * @create: 2018-12-27 23:45
 **/
public class ConfigMDCTest {

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti_MDC.cfg.xml");

    @Test
    @Deployment(resources = {"com/imooc/activiti/my-process.bpmn20.xml"})
    public void test() {
        ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("my-process");
        assertNotNull(processInstance);

        Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
        assertEquals("Activiti is awesome!", task.getName());
        activitiRule.getTaskService().complete(task.getId());

    }

}
