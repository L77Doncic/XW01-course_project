package com.xm01.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.xm01.common.annotation.Excel;
import com.xm01.common.core.domain.BaseEntity;

/**
 * 投诉管理对象 xm01_complaints
 * 
 * @author ruoyi
 * @date 2025-01-12
 */
public class Xm01Complaints extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 投诉ID，主键，自增 */
    private Long id;

    /** 投诉内容 */
    @Excel(name = "投诉内容")
    private String content;

    /** 投诉时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "投诉时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date time;

    /** 投诉人姓名 */
    @Excel(name = "投诉人姓名")
    private String name;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setTime(Date time) 
    {
        this.time = time;
    }

    public Date getTime() 
    {
        return time;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("content", getContent())
            .append("time", getTime())
            .append("name", getName())
            .toString();
    }
}
