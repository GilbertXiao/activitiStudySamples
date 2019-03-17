package com.imooc.activiti.dbentity;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
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
public class DbIdentityTest {

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti-mysql.cfg.xml");

    @Test
    public void testIdentity(){
        IdentityService identityService = activitiRule.getIdentityService();

        User user1 = identityService.newUser("user1");

        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setEmail("123@123.com");
        user1.setPassword("pwd");
        identityService.saveUser(user1);

        User user2= identityService.newUser("user2");

        user2.setFirstName("firstName2");
        user2.setLastName("lastName2");
        user2.setEmail("123@123222.com");
        user2.setPassword("pwd2");
        identityService.saveUser(user2);

        Group group1 = identityService.newGroup("group1");

        group1.setName("for test");
        identityService.saveGroup(group1);

        Group group2= identityService.newGroup("group2");

        group1.setName("for test2");
        identityService.saveGroup(group2);
        identityService.createMembership(user1.getId(),group1.getId());
        identityService.createMembership(user1.getId(),group2.getId());
        identityService.createMembership(user2.getId(),group1.getId());
        identityService.createMembership(user2.getId(),group2.getId());

    }
}
