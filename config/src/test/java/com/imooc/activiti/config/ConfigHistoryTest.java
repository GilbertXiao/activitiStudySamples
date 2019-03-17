package com.imooc.activiti.config;

import com.google.common.collect.Maps;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @program: activiti6-sample
 * @description: MDCTest
 * @author: GilbertXiao
 * @create: 2018-12-27 23:45
 **/
public class ConfigHistoryTest {

    private static final Logger LOGGER= LoggerFactory.getLogger(ConfigHistoryTest.class);
    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti_history.cfg.xml");

    @Test
    @Deployment(resources = {"com/imooc/activiti/my-process.bpmn20.xml"})
    public void test() {

        //启动流程
        startProcessInstance();

        //修改变量
        changeVariable();

        //提交表单 task
        submitFormTask();


        //输出历史内容
        //输出历史活动
        showHistoryActivity();

        //输出历史变量
        showHistoryVariable();
        //输出历史用户任务
        showHistoryTask();

        //输出历史表单
        showHistoryForm();


        //输出历史详情
        showHistoryDetail();

    }

    private void showHistoryDetail() {
        List<HistoricDetail> historicDetails = activitiRule.getHistoryService().createHistoricDetailQuery().listPage(0, 100);
        for (HistoricDetail historicDetail : historicDetails) {
            LOGGER.info("historicDetail={}",toString(historicDetail));
        }
        LOGGER.info("historicDetails.size={}",historicDetails.size());
    }

    private void showHistoryForm() {
        List<HistoricDetail> historicDetailsForm = activitiRule.getHistoryService().createHistoricDetailQuery().formProperties().listPage(0, 100);
        for (HistoricDetail historicDetail : historicDetailsForm) {
            LOGGER.info("historicDetail={}",toString(historicDetail));
        }
        LOGGER.info("historicDetailsForm.size={}",historicDetailsForm.size());
    }

    private void showHistoryTask() {
        List<HistoricTaskInstance> historicTaskInstances = activitiRule.getHistoryService().createHistoricTaskInstanceQuery().listPage(0, 100);

        for (HistoricTaskInstance historicTaskInstance : historicTaskInstances) {
            LOGGER.info("historicTaskInstance={}",historicTaskInstance);
        }
        LOGGER.info("historicTaskInstances.size={}",historicTaskInstances.size());
    }

    private void showHistoryVariable() {
        List<HistoricVariableInstance> historicVariableInstances = activitiRule.getHistoryService().createHistoricVariableInstanceQuery().listPage(0, 100);

        for (HistoricVariableInstance historicVariableInstance : historicVariableInstances) {
            LOGGER.info("historicVariableInstance={}",historicVariableInstance);
        }
        LOGGER.info("historicVariableInstance.size={}",historicVariableInstances.size());
    }

    private void showHistoryActivity() {
        List<HistoricActivityInstance> historicActivityInstances = activitiRule.getHistoryService().createHistoricActivityInstanceQuery()
                .listPage(0, 100);
        for(HistoricActivityInstance historicActivityInstance:historicActivityInstances){
            LOGGER.info("historicActivityInstance = {}",historicActivityInstance);
        }
        LOGGER.info("historicActivityInstances.size = {}",historicActivityInstances.size());
    }

    private void submitFormTask() {
        Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
        Map<String,String> properties = Maps.newHashMap();
        properties.put("formkey1","valuef1");
        properties.put("formkey2","valuef2");
        activitiRule.getFormService().submitTaskFormData(task.getId(),properties);
    }

    private void changeVariable() {
        List<Execution> executions = activitiRule.getRuntimeService().createExecutionQuery().listPage(0, 100);
        for (Execution excution:executions) {
            LOGGER.info("execution = {}",excution);
        }
        LOGGER.info("execution.size = {}",executions.size());
        String id = executions.iterator().next().getId();
        activitiRule.getRuntimeService().setVariable(id,"keyStart1","value1_change");
    }

    private void startProcessInstance() {
        HashMap<String, Object> params = Maps.newHashMap();
        params.put("keyStart1","value1");
        params.put("keyStart2","value2");

        ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("my-process",params);
    }

    static String toString(HistoricDetail historicDetail){
        return ToStringBuilder.reflectionToString(historicDetail, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
