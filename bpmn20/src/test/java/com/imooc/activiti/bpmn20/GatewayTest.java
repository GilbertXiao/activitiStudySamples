package com.imooc.activiti.bpmn20;

import com.google.common.collect.Maps;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: activiti6-sample
 * @description: Test
 * @author: GilbertXiao
 * @create: 2019-02-28 22:06
 **/
public class GatewayTest {

    private static final Logger LOGGER= LoggerFactory.getLogger(GatewayTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    @Test
    @Deployment(resources = {"my-process-exclusiveGateway1.bpmn20.xml"})
    public void testExclusiveGateTask3(){
        Map<String, Object> variables = Maps.newHashMap();
        variables.put("score",3);

        ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("my-process",variables);

        Task task = activitiRule.getTaskService().createTaskQuery().singleResult();

        LOGGER.info("task.name={}",task.getName());

    }

    @Test
    @Deployment(resources = {"my-process-parallelGateway1.bpmn20.xml"})
    public void testExclusiveGateTask4(){

        ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("my-process");

        List<Task> tasks = activitiRule.getTaskService().createTaskQuery().processInstanceId(processInstance.getId()).listPage(0, 100);

        for (Task task : tasks) {
            LOGGER.info("task.name={}",task.getName());
            activitiRule.getTaskService().complete(task.getId());
        }

        LOGGER.info("task.size={}",tasks.size());
        Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
        LOGGER.info("task.name={}",task.getName());
    }

}
