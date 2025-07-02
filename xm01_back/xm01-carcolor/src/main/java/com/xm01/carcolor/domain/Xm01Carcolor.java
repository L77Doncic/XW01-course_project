package com.xm01.carcolor.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.xm01.common.annotation.Excel;
import com.xm01.common.core.domain.BaseEntity;

/**
 * carcolor对象 xm01_carcolor
 * 
 * @author ruoyi
 * @date 2025-01-13
 */
public class Xm01Carcolor extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 车牌号 */
    @Excel(name = "车牌号")
    private String plateNumber;

    /** 车牌颜色 */
    @Excel(name = "车牌颜色")
    private String plateColor;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPlateNumber(String plateNumber) 
    {
        this.plateNumber = plateNumber;
    }

    public String getPlateNumber() 
    {
        return plateNumber;
    }
    public void setPlateColor(String plateColor) 
    {
        this.plateColor = plateColor;
    }

    public String getPlateColor() 
    {
        return plateColor;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("plateNumber", getPlateNumber())
            .append("plateColor", getPlateColor())
            .toString();
    }
}
