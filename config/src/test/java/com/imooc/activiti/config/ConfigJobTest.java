package com.imooc.activiti.config;

import org.activiti.engine.runtime.Job;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @program: activiti6-sample
 * @description: MDCTest
 * @author: GilbertXiao
 * @create: 2018-12-27 23:45
 **/
public class ConfigJobTest {

    private static final Logger LOGGER= LoggerFactory.getLogger(ConfigJobTest.class);
    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti_job.cfg.xml");

    @Test
    @Deployment(resources = {"com/imooc/activiti/my-process_job.bpmn20.xml"})
    public void test() {
        LOGGER.info("START");
        List<Job> jobs = activitiRule.getManagementService().createTimerJobQuery().listPage(0, 100);
        for (Job job : jobs) {
            LOGGER.info("定时任务{}，默认重复次数{}",job,job.getRetries());
        }
        LOGGER.info("jobList.size()={}",jobs.size());
        try {
            Thread.sleep(1000*100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.info("END");


    }

}
