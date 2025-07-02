package com.xm01.carcolor.controller;

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
import com.xm01.carcolor.domain.Xm01Carcolor;
import com.xm01.carcolor.service.IXm01CarcolorService;
import com.xm01.common.utils.poi.ExcelUtil;
import com.xm01.common.core.page.TableDataInfo;

/**
 * carcolorController
 * 
 * @author ruoyi
 * @date 2025-01-13
 */
@RestController
@RequestMapping("/carcolor/carcolor")
public class Xm01CarcolorController extends BaseController
{
    @Autowired
    private IXm01CarcolorService xm01CarcolorService;

    /**
     * 查询carcolor列表
     */
    @PreAuthorize("@ss.hasPermi('carcolor:carcolor:list')")
    @GetMapping("/list")
    public TableDataInfo list(Xm01Carcolor xm01Carcolor)
    {
        startPage();
        List<Xm01Carcolor> list = xm01CarcolorService.selectXm01CarcolorList(xm01Carcolor);
        return getDataTable(list);
    }

    /**
     * 导出carcolor列表
     */
    @PreAuthorize("@ss.hasPermi('carcolor:carcolor:export')")
    @Log(title = "carcolor", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Xm01Carcolor xm01Carcolor)
    {
        List<Xm01Carcolor> list = xm01CarcolorService.selectXm01CarcolorList(xm01Carcolor);
        ExcelUtil<Xm01Carcolor> util = new ExcelUtil<Xm01Carcolor>(Xm01Carcolor.class);
        util.exportExcel(response, list, "carcolor数据");
    }

    /**
     * 获取carcolor详细信息
     */
    @PreAuthorize("@ss.hasPermi('carcolor:carcolor:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(xm01CarcolorService.selectXm01CarcolorById(id));
    }

    /**
     * 新增carcolor
     */
    @PreAuthorize("@ss.hasPermi('carcolor:carcolor:add')")
    @Log(title = "carcolor", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Xm01Carcolor xm01Carcolor)
    {
        return toAjax(xm01CarcolorService.insertXm01Carcolor(xm01Carcolor));
    }

    /**
     * 修改carcolor
     */
    @PreAuthorize("@ss.hasPermi('carcolor:carcolor:edit')")
    @Log(title = "carcolor", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Xm01Carcolor xm01Carcolor)
    {
        return toAjax(xm01CarcolorService.updateXm01Carcolor(xm01Carcolor));
    }

    /**
     * 删除carcolor
     */
    @PreAuthorize("@ss.hasPermi('carcolor:carcolor:remove')")
    @Log(title = "carcolor", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(xm01CarcolorService.deleteXm01CarcolorByIds(ids));
    }
}
