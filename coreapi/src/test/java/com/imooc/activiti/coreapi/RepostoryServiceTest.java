package com.imooc.activiti.coreapi;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @program: activiti6-sample
 * @description: test
 * @author: GilbertXiao
 * @create: 2019-01-09 21:27
 **/
public class RepostoryServiceTest {

    private static final Logger LOGGER= LoggerFactory.getLogger(RepostoryServiceTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    @Test
    public void testRepostory(){
        RepositoryService repositoryService = activitiRule.getRepositoryService();
        DeploymentBuilder deployment = repositoryService.createDeployment();
        deployment.name("测试部署资源1")
                .addClasspathResource("my-process.bpmn20.xml")
                .addClasspathResource("second_approve.bpmn");

        Deployment deploy = deployment.deploy();

        DeploymentBuilder deployment2 = repositoryService.createDeployment();
        deployment2.name("测试部署资源2")
                .addClasspathResource("my-process.bpmn20.xml")
                .addClasspathResource("second_approve.bpmn");

        Deployment deploy1 = deployment2.deploy();
        LOGGER.info("deploy={}",deploy);
        DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();
        List<Deployment> deployments = deploymentQuery.orderByDeploymenTime().asc().listPage(0, 100);
        //.deploymentId(deploy.getId())
        for (Deployment singleDeployment : deployments) {
            LOGGER.info("deploy={}",singleDeployment );
            List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().deploymentId(singleDeployment .getId()).listPage(0, 100);
            for (ProcessDefinition processDefinition : processDefinitions) {
                LOGGER.info("流程定义={}，version={},key={},id={}",processDefinition,processDefinition.getVersion(),processDefinition.getKey(),processDefinition.getId());


            }
        }
    }


    @Test
    @org.activiti.engine.test.Deployment(resources = {"my-process.bpmn20.xml"})
    public void testSupsend(){

        RepositoryService repositoryService = activitiRule.getRepositoryService();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().singleResult();
        LOGGER.info("processDefinition.id={}",processDefinition.getId());
        repositoryService.suspendProcessDefinitionById(processDefinition.getId());

        try {
            LOGGER.info("开始启动");
            activitiRule.getRuntimeService().startProcessInstanceById(processDefinition.getId());
            LOGGER.info("启动成功");
        } catch (Exception e) {
            LOGGER.info("启动失败");
            LOGGER.info(e.getMessage(),e);
        }

        repositoryService.activateProcessDefinitionById(processDefinition.getId());

        LOGGER.info("开始启动");
        activitiRule.getRuntimeService().startProcessInstanceById(processDefinition.getId());
        LOGGER.info("启动成功");
    }

    @Test
    @org.activiti.engine.test.Deployment(resources = {"my-process.bpmn20.xml"})
    public void testCandidateStarter(){

        RepositoryService repositoryService = activitiRule.getRepositoryService();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().singleResult();
        LOGGER.info("processDefinition.id={}",processDefinition.getId());

        repositoryService.addCandidateStarterUser(processDefinition.getId(),"user");
        repositoryService.addCandidateStarterGroup(processDefinition.getId(),"group");

        List<IdentityLink> identityLinksForProcessDefinition = repositoryService.getIdentityLinksForProcessDefinition(processDefinition.getId());

        for (IdentityLink identityLink : identityLinksForProcessDefinition) {
            LOGGER.info("identityLink={}",identityLink);

        }
        repositoryService.deleteCandidateStarterGroup(processDefinition.getId(),"group");
        repositoryService.deleteCandidateStarterUser(processDefinition.getId(),"user");


    }



}
