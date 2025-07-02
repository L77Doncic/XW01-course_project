package com.xm01.todo.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.xm01.common.annotation.Log;
import com.xm01.common.core.controller.BaseController;
import com.xm01.common.core.domain.AjaxResult;
import com.xm01.common.enums.BusinessType;
import com.xm01.todo.domain.ProjectTodo;
import com.xm01.todo.service.IProjectTodoService;
import com.xm01.common.utils.poi.ExcelUtil;
import com.xm01.common.core.page.TableDataInfo;

/**
 * 待办事项Controller
 * 
 * @author xm01
 * @date 2025-06-04
 */
@RestController
@RequestMapping("/todo/todo")
public class ProjectTodoController extends BaseController
{
    @Autowired
    private IProjectTodoService projectTodoService;

    /**
     * 查询待办事项列表
     */
    @PreAuthorize("@ss.hasPermi('todo:todo:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProjectTodo projectTodo)
    {
        //1.开启分页
        startPage();
        //2.查询课程列表
        List<ProjectTodo> list = projectTodoService.selectProjectTodoList(projectTodo);
        //3.返回表格分页数据对象
        return getDataTable(list);
    }

    /**
     * 导出待办事项列表
     */
    @PreAuthorize("@ss.hasPermi('todo:todo:export')")
    @Log(title = "待办事项", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProjectTodo projectTodo)
    {
        List<ProjectTodo> list = projectTodoService.selectProjectTodoList(projectTodo);
        ExcelUtil<ProjectTodo> util = new ExcelUtil<ProjectTodo>(ProjectTodo.class);
        util.exportExcel(response, list, "待办事项数据");
    }

    /**
     * 获取待办事项详细信息
     */
    @PreAuthorize("@ss.hasPermi('todo:todo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(projectTodoService.selectProjectTodoById(id));
    }
    /**
     * 打印待办事项列表
     */
    @PreAuthorize("@ss.hasPermi('todo:todo:print')")
    @GetMapping("/print")
    public void print(HttpServletResponse response, ProjectTodo projectTodo)
    {
        List<ProjectTodo> list = projectTodoService.selectProjectTodoList(projectTodo);
        // 返回打印所需的数据格式
        response.setContentType("application/json;charset=utf-8");
        // 这里可以返回适合打印的数据格式
    }

    /**
     * 调用第三方接口
     */
    @PreAuthorize("@ss.hasPermi('todo:todo:api')")
    @GetMapping("/third-party")
    public AjaxResult thirdPartyAPI()
    {
        // 这里可以调用第三方API并返回结果
        return success("第三方接口调用成功");
    }
    /**
     * 新增待办事项
     */
    @PreAuthorize("@ss.hasPermi('todo:todo:add')")
    @Log(title = "待办事项", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProjectTodo projectTodo)
    {
        return toAjax(projectTodoService.insertProjectTodo(projectTodo));
    }

    /**
     * 修改待办事项
     */
    @PreAuthorize("@ss.hasPermi('todo:todo:edit')")
    @Log(title = "待办事项", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProjectTodo projectTodo)
    {
        return toAjax(projectTodoService.updateProjectTodo(projectTodo));
    }

    /**
     * 删除待办事项
     */
    @PreAuthorize("@ss.hasPermi('todo:todo:remove')")
    @Log(title = "待办事项", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(projectTodoService.deleteProjectTodoByIds(ids));
    }
}
