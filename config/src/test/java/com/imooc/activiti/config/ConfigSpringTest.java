package com.imooc.activiti.config;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @program: activiti6-sample
 * @description: MDCTest
 * @author: GilbertXiao
 * @create: 2018-12-27 23:45
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:activiti-context.xml"})
public class ConfigSpringTest {

    @Rule
    @Autowired
    public ActivitiRule activitiRule;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Test
    @Deployment(resources = {"com/imooc/activiti/my-process.bpmn20.xml"})
    public void test() {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("my-process");
        assertNotNull(processInstance);

        Task task = taskService.createTaskQuery().singleResult();
        assertEquals("Activiti is awesome!", task.getName());
        taskService.complete(task.getId());

    }

}
