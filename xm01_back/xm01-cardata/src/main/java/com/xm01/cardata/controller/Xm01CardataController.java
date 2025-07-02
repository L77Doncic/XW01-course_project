package com.xm01.cardata.controller;

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
import com.xm01.cardata.domain.Xm01Cardata;
import com.xm01.cardata.service.IXm01CardataService;
import com.xm01.common.utils.poi.ExcelUtil;
import com.xm01.common.core.page.TableDataInfo;

/**
 * 违法信息Controller
 * 
 * @author ruoyi
 * @date 2025-01-13
 */
@RestController
@RequestMapping("/cardata/cardata")
public class Xm01CardataController extends BaseController
{
    @Autowired
    private IXm01CardataService xm01CardataService;

    /**
     * 查询违法信息列表
     */
    @PreAuthorize("@ss.hasPermi('cardata:cardata:list')")
    @GetMapping("/list")
    public TableDataInfo list(Xm01Cardata xm01Cardata)
    {
        startPage();
        List<Xm01Cardata> list = xm01CardataService.selectXm01CardataList(xm01Cardata);
        return getDataTable(list);
    }

    /**
     * 导出违法信息列表
     */
    @PreAuthorize("@ss.hasPermi('cardata:cardata:export')")
    @Log(title = "违法信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Xm01Cardata xm01Cardata)
    {
        List<Xm01Cardata> list = xm01CardataService.selectXm01CardataList(xm01Cardata);
        ExcelUtil<Xm01Cardata> util = new ExcelUtil<Xm01Cardata>(Xm01Cardata.class);
        util.exportExcel(response, list, "违法信息数据");
    }

    /**
     * 获取违法信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('cardata:cardata:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(xm01CardataService.selectXm01CardataById(id));
    }

    /**
     * 新增违法信息
     */
    @PreAuthorize("@ss.hasPermi('cardata:cardata:add')")
    @Log(title = "违法信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Xm01Cardata xm01Cardata)
    {
        return toAjax(xm01CardataService.insertXm01Cardata(xm01Cardata));
    }

    /**
     * 修改违法信息
     */
    @PreAuthorize("@ss.hasPermi('cardata:cardata:edit')")
    @Log(title = "违法信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Xm01Cardata xm01Cardata)
    {
        return toAjax(xm01CardataService.updateXm01Cardata(xm01Cardata));
    }

    /**
     * 删除违法信息
     */
    @PreAuthorize("@ss.hasPermi('cardata:cardata:remove')")
    @Log(title = "违法信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(xm01CardataService.deleteXm01CardataByIds(ids));
    }
}
