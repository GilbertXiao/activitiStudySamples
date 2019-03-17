package com.imooc.activiti.coreapi;

import com.imooc.activiti.mapper.MyCustomMapper;
import org.activiti.engine.ManagementService;
import org.activiti.engine.impl.cmd.AbstractCustomSqlExecution;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.management.TablePage;
import org.activiti.engine.runtime.DeadLetterJobQuery;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.runtime.JobQuery;
import org.activiti.engine.runtime.SuspendedJobQuery;
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
 * @description: ManagementServiceTest
 * @author: GilbertXiao
 * @create: 2019-01-25 03:25
 **/
public class ManagementServiceTest {

    private static final Logger LOGGER= LoggerFactory.getLogger(ManagementServiceTest.class);

    @Rule
    public ActivitiRule activitiRule=new ActivitiRule("activiti-job.cfg.xml");

    @Deployment(resources = {"my-process-job.bpmn20.xml"})
    @Test
    public void testJobQuery(){
        ManagementService managementService = activitiRule.getManagementService();
        List<Job> jobs = managementService.createTimerJobQuery().listPage(0, 100);
        for (Job timeJob : jobs) {
            LOGGER.info("timeJob={}",timeJob);

        }

        JobQuery jobQuery = managementService.createJobQuery();
        SuspendedJobQuery suspendedJobQuery = managementService.createSuspendedJobQuery();
        DeadLetterJobQuery deadLetterJobQuery = managementService.createDeadLetterJobQuery();




    }

    @Deployment(resources = {"my-process-job.bpmn20.xml"})
    @Test
    public void testTablePageQuery(){
        ManagementService managementService = activitiRule.getManagementService();
        List<Job> jobs = managementService.createTimerJobQuery().listPage(0, 100);
        TablePage tablePage = managementService.createTablePageQuery().tableName(managementService.getTableName(ProcessDefinitionEntity.class)).listPage(0, 100);

        List<Map<String, Object>> rows = tablePage.getRows();

        for (Map<String, Object> row : rows) {
            LOGGER.info("row={}",row);
        }


    }


    @Deployment(resources = {"my-process.bpmn20.xml"})
    @Test
    public void testTableQuery(){
        activitiRule.getRuntimeService().startProcessInstanceByKey("my-process");
        ManagementService managementService = activitiRule.getManagementService();
        List<Map<String, Object>> maps = managementService.executeCustomSql(new AbstractCustomSqlExecution<MyCustomMapper, List<Map<String, Object>>>(MyCustomMapper.class) {

            @Override
            public List<Map<String, Object>> execute(MyCustomMapper o) {
                return o.findAll();
            }
        });

        for (Map<String, Object> map : maps) {

            LOGGER.info("map={}",map);

        }

    }


    @Deployment(resources = {"my-process.bpmn20.xml"})
    @Test
    public void testCommand(){
        activitiRule.getRuntimeService().startProcessInstanceByKey("my-process");
        ManagementService managementService = activitiRule.getManagementService();

        ProcessDefinitionEntity processDefinitionEntity = managementService.executeCommand(new Command<ProcessDefinitionEntity>() {

            @Override
            public ProcessDefinitionEntity execute(CommandContext commandContext) {
                ProcessDefinitionEntity latestProcessDefinitionByKey = commandContext.getProcessDefinitionEntityManager().findLatestProcessDefinitionByKey("my-process");
                return latestProcessDefinitionByKey;
            }
        });

        LOGGER.info("processDefinitionEntity={}",processDefinitionEntity);
    }

}
