package com.xm01.todo.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.xm01.common.annotation.Excel;
import com.xm01.common.core.domain.BaseEntity;

/**
 * 待办事项对象 project_todo
 * 
 * @author xm01
 * @date 2024-12-04
 */
public class ProjectTodo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** $column.columnComment */
    private String parentId;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 内容 */
    @Excel(name = "内容")
    private String content;

    /** 类型 */
    @Excel(name = "类型")
    private String type;

    /** 截止时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "截止时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date limitTime;

    /** 是否结束 */
    @Excel(name = "是否结束")
    private Long endTag;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date beginTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 执行者 */
    private String executor;

    /** 执行状态 */
    private String status;

    /** 是否置顶 */
    private String topTag;

    /** 优先权 */
    private Long priority;

    /** $column.columnComment */
    private String userId;

    /** $column.columnComment */
    private Long checkTag;

    /** $column.columnComment */
    private String checker;

    /** $column.columnComment */
    private Date checkTime;

    /** 创建者 */
    @Excel(name = "创建者")
    private String creator;

    /** 创建者id */
    private String creatorId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setParentId(String parentId) 
    {
        this.parentId = parentId;
    }

    public String getParentId() 
    {
        return parentId;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setLimitTime(Date limitTime) 
    {
        this.limitTime = limitTime;
    }

    public Date getLimitTime() 
    {
        return limitTime;
    }
    public void setEndTag(Long endTag) 
    {
        this.endTag = endTag;
    }

    public Long getEndTag() 
    {
        return endTag;
    }
    public void setBeginTime(Date beginTime) 
    {
        this.beginTime = beginTime;
    }

    public Date getBeginTime() 
    {
        return beginTime;
    }
    public void setEndTime(Date endTime) 
    {
        this.endTime = endTime;
    }

    public Date getEndTime() 
    {
        return endTime;
    }
    public void setExecutor(String executor) 
    {
        this.executor = executor;
    }

    public String getExecutor() 
    {
        return executor;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setTopTag(String topTag) 
    {
        this.topTag = topTag;
    }

    public String getTopTag() 
    {
        return topTag;
    }
    public void setPriority(Long priority) 
    {
        this.priority = priority;
    }

    public Long getPriority() 
    {
        return priority;
    }
    public void setUserId(String userId) 
    {
        this.userId = userId;
    }

    public String getUserId() 
    {
        return userId;
    }
    public void setCheckTag(Long checkTag) 
    {
        this.checkTag = checkTag;
    }

    public Long getCheckTag() 
    {
        return checkTag;
    }
    public void setChecker(String checker) 
    {
        this.checker = checker;
    }

    public String getChecker() 
    {
        return checker;
    }
    public void setCheckTime(Date checkTime) 
    {
        this.checkTime = checkTime;
    }

    public Date getCheckTime() 
    {
        return checkTime;
    }
    public void setCreator(String creator) 
    {
        this.creator = creator;
    }

    public String getCreator() 
    {
        return creator;
    }
    public void setCreatorId(String creatorId) 
    {
        this.creatorId = creatorId;
    }

    public String getCreatorId() 
    {
        return creatorId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("parentId", getParentId())
            .append("title", getTitle())
            .append("content", getContent())
            .append("type", getType())
            .append("limitTime", getLimitTime())
            .append("endTag", getEndTag())
            .append("beginTime", getBeginTime())
            .append("endTime", getEndTime())
            .append("executor", getExecutor())
            .append("status", getStatus())
            .append("topTag", getTopTag())
            .append("priority", getPriority())
            .append("userId", getUserId())
            .append("checkTag", getCheckTag())
            .append("checker", getChecker())
            .append("checkTime", getCheckTime())
            .append("creator", getCreator())
            .append("creatorId", getCreatorId())
            .append("createTime", getCreateTime())
            .toString();
    }
}
