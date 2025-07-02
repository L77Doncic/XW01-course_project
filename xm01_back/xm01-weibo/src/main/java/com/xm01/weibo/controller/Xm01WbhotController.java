package com.xm01.weibo.controller;

import java.io.BufferedReader;
import java.sql.SQLOutput;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.http.ResponseEntity;
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
import com.xm01.weibo.domain.Xm01Wbhot;
import com.xm01.weibo.service.IXm01WbhotService;
import com.xm01.common.utils.poi.ExcelUtil;
import com.xm01.common.core.page.TableDataInfo;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URL;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
/**
 * 微博热搜Controller
 * 
 * @author xm01
 * @date 2025-06-15
 */
@RestController
@RequestMapping("/weibo/wbhot")
public class Xm01WbhotController extends BaseController
{
    @Autowired
    private IXm01WbhotService xm01WbhotService;

    private final WebClient webClient;

    // 使用构造器注入 WebClient
    @Autowired
    public Xm01WbhotController(WebClient.Builder webClientBuilder) {
        // 配置 WebClient 的 base URL
        this.webClient = webClientBuilder.baseUrl("https://weibo.com").build();
    }

    /**
     * 通过访问 /weibo/wbhot/getJsonData 获取微博热搜 JSON 数据
     */
    @GetMapping("/getJsonData")
    public void getWeiboHotSearch() {
        // 微博热搜接口路径
        String endpoint = "/ajax/side/hotSearch";

        // 使用 WebClient 发起 GET 请求并返回 JSON 数据
        String json = webClient.get()
                .uri(endpoint) // 设置路径
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36") // 设置请求头
                .retrieve() // 发起请求并获取响应
                .bodyToMono(String.class)// 将响应体转换为 String 并返回
                .block();

        System.out.println("json"+json);
        // 将 JSON 字符串解析为 JSONObject
        JSONObject jsonObject = JSON.parseObject(json);

        // 检查 "data" 是否存在
        JSONObject dataObject = jsonObject.getJSONObject("data");
        if (dataObject == null) {
            System.out.println("data 字段不存在");
            return;
        }

        // 获取 "realtime" 数组
        JSONArray realtimeArray = dataObject.getJSONArray("realtime");
        if (realtimeArray == null || realtimeArray.isEmpty()) {
            System.out.println("realtime 数组为空");
            return;
        }
        removeAll();//删除原来所有的，再添加
        // 遍历数组，获取每条记录的 "note" 字段
        for (int i = 0; i < realtimeArray.size(); i++) {

            JSONObject record = realtimeArray.getJSONObject(i);
            Xm01Wbhot wb =new Xm01Wbhot();
            wb.setRealpos(record.getLong("realpos"));
            wb.setNote(record.getString("note"));
            wb.setNum(record.getLong("num"));
            wb.setIconDesc(record.getString("icon_desc"));
            add(wb);
        }

    }


    /**
     * 查询微博热搜列表
     */
    @PreAuthorize("@ss.hasPermi('weibo:wbhot:list')")
    @GetMapping("/list")

    public TableDataInfo list(Xm01Wbhot xm01Wbhot)
    {
        startPage();
        List<Xm01Wbhot> list = xm01WbhotService.selectXm01WbhotList(xm01Wbhot);
        return getDataTable(list);
    }

    /**
     * 导出微博热搜列表
     */
    @PreAuthorize("@ss.hasPermi('weibo:wbhot:export')")
    @Log(title = "微博热搜", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Xm01Wbhot xm01Wbhot)
    {
        List<Xm01Wbhot> list = xm01WbhotService.selectXm01WbhotList(xm01Wbhot);
        ExcelUtil<Xm01Wbhot> util = new ExcelUtil<Xm01Wbhot>(Xm01Wbhot.class);
        util.exportExcel(response, list, "微博热搜数据");
    }

    /**
     * 获取微博热搜详细信息
     */
    @PreAuthorize("@ss.hasPermi('weibo:wbhot:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(xm01WbhotService.selectXm01WbhotById(id));
    }

    /**
     * 新增微博热搜
     */
    @PreAuthorize("@ss.hasPermi('weibo:wbhot:add')")
    @Log(title = "微博热搜", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Xm01Wbhot xm01Wbhot)
    {
        return toAjax(xm01WbhotService.insertXm01Wbhot(xm01Wbhot));
    }

    /**
     * 修改微博热搜
     */
    @PreAuthorize("@ss.hasPermi('weibo:wbhot:edit')")
    @Log(title = "微博热搜", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Xm01Wbhot xm01Wbhot)
    {
        return toAjax(xm01WbhotService.updateXm01Wbhot(xm01Wbhot));
    }

    /**
     * 删除微博热搜
     */
    @PreAuthorize("@ss.hasPermi('weibo:wbhot:remove')")
    @Log(title = "微博热搜", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(xm01WbhotService.deleteXm01WbhotByIds(ids));
    }

    /**
     * 删除所有以存的
     * @return
     */
    public AjaxResult removeAll(){
        return toAjax(xm01WbhotService.deleteXM01wbhotAll());
    }
}
