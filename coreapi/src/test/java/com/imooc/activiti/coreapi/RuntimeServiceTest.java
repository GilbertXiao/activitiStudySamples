package com.imooc.activiti.coreapi;

import com.google.common.collect.Maps;
import com.sun.javafx.collections.MappingChange;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceBuilder;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @program: activiti6-sample
 * @description: test
 * @author: GilbertXiao
 * @create: 2019-01-09 21:27
 **/
public class RuntimeServiceTest {

    private static final Logger LOGGER= LoggerFactory.getLogger(RuntimeServiceTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    @Test
    @org.activiti.engine.test.Deployment(resources = {"my-process.bpmn20.xml"})
    public void testStartProcess(){
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        Map<String,Object> variables= Maps.newHashMap();
        variables.put("key1","value1");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("my-process", variables);
        LOGGER.info("processInstance={}",processInstance);
    }

    @Test
    @org.activiti.engine.test.Deployment(resources = {"my-process.bpmn20.xml"})
    public void testStartProcessById(){
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        ProcessDefinition processDefinition = activitiRule.getRepositoryService().createProcessDefinitionQuery().singleResult();

        Map<String,Object> variables= Maps.newHashMap();
        variables.put("key1","value1");
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), variables);
        LOGGER.info("processInstance={}",processInstance);
    }

    @Test
    @org.activiti.engine.test.Deployment(resources = {"my-process.bpmn20.xml"})
    public void testStartProcessBuilder(){
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        ProcessDefinition processDefinition = activitiRule.getRepositoryService().createProcessDefinitionQuery().singleResult();

        Map<String,Object> variables= Maps.newHashMap();
        variables.put("key1","value1");
        ProcessInstanceBuilder processInstanceBuilder = runtimeService.createProcessInstanceBuilder();
        ProcessInstance start = processInstanceBuilder.businessKey("buildkey001").processDefinitionKey("my-process").variables(variables).start();
        // ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), variables);
        LOGGER.info("processInstance={}",start);
    }

    @Test
    @org.activiti.engine.test.Deployment(resources = {"my-process.bpmn20.xml"})
    public void testVariables(){
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        Map<String,Object> variables= Maps.newHashMap();
        variables.put("key1","value1");
        variables.put("key2","value2");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("my-process", variables);
        LOGGER.info("processInstance={}",processInstance);
        runtimeService.setVariable(processInstance.getProcessInstanceId(),"key3","value3");
        runtimeService.setVariable(processInstance.getProcessInstanceId(),"key2","value--2");
        Map<String, Object> variables1 = runtimeService.getVariables(processInstance.getId());
        LOGGER.info("varibles={}",variables1);


    }

    @Test
    @org.activiti.engine.test.Deployment(resources = {"my-process.bpmn20.xml"})
    public void testProcessInstanceQuery(){
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        Map<String,Object> variables= Maps.newHashMap();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("my-process", variables);
        LOGGER.info("processInstance={}",processInstance);

        List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().listPage(0, 100);
    }


    @Test
    @org.activiti.engine.test.Deployment(resources = {"my-process-trigger.bpmn20.xml"})
    public void testTrigger(){
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("my-process");
        Execution someTask = runtimeService.createExecutionQuery().activityId("someTask").singleResult();
        LOGGER.info("excution={}",someTask);
        runtimeService.trigger(someTask.getId());
        someTask = runtimeService.createExecutionQuery().activityId("someTask").singleResult();
        LOGGER.info("excution={}",someTask);
    }

    @Test
    @org.activiti.engine.test.Deployment(resources = {"my-process-signal-received.bpmn20.xml"})
    public void testSignalEventReceived(){
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("my-process");
        Execution execution = runtimeService.createExecutionQuery().signalEventSubscriptionName("my-signal").singleResult();
        LOGGER.info("excution={}",execution);

        runtimeService.signalEventReceived("my-signal");
        execution=runtimeService.createExecutionQuery().signalEventSubscriptionName("my-signal").singleResult();
        LOGGER.info("excution={}",execution);
    }

    @Test
    @org.activiti.engine.test.Deployment(resources = {"my-process-message-received.bpmn20.xml"})
    public void testMessageEventReceived(){
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("my-process");
        Execution execution = runtimeService.createExecutionQuery().messageEventSubscriptionName("my-message").singleResult();
        LOGGER.info("excution={}",execution);

        runtimeService.messageEventReceived("my-message",execution.getId());
        execution=runtimeService.createExecutionQuery().messageEventSubscriptionName("my-message").singleResult();
        LOGGER.info("excution={}",execution);
    }

    @Test
    @org.activiti.engine.test.Deployment(resources = {"my-process-message.bpmn20.xml"})
    public void testMessageStart(){
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByMessage("my-message");
        LOGGER.info("processInstance={}",processInstance);

        List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().listPage(0, 100);
    }



}
