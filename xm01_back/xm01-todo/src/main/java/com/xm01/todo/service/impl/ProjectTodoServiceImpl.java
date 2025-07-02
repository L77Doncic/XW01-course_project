package com.xm01.todo.service.impl;

import java.util.List;
import com.xm01.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xm01.todo.mapper.ProjectTodoMapper;
import com.xm01.todo.domain.ProjectTodo;
import com.xm01.todo.service.IProjectTodoService;

/**
 * 待办事项Service业务层处理
 * 
 * @author xm01
 * @date 2025-06-04
 */
@Service
public class ProjectTodoServiceImpl implements IProjectTodoService 
{
    @Autowired
    private ProjectTodoMapper projectTodoMapper;

    /**
     * 查询待办事项
     * 
     * @param id 待办事项主键
     * @return 待办事项
     */
    @Override
    public ProjectTodo selectProjectTodoById(Long id)
    {
        return projectTodoMapper.selectProjectTodoById(id);
    }

    /**
     * 查询待办事项列表
     * 
     * @param projectTodo 待办事项
     * @return 待办事项
     */
    @Override
    public List<ProjectTodo> selectProjectTodoList(ProjectTodo projectTodo)
    {
        return projectTodoMapper.selectProjectTodoList(projectTodo);
    }

    /**
     * 新增待办事项
     * 
     * @param projectTodo 待办事项
     * @return 结果
     */
    @Override
    public int insertProjectTodo(ProjectTodo projectTodo)
    {
        projectTodo.setCreateTime(DateUtils.getNowDate());
        return projectTodoMapper.insertProjectTodo(projectTodo);
    }

    /**
     * 修改待办事项
     * 
     * @param projectTodo 待办事项
     * @return 结果
     */
    @Override
    public int updateProjectTodo(ProjectTodo projectTodo)
    {
        return projectTodoMapper.updateProjectTodo(projectTodo);
    }

    /**
     * 批量删除待办事项
     * 
     * @param ids 需要删除的待办事项主键
     * @return 结果
     */
    @Override
    public int deleteProjectTodoByIds(Long[] ids)
    {
        return projectTodoMapper.deleteProjectTodoByIds(ids);
    }

    /**
     * 删除待办事项信息
     * 
     * @param id 待办事项主键
     * @return 结果
     */
    @Override
    public int deleteProjectTodoById(Long id)
    {
        return projectTodoMapper.deleteProjectTodoById(id);
    }
}
