package com.xm01.todo.service;

import java.util.List;
import com.xm01.todo.domain.ProjectTodo;

/**
 * 待办事项Service接口
 * 
 * @author xm01
 * @date 2025-06-04
 */
public interface IProjectTodoService 
{
    /**
     * 查询待办事项
     * 
     * @param id 待办事项主键
     * @return 待办事项
     */
    public ProjectTodo selectProjectTodoById(Long id);

    /**
     * 查询待办事项列表
     * 
     * @param projectTodo 待办事项
     * @return 待办事项集合
     */
    public List<ProjectTodo> selectProjectTodoList(ProjectTodo projectTodo);

    /**
     * 新增待办事项
     * 
     * @param projectTodo 待办事项
     * @return 结果
     */
    public int insertProjectTodo(ProjectTodo projectTodo);

    /**
     * 修改待办事项
     * 
     * @param projectTodo 待办事项
     * @return 结果
     */
    public int updateProjectTodo(ProjectTodo projectTodo);

    /**
     * 批量删除待办事项
     * 
     * @param ids 需要删除的待办事项主键集合
     * @return 结果
     */
    public int deleteProjectTodoByIds(Long[] ids);

    /**
     * 删除待办事项信息
     * 
     * @param id 待办事项主键
     * @return 结果
     */
    public int deleteProjectTodoById(Long id);
}
