package com.xm01.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.xm01.common.annotation.Excel;
import com.xm01.common.core.domain.BaseEntity;

/**
 * weather对象 xm01_weather
 * 
 * @author ruoyi
 * @date 2025-01-11
 */
public class Xm01Weather extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 城市 */
    @Excel(name = "城市")
    private String city;

    /** 观测时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "观测时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date obsTime;

    /** 当前温度 */
    @Excel(name = "当前温度")
    private BigDecimal currentTemp;

    /** 最高温度 */
    @Excel(name = "最高温度")
    private String tempMax;

    /** 最低温度 */
    @Excel(name = "最低温度")
    private String tempMin;

    /** 天气情况 */
    @Excel(name = "天气情况")
    private String currentWeatherText;

    /** 未来1天 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "未来1天", width = 30, dateFormat = "yyyy-MM-dd")
    private Date day1FxDate;

    /** 未来1天最高温 */
    @Excel(name = "未来1天最高温")
    private BigDecimal day1TempMax;

    /** 未来1天最低温 */
    @Excel(name = "未来1天最低温")
    private BigDecimal day1TempMin;

    /** 未来2天 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "未来2天", width = 30, dateFormat = "yyyy-MM-dd")
    private Date day2FxDate;

    /** 未来2天最高温 */
    @Excel(name = "未来2天最高温")
    private BigDecimal day2TempMax;

    /** 未来2天最低温 */
    @Excel(name = "未来2天最低温")
    private BigDecimal day2TempMin;

    /** 未来3天 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "未来3天", width = 30, dateFormat = "yyyy-MM-dd")
    private Date day3FxDate;

    /** 未来3天最高温 */
    @Excel(name = "未来3天最高温")
    private BigDecimal day3TempMax;

    /** 未来3天最低温 */
    @Excel(name = "未来3天最低温")
    private BigDecimal day3TempMin;

    /** 未来4天 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "未来4天", width = 30, dateFormat = "yyyy-MM-dd")
    private Date day4FxDate;

    /** 未来4天最高温 */
    @Excel(name = "未来4天最高温")
    private BigDecimal day4TempMax;

    /** 未来4天最低温 */
    @Excel(name = "未来4天最低温")
    private BigDecimal day4TempMin;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdAt;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCity(String city) 
    {
        this.city = city;
    }

    public String getCity() 
    {
        return city;
    }
    public void setObsTime(Date obsTime) 
    {
        this.obsTime = obsTime;
    }

    public Date getObsTime() 
    {
        return obsTime;
    }
    public void setCurrentTemp(BigDecimal currentTemp) 
    {
        this.currentTemp = currentTemp;
    }

    public BigDecimal getCurrentTemp() 
    {
        return currentTemp;
    }
    public void setTempMax(String tempMax) 
    {
        this.tempMax = tempMax;
    }

    public String getTempMax() 
    {
        return tempMax;
    }
    public void setTempMin(String tempMin) 
    {
        this.tempMin = tempMin;
    }

    public String getTempMin() 
    {
        return tempMin;
    }
    public void setCurrentWeatherText(String currentWeatherText) 
    {
        this.currentWeatherText = currentWeatherText;
    }

    public String getCurrentWeatherText() 
    {
        return currentWeatherText;
    }
    public void setDay1FxDate(Date day1FxDate) 
    {
        this.day1FxDate = day1FxDate;
    }

    public Date getDay1FxDate() 
    {
        return day1FxDate;
    }
    public void setDay1TempMax(BigDecimal day1TempMax) 
    {
        this.day1TempMax = day1TempMax;
    }

    public BigDecimal getDay1TempMax() 
    {
        return day1TempMax;
    }
    public void setDay1TempMin(BigDecimal day1TempMin) 
    {
        this.day1TempMin = day1TempMin;
    }

    public BigDecimal getDay1TempMin() 
    {
        return day1TempMin;
    }
    public void setDay2FxDate(Date day2FxDate) 
    {
        this.day2FxDate = day2FxDate;
    }

    public Date getDay2FxDate() 
    {
        return day2FxDate;
    }
    public void setDay2TempMax(BigDecimal day2TempMax) 
    {
        this.day2TempMax = day2TempMax;
    }

    public BigDecimal getDay2TempMax() 
    {
        return day2TempMax;
    }
    public void setDay2TempMin(BigDecimal day2TempMin) 
    {
        this.day2TempMin = day2TempMin;
    }

    public BigDecimal getDay2TempMin() 
    {
        return day2TempMin;
    }
    public void setDay3FxDate(Date day3FxDate) 
    {
        this.day3FxDate = day3FxDate;
    }

    public Date getDay3FxDate() 
    {
        return day3FxDate;
    }
    public void setDay3TempMax(BigDecimal day3TempMax) 
    {
        this.day3TempMax = day3TempMax;
    }

    public BigDecimal getDay3TempMax() 
    {
        return day3TempMax;
    }
    public void setDay3TempMin(BigDecimal day3TempMin) 
    {
        this.day3TempMin = day3TempMin;
    }

    public BigDecimal getDay3TempMin() 
    {
        return day3TempMin;
    }
    public void setDay4FxDate(Date day4FxDate) 
    {
        this.day4FxDate = day4FxDate;
    }

    public Date getDay4FxDate() 
    {
        return day4FxDate;
    }
    public void setDay4TempMax(BigDecimal day4TempMax) 
    {
        this.day4TempMax = day4TempMax;
    }

    public BigDecimal getDay4TempMax() 
    {
        return day4TempMax;
    }
    public void setDay4TempMin(BigDecimal day4TempMin) 
    {
        this.day4TempMin = day4TempMin;
    }

    public BigDecimal getDay4TempMin() 
    {
        return day4TempMin;
    }
    public void setCreatedAt(Date createdAt) 
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() 
    {
        return createdAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("city", getCity())
            .append("obsTime", getObsTime())
            .append("currentTemp", getCurrentTemp())
            .append("tempMax", getTempMax())
            .append("tempMin", getTempMin())
            .append("currentWeatherText", getCurrentWeatherText())
            .append("day1FxDate", getDay1FxDate())
            .append("day1TempMax", getDay1TempMax())
            .append("day1TempMin", getDay1TempMin())
            .append("day2FxDate", getDay2FxDate())
            .append("day2TempMax", getDay2TempMax())
            .append("day2TempMin", getDay2TempMin())
            .append("day3FxDate", getDay3FxDate())
            .append("day3TempMax", getDay3TempMax())
            .append("day3TempMin", getDay3TempMin())
            .append("day4FxDate", getDay4FxDate())
            .append("day4TempMax", getDay4TempMax())
            .append("day4TempMin", getDay4TempMin())
            .append("createdAt", getCreatedAt())
            .toString();
    }
}
