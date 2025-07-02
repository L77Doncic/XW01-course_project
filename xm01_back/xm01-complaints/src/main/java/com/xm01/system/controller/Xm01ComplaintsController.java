package com.xm01.system.controller;

import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
import com.xm01.system.domain.Xm01Complaints;
import com.xm01.system.service.IXm01ComplaintsService;
import com.xm01.common.utils.poi.ExcelUtil;
import com.xm01.common.core.page.TableDataInfo;

/**
 * 投诉管理Controller
 * 
 * @author ruoyi
 * @date 2025-01-12
 */
@RestController
@RequestMapping("/complaint/complaints")
public class Xm01ComplaintsController extends BaseController
{
    @Autowired
    private IXm01ComplaintsService xm01ComplaintsService;

    /**
     * 查询投诉管理列表
     */
    @PreAuthorize("@ss.hasPermi('complaint:complaints:list')")
    @GetMapping("/list")
    public TableDataInfo list(Xm01Complaints xm01Complaints)
    {
        startPage();
        List<Xm01Complaints> list = xm01ComplaintsService.selectXm01ComplaintsList(xm01Complaints);
        return getDataTable(list);
    }

    /**
     * 导出投诉管理列表
     */
    @PreAuthorize("@ss.hasPermi('complaint:complaints:export')")
    @Log(title = "投诉管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Xm01Complaints xm01Complaints)
    {
        List<Xm01Complaints> list = xm01ComplaintsService.selectXm01ComplaintsList(xm01Complaints);
        ExcelUtil<Xm01Complaints> util = new ExcelUtil<Xm01Complaints>(Xm01Complaints.class);
        util.exportExcel(response, list, "投诉管理数据");
    }

    /**
     * 获取投诉管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('complaint:complaints:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(xm01ComplaintsService.selectXm01ComplaintsById(id));
    }

    /**
     * 新增投诉管理
     */
    @PreAuthorize("@ss.hasPermi('complaint:complaints:add')")
    @Log(title = "投诉管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Xm01Complaints xm01Complaints)
    {
        return toAjax(xm01ComplaintsService.insertXm01Complaints(xm01Complaints));
    }

    /**
     * 修改投诉管理
     */
    @PreAuthorize("@ss.hasPermi('complaint:complaints:edit')")
    @Log(title = "投诉管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Xm01Complaints xm01Complaints)
    {
        return toAjax(xm01ComplaintsService.updateXm01Complaints(xm01Complaints));
    }

    /**
     * 删除投诉管理
     */
    @PreAuthorize("@ss.hasPermi('complaint:complaints:remove')")
    @Log(title = "投诉管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(xm01ComplaintsService.deleteXm01ComplaintsByIds(ids));
    }

    /**
     * 打印投诉管理列表
     */
    @PreAuthorize("@ss.hasPermi('complaint:complaints:print')")
    @GetMapping("/print")
    public void print(HttpServletResponse response, Xm01Complaints xm01Complaints)
    {
        List<Xm01Complaints> list = xm01ComplaintsService.selectXm01ComplaintsList(xm01Complaints);
        // 返回打印所需的数据格式
        response.setContentType("application/json;charset=utf-8");
    }

    /**
     * 模拟第三方接口
     */
    @PreAuthorize("@ss.hasPermi('complaint:complaints:api')")
    @GetMapping("/third-party-mock")
    public AjaxResult thirdPartyMock()
    {
        Xm01Complaints mockData = new Xm01Complaints();
        mockData.setContent("来自第三方系统的投诉建议");
        mockData.setName("第三方用户");
        mockData.setTime(new Date());

        return success(mockData);
    }
}
