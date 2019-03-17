package com.imooc.activiti.bpmn20;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @program: activiti6-sample
 * @description: test
 * @author: GilbertXiao
 * @create: 2019-02-15 22:45
 **/
public class TimerEventTest {
    private static final Logger LOGGER= LoggerFactory.getLogger(TimerEventTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    @Test
    @Deployment(resources = {"my-process-timer-boundary.bpmn20.xml"})
    public void testTimerBoundary() throws InterruptedException {

        ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("my-process");
        List<Task> tasks = activitiRule.getTaskService().createTaskQuery().listPage(0, 100);
        for (Task task : tasks) {
            LOGGER.info("task.name={}",task.getName());
        }

        LOGGER.info("tasks.size={}",tasks.size());

        Thread.sleep(1000*15);

        List<Task> secondTasks = activitiRule.getTaskService().createTaskQuery().listPage(0, 100);
        for (Task task : secondTasks) {
            LOGGER.info("task.name={}",task.getName());
        }
        LOGGER.info("tasks.size={}",secondTasks.size());

    }

}
