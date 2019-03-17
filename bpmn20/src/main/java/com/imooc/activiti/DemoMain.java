package com.imooc.activiti;


import com.google.common.collect.Maps;
import org.activiti.engine.*;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.impl.form.DateFormType;
import org.activiti.engine.impl.form.StringFormType;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class DemoMain {

    private static final Logger log = LoggerFactory.getLogger(DemoMain.class);

    public static void main(String[] args) throws ParseException {
        log.info("start our program");
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();
        ProcessEngine processEngine = configuration.buildProcessEngine();
        String name = processEngine.getName();
        String version = processEngine.VERSION;
        log.info("流程引擎名称{},版本{}", name, version);

        RepositoryService repositoryService = processEngine.getRepositoryService();
        DeploymentBuilder deployment = repositoryService.createDeployment();
        deployment.addClasspathResource("second_approve.bpmn");
        Deployment deploy = deployment.deploy();
        String deployId = deploy.getId();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployId).singleResult();
        log.info("流程定义文件{}，流程{}", processDefinition.getName(), processDefinition.getId());

        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());
        log.info("启动流程{}", processInstance.getProcessDefinitionId());

        Scanner scanner = new Scanner(System.in);

        while (processInstance != null && !processInstance.isEnded()) {
            TaskService taskService = processEngine.getTaskService();
            List<Task> list = taskService.createTaskQuery().list();
            log.info("待处理任务数量{}", list.size());
            for (Task task : list) {
                log.info("待处理任务{}", task.getName());
                FormService formService = processEngine.getFormService();
                TaskFormData taskFormData = formService.getTaskFormData(task.getId());
                List<FormProperty> formProperties = taskFormData.getFormProperties();
                HashMap<String, Object> map = Maps.newHashMap();
                for (FormProperty property : formProperties) {
                    String line = null;
                    if (StringFormType.class.isInstance(property.getType())) {

                        log.info("请输入{}？", property.getName());
                        line = scanner.nextLine();
                        map.put(property.getId(), line);
                    } else if (DateFormType.class.isInstance(property.getType())) {
                        log.info("请输入{}？格式（yyyy-MM-dd）", property.getName());
                        String pattern = "yyyy-MM-dd";
                        line = scanner.nextLine();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                        Date date = simpleDateFormat.parse(line);
                        map.put(property.getId(), date);
                    } else {
                        log.info("内容暂不支持{}", property.getType());
                    }
                    log.info("您输入的内容是[{}]", line);
                }
                taskService.complete(task.getId(), map);

                processInstance = processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceId(processInstance.getId()).singleResult();


            }

        }


        log.info("stop");
    }
}
