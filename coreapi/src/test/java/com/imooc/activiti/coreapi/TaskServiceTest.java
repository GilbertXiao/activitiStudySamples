package com.imooc.activiti.coreapi;

import com.google.common.collect.Maps;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.*;
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

/**
 * @program: activiti6-sample
 * @description: TaskServiceTest
 * @author: GilbertXiao
 * @create: 2019-01-15 23:09
 **/
public class TaskServiceTest {
    private static final Logger LOGGER= LoggerFactory.getLogger(TaskServiceTest.class);

    @Rule
    public ActivitiRule activitiRule=new ActivitiRule();

    @Test
    @Deployment(resources = {"my-process-task.bpmn20.xml"})
    public void testTaskService(){
        HashMap<String, Object> variables =Maps.newHashMap();
        variables.put("message","my test message!!!");
        activitiRule.getRuntimeService().startProcessInstanceByKey("my-process",variables);

        TaskService taskService = activitiRule.getTaskService();
        Task task = taskService.createTaskQuery().singleResult();
        LOGGER.info("task={}", ToStringBuilder.reflectionToString(task, ToStringStyle.JSON_STYLE));
        LOGGER.info("task.description={}", task.getDescription());

        taskService.setVariable(task.getId(),"key1","values1");
        taskService.setVariableLocal(task.getId(),"localKey1","value2");

        Map<String, Object> variables1 = taskService.getVariables(task.getId());

        Map<String, Object> variablesLocal = taskService.getVariablesLocal(task.getId());

        Map<String, Object> variables2 = activitiRule.getRuntimeService().getVariables(task.getExecutionId());

        LOGGER.info("map1={}",variables1);
        LOGGER.info("map2={}",variablesLocal);
        LOGGER.info("map3={}",variables2);

        HashMap<String, Object> completeVar = Maps.newHashMap();
        completeVar.put("vkey","values1");
        taskService.complete(task.getId(),completeVar);

        Task task1 = taskService.createTaskQuery().taskId(task.getId()).singleResult();
        LOGGER.info("task1={}",task1);

    }


    @Test
    @Deployment(resources = {"my-process-task.bpmn20.xml"})
    public void testTaskServiceUser(){
        Map<String, Object> variables =Maps.newHashMap();
        variables.put("message","my test message!!!");
        activitiRule.getRuntimeService().startProcessInstanceByKey("my-process",variables);

        TaskService taskService = activitiRule.getTaskService();
        Task task = taskService.createTaskQuery().singleResult();
        LOGGER.info("task={}", ToStringBuilder.reflectionToString(task, ToStringStyle.JSON_STYLE));
        LOGGER.info("task.description={}", task.getDescription());

        taskService.setOwner(task.getId(),"user1");
        //taskService.setAssignee(task.getId(),"Jimmy");

        List<Task> tasks = taskService.createTaskQuery().taskCandidateUser("Jimmy").taskUnassigned().listPage(0, 100);
        for (Task task1 : tasks) {
            try {
                taskService.claim(task1.getId(),"Jimmy");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask(task.getId());

        for (IdentityLink identityLink : identityLinksForTask) {
            LOGGER.info("identityLink={}",identityLink);
        }

        List<Task> myTaskList = taskService.createTaskQuery().taskAssignee("Jimmy").listPage(0, 100);
        for (Task task1 : myTaskList) {
            Map<String,Object> variable = Maps.newHashMap();

            variable.put("ckey1","cvalue1");
            taskService.complete(task1.getId(),variable);


        }

        List<Task> myTaskList1 = taskService.createTaskQuery().taskAssignee("Jimmy").listPage(0, 100);
        LOGGER.info("是否存在{}",!myTaskList1.isEmpty());
    }

    @Test
        @Deployment(resources = {"my-process-task.bpmn20.xml"})
        public void testTaskAttachment() {
            Map<String, Object> variables = Maps.newHashMap();
            variables.put("message", "my test message!!!");
            activitiRule.getRuntimeService().startProcessInstanceByKey("my-process", variables);

            TaskService taskService = activitiRule.getTaskService();
            Task task = taskService.createTaskQuery().singleResult();
            LOGGER.info("task={}", ToStringBuilder.reflectionToString(task, ToStringStyle.JSON_STYLE));
            LOGGER.info("task.description={}", task.getDescription());

            taskService.createAttachment("url",task.getId(),task.getProcessInstanceId(),"name","desc","/url/test.png");

            List<Attachment> taskAttachments = taskService.getTaskAttachments(task.getId());
            for (Attachment taskAttachment : taskAttachments) {
                LOGGER.info("taskAttachment={}",ToStringBuilder.reflectionToString(taskAttachment,ToStringStyle.JSON_STYLE));

            }

    }

    @Test
    @Deployment(resources = {"my-process-task.bpmn20.xml"})
    public void testTaskComment() {
        Map<String, Object> variables = Maps.newHashMap();
        variables.put("message", "my test message!!!");
        activitiRule.getRuntimeService().startProcessInstanceByKey("my-process", variables);

        TaskService taskService = activitiRule.getTaskService();
        Task task = taskService.createTaskQuery().singleResult();
        LOGGER.info("task={}", ToStringBuilder.reflectionToString(task, ToStringStyle.JSON_STYLE));
        LOGGER.info("task.description={}", task.getDescription());

        taskService.addComment(task.getId(),task.getProcessInstanceId(),"note1");
        taskService.addComment(task.getId(),task.getProcessInstanceId(),"note2");

        List<Comment> taskComments = taskService.getTaskComments(task.getId());
        for (Comment taskComment : taskComments) {
            LOGGER.info("taskComment={}",ToStringBuilder.reflectionToString(taskComment,ToStringStyle.JSON_STYLE));
        }

        List<Event> taskEvents = taskService.getTaskEvents(task.getId());

        for (Event taskEvent : taskEvents) {
            LOGGER.info("taskEvent={}",ToStringBuilder.reflectionToString(taskEvent,ToStringStyle.JSON_STYLE));
        }

    }
}
