package com.imooc.activiti.bpmn20;

import com.google.common.collect.Maps;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
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
 * @description: UserTaskTest
 * @author: GilbertXiao
 * @create: 2019-02-26 21:39
 **/
public class ScriptTaskTest {

    private static final Logger LOGGER= LoggerFactory.getLogger(ScriptTaskTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    @Test
    @Deployment(resources = {"my-process-scripttask1.bpmn20.xml"})
    public void testUserTask(){
        ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("my-process");

        HistoryService historyService = activitiRule.getHistoryService();
        List<HistoricVariableInstance> historicVariableInstances = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstance.getId()).orderByVariableName().asc().listPage(0, 100);

        for (HistoricVariableInstance historicVariableInstance : historicVariableInstances) {
            LOGGER.info("historicVariableInstance={}",historicVariableInstance);

        }
        LOGGER.info("historicVariableInstances.size()={}",historicVariableInstances.size());
    }

    @Test
    @Deployment(resources = {"my-process-scripttask2.bpmn20.xml"})
    public void testScriptTask2(){
        Map<String,Object> variables = Maps.newHashMap();
        variables.put("key1",3);
        variables.put("key2",8);
        ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("my-process",variables);

        HistoryService historyService = activitiRule.getHistoryService();
        List<HistoricVariableInstance> historicVariableInstances = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstance.getId()).orderByVariableName().asc().listPage(0, 100);

        for (HistoricVariableInstance historicVariableInstance : historicVariableInstances) {
            LOGGER.info("historicVariableInstance={}",historicVariableInstance);

        }
        LOGGER.info("historicVariableInstances.size()={}",historicVariableInstances.size());
        ManagementService managementService = activitiRule.getManagementService();
        managementService.executeCommand(new Command<Object>() {
            @Override
            public Object execute(CommandContext commandContext) {
                return null;
            }
        });
    }



}
