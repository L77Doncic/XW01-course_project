package com.xm01.system.controller;

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
import com.xm01.system.domain.Xm01Weather;
import com.xm01.system.service.IXm01WeatherService;
import com.xm01.common.utils.poi.ExcelUtil;
import com.xm01.common.core.page.TableDataInfo;

/**
 * weatherController
 * 
 * @author ruoyi
 * @date 2025-01-11
 */
@RestController
@RequestMapping("/system/weather")
public class Xm01WeatherController extends BaseController
{
    @Autowired
    private IXm01WeatherService xm01WeatherService;

    /**
     * 查询weather列表
     */
    @PreAuthorize("@ss.hasPermi('system:weather:list')")
    @GetMapping("/list")
    public TableDataInfo list(Xm01Weather xm01Weather)
    {
        startPage();
        List<Xm01Weather> list = xm01WeatherService.selectXm01WeatherList(xm01Weather);
        return getDataTable(list);
    }

    /**
     * 导出weather列表
     */
    @PreAuthorize("@ss.hasPermi('system:weather:export')")
    @Log(title = "weather", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Xm01Weather xm01Weather)
    {
        List<Xm01Weather> list = xm01WeatherService.selectXm01WeatherList(xm01Weather);
        ExcelUtil<Xm01Weather> util = new ExcelUtil<Xm01Weather>(Xm01Weather.class);
        util.exportExcel(response, list, "weather数据");
    }

    /**
     * 获取weather详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:weather:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(xm01WeatherService.selectXm01WeatherById(id));
    }

    /**
     * 新增weather
     */
    @PreAuthorize("@ss.hasPermi('system:weather:add')")
    @Log(title = "weather", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Xm01Weather xm01Weather)
    {
        return toAjax(xm01WeatherService.insertXm01Weather(xm01Weather));
    }

    /**
     * 修改weather
     */
    @PreAuthorize("@ss.hasPermi('system:weather:edit')")
    @Log(title = "weather", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Xm01Weather xm01Weather)
    {
        return toAjax(xm01WeatherService.updateXm01Weather(xm01Weather));
    }

    /**
     * 删除weather
     */
    @PreAuthorize("@ss.hasPermi('system:weather:remove')")
    @Log(title = "weather", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(xm01WeatherService.deleteXm01WeatherByIds(ids));
    }
}
