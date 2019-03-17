package com.imooc.activiti.dbentity;

import org.activiti.engine.ManagementService;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ByteArrayEntityImpl;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @program: activiti6-sample
 * @description: DbRepositoryTest
 * @author: GilbertXiao
 * @create: 2019-02-05 17:04
 **/
public class DbRepositoryTest {

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti-mysql.cfg.xml");

    @Test
    public void testByeArray(){
         activitiRule.getRepositoryService().createDeployment().name("二次审批流程").addClasspathResource("second_approve.bpmn20.xml").deploy();
    }

    @Test
    public void testDeploy(){
        ManagementService managementService = activitiRule.getManagementService();
        managementService.executeCommand(new Command<Object>() {

            @Override
            public Object execute(CommandContext commandContext) {
                ByteArrayEntityImpl entity = new ByteArrayEntityImpl();
                entity.setName("test");
                entity.setBytes("test message".getBytes());
                commandContext.getByteArrayEntityManager().insert(entity);
                return null;
            }
        });
    }
}
