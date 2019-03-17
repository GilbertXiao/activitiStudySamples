package com.imooc.activiti.bpmn20;

import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: activiti6-sample
 * @description: UserTaskTest
 * @author: GilbertXiao
 * @create: 2019-02-26 21:39
 **/
public class UserTaskTest {

    private static final Logger LOGGER= LoggerFactory.getLogger(UserTaskTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    @Test
    @Deployment(resources = {"my-process-usertask.bpmn20.xml"})
    public void testUserTask(){
        ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("my-process");

        TaskService taskService = activitiRule.getTaskService();
        Task user1 = taskService.createTaskQuery().taskCandidateUser("user1").singleResult();
        LOGGER.info("find by user1 task={},",user1);
        Task user2 = taskService.createTaskQuery().taskCandidateUser("user2").singleResult();
        LOGGER.info("find by user2 task={},",user2);
        Task group1 = taskService.createTaskQuery().taskCandidateGroup("group1").singleResult();
        LOGGER.info("find by group1 task={},",group1);
    }

}
