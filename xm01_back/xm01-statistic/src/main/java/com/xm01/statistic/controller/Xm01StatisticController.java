package com.xm01.statistic.controller;

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
import com.xm01.statistic.domain.Xm01Statistic;
import com.xm01.statistic.service.IXm01StatisticService;
import com.xm01.common.utils.poi.ExcelUtil;
import com.xm01.common.core.page.TableDataInfo;

/**
 * 车流量Controller
 * 
 * @author ruoyi
 * @date 2025-01-12
 */
@RestController
@RequestMapping("/statistic/statistic")
public class Xm01StatisticController extends BaseController
{
    @Autowired
    private IXm01StatisticService xm01StatisticService;

    /**
     * 查询车流量列表
     */
    @PreAuthorize("@ss.hasPermi('statistic:statistic:list')")
    @GetMapping("/list")
    public TableDataInfo list(Xm01Statistic xm01Statistic)
    {
        startPage();
        List<Xm01Statistic> list = xm01StatisticService.selectXm01StatisticList(xm01Statistic);
        return getDataTable(list);
    }

    /**
     * 导出车流量列表
     */
    @PreAuthorize("@ss.hasPermi('statistic:statistic:export')")
    @Log(title = "车流量", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Xm01Statistic xm01Statistic)
    {
        List<Xm01Statistic> list = xm01StatisticService.selectXm01StatisticList(xm01Statistic);
        ExcelUtil<Xm01Statistic> util = new ExcelUtil<Xm01Statistic>(Xm01Statistic.class);
        util.exportExcel(response, list, "车流量数据");
    }

    /**
     * 获取车流量详细信息
     */
    @PreAuthorize("@ss.hasPermi('statistic:statistic:query')")
    @GetMapping(value = "/{statisticId}")
    public AjaxResult getInfo(@PathVariable("statisticId") Long statisticId)
    {
        return success(xm01StatisticService.selectXm01StatisticByStatisticId(statisticId));
    }

    /**
     * 新增车流量
     */
    @PreAuthorize("@ss.hasPermi('statistic:statistic:add')")
    @Log(title = "车流量", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Xm01Statistic xm01Statistic)
    {
        return toAjax(xm01StatisticService.insertXm01Statistic(xm01Statistic));
    }

    /**
     * 修改车流量
     */
    @PreAuthorize("@ss.hasPermi('statistic:statistic:edit')")
    @Log(title = "车流量", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Xm01Statistic xm01Statistic)
    {
        return toAjax(xm01StatisticService.updateXm01Statistic(xm01Statistic));
    }

    /**
     * 删除车流量
     */
    @PreAuthorize("@ss.hasPermi('statistic:statistic:remove')")
    @Log(title = "车流量", businessType = BusinessType.DELETE)
	@DeleteMapping("/{statisticIds}")
    public AjaxResult remove(@PathVariable Long[] statisticIds)
    {
        return toAjax(xm01StatisticService.deleteXm01StatisticByStatisticIds(statisticIds));
    }
}
