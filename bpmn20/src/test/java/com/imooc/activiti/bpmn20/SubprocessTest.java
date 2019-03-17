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

import java.util.List;
import java.util.Map;

/**
 * @program: activiti6-sample
 * @description: Test
 * @author: GilbertXiao
 * @create: 2019-02-28 22:06
 **/
public class SubprocessTest {

    private static final Logger LOGGER= LoggerFactory.getLogger(SubprocessTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    @Test
    @Deployment(resources = {"my-process-subprocess1.bpmn20.xml"})
    public void testSubProcess(){
        Map<String, Object> variables = Maps.newHashMap();


        ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("my-process");

        Task task = activitiRule.getTaskService().createTaskQuery().singleResult();

        LOGGER.info("task.name={}",task.getName());

    }

    @Test
    @Deployment(resources = {"my-process-subprocess1.bpmn20.xml"})
    public void testSubProcess2(){
        Map<String, Object> variables = Maps.newHashMap();
        variables.put("errorFlag",true);

        ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("my-process",variables);

        Task task = activitiRule.getTaskService().createTaskQuery().singleResult();

        LOGGER.info("task.name={}",task.getName());

        Map<String, Object> variables1 = activitiRule.getRuntimeService().getVariables(processInstance.getId());

        for (String s : variables1.keySet()) {
            LOGGER.info("KEY {}, VALUE {}",s,variables1.get(s));
        }

    }

    @Test
    @Deployment(resources = {"my-process-subprocess2.bpmn20.xml"})
    public void testSubProcess3(){
        Map<String, Object> variables = Maps.newHashMap();
        variables.put("errorFlag",true);
        variables.put("key","value");

        ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("my-process",variables);

        Task task = activitiRule.getTaskService().createTaskQuery().singleResult();

        LOGGER.info("task.name={}",task.getName());

        Map<String, Object> variables1 = activitiRule.getRuntimeService().getVariables(processInstance.getId());

        for (String s : variables1.keySet()) {
            LOGGER.info("KEY {}, VALUE {}",s,variables1.get(s));
        }
    }

    @Test
    @Deployment(resources = {"my-process-subprocess3.bpmn20.xml","my-process-subprocess4.bpmn20.xml"})
    public void testSubProcess4(){
        Map<String, Object> variables = Maps.newHashMap();
        variables.put("errorFlag",true);
        variables.put("key000","value0000");

        ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("my-process",variables);

        Task task = activitiRule.getTaskService().createTaskQuery().singleResult();

        LOGGER.info("task.name={}",task.getName());

        Map<String, Object> variables1 = activitiRule.getRuntimeService().getVariables(processInstance.getId());

        for (String s : variables1.keySet()) {
            LOGGER.info("KEY {}, VALUE {}",s,variables1.get(s));
        }
    }




}
