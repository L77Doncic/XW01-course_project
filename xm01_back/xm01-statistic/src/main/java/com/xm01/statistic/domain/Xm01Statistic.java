package com.xm01.statistic.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.xm01.common.annotation.Excel;
import com.xm01.common.core.domain.BaseEntity;

/**
 * 车流量对象 xm01_statistic
 * 
 * @author ruoyi
 * @date 2025-01-12
 */
public class Xm01Statistic extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long statisticId;

    /** 车流量 */
    @Excel(name = "车流量")
    private Long trafficFlow;

    /** 纬度 */
    @Excel(name = "纬度")
    private BigDecimal widX1;

    /** 经度 */
    @Excel(name = "经度")
    private BigDecimal widY1;

    /** $column.columnComment */
    private Long widX2;

    /** $column.columnComment */
    private Long widY2;

    public void setStatisticId(Long statisticId) 
    {
        this.statisticId = statisticId;
    }

    public Long getStatisticId() 
    {
        return statisticId;
    }
    public void setTrafficFlow(Long trafficFlow) 
    {
        this.trafficFlow = trafficFlow;
    }

    public Long getTrafficFlow() 
    {
        return trafficFlow;
    }
    public void setWidX1(BigDecimal widX1) 
    {
        this.widX1 = widX1;
    }

    public BigDecimal getWidX1() 
    {
        return widX1;
    }
    public void setWidY1(BigDecimal widY1) 
    {
        this.widY1 = widY1;
    }

    public BigDecimal getWidY1() 
    {
        return widY1;
    }
    public void setWidX2(Long widX2) 
    {
        this.widX2 = widX2;
    }

    public Long getWidX2() 
    {
        return widX2;
    }
    public void setWidY2(Long widY2) 
    {
        this.widY2 = widY2;
    }

    public Long getWidY2() 
    {
        return widY2;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("statisticId", getStatisticId())
            .append("trafficFlow", getTrafficFlow())
            .append("widX1", getWidX1())
            .append("widY1", getWidY1())
            .append("widX2", getWidX2())
            .append("widY2", getWidY2())
            .append("createTime", getCreateTime())
            .toString();
    }
}
