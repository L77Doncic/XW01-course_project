package com.xm01.cardata.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.xm01.common.annotation.Excel;
import com.xm01.common.core.domain.BaseEntity;

/**
 * 违法信息对象 xm01_cardata
 * 
 * @author ruoyi
 * @date 2025-01-13
 */
public class Xm01Cardata extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 车牌号 */
    @Excel(name = "车牌号")
    private String carNumber;

    /** 车辆类型 */
    @Excel(name = "车辆类型")
    private String carType;

    /** 违章类型 */
    @Excel(name = "违章类型")
    private String trafficType;

    /** 位置 */
    @Excel(name = "位置")
    private String location;

    /** 时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date time;

    /** 车速 */
    @Excel(name = "车速")
    private Long carSpeed;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCarNumber(String carNumber) 
    {
        this.carNumber = carNumber;
    }

    public String getCarNumber() 
    {
        return carNumber;
    }
    public void setCarType(String carType) 
    {
        this.carType = carType;
    }

    public String getCarType() 
    {
        return carType;
    }
    public void setTrafficType(String trafficType) 
    {
        this.trafficType = trafficType;
    }

    public String getTrafficType() 
    {
        return trafficType;
    }
    public void setLocation(String location) 
    {
        this.location = location;
    }

    public String getLocation() 
    {
        return location;
    }
    public void setTime(Date time) 
    {
        this.time = time;
    }

    public Date getTime() 
    {
        return time;
    }
    public void setCarSpeed(Long carSpeed) 
    {
        this.carSpeed = carSpeed;
    }

    public Long getCarSpeed() 
    {
        return carSpeed;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("carNumber", getCarNumber())
            .append("carType", getCarType())
            .append("trafficType", getTrafficType())
            .append("location", getLocation())
            .append("time", getTime())
            .append("carSpeed", getCarSpeed())
            .toString();
    }
}
