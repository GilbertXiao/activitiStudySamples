package com.imooc.activiti.coreapi;

import com.google.common.collect.Maps;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceBuilder;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.concurrent.ThreadSafe;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: activiti6-sample
 * @description: HistoryService
 * @author: GilbertXiao
 * @create: 2019-01-23 21:56
 **/
public class HistoryServiceTest {

    private static final Logger LOGGER= LoggerFactory.getLogger(HistoryServiceTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti-history.cfg.xml");

    @Test
    @Deployment(resources = {"my-process.bpmn20.xml"})
    public void testHistoryService(){
        HistoryService historyService = activitiRule.getHistoryService();
        ProcessInstanceBuilder processInstanceBuilder = activitiRule.getRuntimeService().createProcessInstanceBuilder();


        Map<String, Object> variables = Maps.newHashMap();
        variables.put("key01","value01");
        variables.put("key02","value02");
        variables.put("key03","value03");


        Map<String, Object> transientVariables= Maps.newHashMap();
        transientVariables.put("key01","value01");
        transientVariables.put("key02","value02");
        transientVariables.put("key03","value03");
        ProcessInstance processInstance = processInstanceBuilder.processDefinitionKey("my-process")
                .variables(variables)
                .transientVariables(transientVariables).start();

        activitiRule.getRuntimeService().setVariable(processInstance.getId(),"key01","value0000001");
        Task task = activitiRule.getTaskService().createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

        Map<String, String> properties=Maps.newHashMap();
        properties.put("fkey1","fvalue11");
        properties.put("key02","value222222222222222");

        activitiRule.getFormService().submitTaskFormData(task.getId(),properties);

        List<HistoricProcessInstance> historicProcessInstances = historyService.createHistoricProcessInstanceQuery().listPage(0, 100);
        for (HistoricProcessInstance historicProcessInstance : historicProcessInstances) {

            LOGGER.info("historicProcessInstance={}",historicProcessInstance);

        }

        List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery().listPage(0, 100);
        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
            LOGGER.info("historicActivityInstance={}",historicActivityInstance);
        }

        List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery().listPage(0, 100);
        for (HistoricTaskInstance historicTaskInstance : historicTaskInstances) {
            LOGGER.info("historicTaskInstance={}",historicTaskInstance);
        }

        List<HistoricVariableInstance> historicVariableInstances = historyService.createHistoricVariableInstanceQuery().listPage(0, 100);
        for (HistoricVariableInstance historicVariableInstance : historicVariableInstances) {
            LOGGER.info("historicVariableInstance={}",historicVariableInstance);
        }

        List<HistoricDetail> historicDetails = historyService.createHistoricDetailQuery().listPage(0, 100);
        for (HistoricDetail historicDetail : historicDetails) {
            LOGGER.info("historicDetail={}",historicDetail);
        }

        ProcessInstanceHistoryLog processInstanceHistoryLog = historyService.createProcessInstanceHistoryLogQuery(processInstance.getId()).includeVariables().includeFormProperties().includeComments().includeTasks().includeVariableUpdates()
                .singleResult();
        List<HistoricData> historicData = processInstanceHistoryLog.getHistoricData();
        for (HistoricData historicDatum : historicData) {
            LOGGER.info("historicDatum={}",historicDatum);
        }

        historyService.deleteHistoricProcessInstance(processInstance.getId());
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstance.getId()).singleResult();

        LOGGER.info("historicProcessInstance={}",historicProcessInstance);



    }

}
