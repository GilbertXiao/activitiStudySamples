package com.imooc.activiti.mapper;

import org.activiti.engine.task.Task;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @program: activiti6-sample
 * @description: mapper
 * @author: GilbertXiao
 * @create: 2019-01-31 23:32
 **/
public interface MyCustomMapper {

    @Select("select * from act_ru_task")
    public List<Map<String,Object>> findAll();
}
