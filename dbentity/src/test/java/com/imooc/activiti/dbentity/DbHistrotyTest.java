package com.imooc.activiti.dbentity;

import com.google.common.collect.Maps;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.Map;

/**
 * @program: activiti6-sample
 * @description: DbRepositoryTest
 * @author: GilbertXiao
 * @create: 2019-02-05 17:04
 **/
public class DbHistrotyTest {

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti-mysql-hi.cfg.xml");

    @Test
    public void testRuntime(){
        activitiRule.getRepositoryService().createDeployment().name("二次审批流程").addClasspathResource("my-process.bpmn20.xml").deploy();

        RuntimeService runtimeService = activitiRule.getRuntimeService();
        Map<String,Object> variables =Maps.newHashMap();
        variables.put("key1","values1");
        variables.put("key2","values2");
        variables.put("key3","values3");

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("my-process", variables);

        runtimeService.setVariable(processInstance.getId(),"key1","value111111111111");

        TaskService taskService = activitiRule.getTaskService();
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        taskService.setOwner(task.getId(),"user1");

        taskService.createAttachment("url",task.getId(),processInstance.getId(),"name","desc","/url/test.png");
        taskService.addComment(task.getId(),task.getProcessInstanceId(),"record note1");
        taskService.addComment(task.getId(),task.getProcessInstanceId(),"record note2");

        Map<String,String> properties=Maps.newHashMap();
        properties.put("pro1","gil111");
        properties.put("pro2","gil222");
        properties.put("pro3","gil333");
        activitiRule.getFormService().submitTaskFormData(task.getId(),properties);

    }


}
