package com.xm01.weibo.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.xm01.common.annotation.Excel;
import com.xm01.common.core.domain.BaseEntity;

/**
 * 微博热搜对象 xm01_wbhot
 * 
 * @author xm01
 * @date 2025-06-15
 */
public class Xm01Wbhot extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 排名 */
    @Excel(name = "排名")
    private Long realpos;

    /** 图标描述颜色，十六进制颜色代码 */
    private String iconDescColor;

    /** 内容 */
    @Excel(name = "内容")
    private String note;

    /** 参与人数或相关数值 */
    @Excel(name = "参与人数或相关数值")
    private Long num;

    /** 话题标识，1表示是话题 */
    private Integer topicFlag;

    /** 表情符号链接 */
    private String emoticon;

    /** 话题方案 */
    private String wordScheme;

    /** 小图标描述颜色，十六进制颜色代码 */
    private String smallIconDescColor;

    /** 图标链接 */
    private String icon;

    /** 图标宽度 */
    private Long iconWidth;

    /** 图标高度 */
    private Long iconHeight;

    /** 标签名称 */
    private String labelName;

    /** 标志位，1表示有效 */
    private Integer flag;

    /** 小图标描述 */
    private String smallIconDesc;

    /** 话题词汇 */
    private String word;

    /** 话题类型 */
    @Excel(name = "话题类型")
    private String iconDesc;

    /** 排名 */
    private Long rank;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setRealpos(Long realpos) 
    {
        this.realpos = realpos;
    }

    public Long getRealpos() 
    {
        return realpos;
    }
    public void setIconDescColor(String iconDescColor) 
    {
        this.iconDescColor = iconDescColor;
    }

    public String getIconDescColor() 
    {
        return iconDescColor;
    }
    public void setNote(String note) 
    {
        this.note = note;
    }

    public String getNote() 
    {
        return note;
    }
    public void setNum(Long num) 
    {
        this.num = num;
    }

    public Long getNum() 
    {
        return num;
    }
    public void setTopicFlag(Integer topicFlag) 
    {
        this.topicFlag = topicFlag;
    }

    public Integer getTopicFlag() 
    {
        return topicFlag;
    }
    public void setEmoticon(String emoticon) 
    {
        this.emoticon = emoticon;
    }

    public String getEmoticon() 
    {
        return emoticon;
    }
    public void setWordScheme(String wordScheme) 
    {
        this.wordScheme = wordScheme;
    }

    public String getWordScheme() 
    {
        return wordScheme;
    }
    public void setSmallIconDescColor(String smallIconDescColor) 
    {
        this.smallIconDescColor = smallIconDescColor;
    }

    public String getSmallIconDescColor() 
    {
        return smallIconDescColor;
    }
    public void setIcon(String icon) 
    {
        this.icon = icon;
    }

    public String getIcon() 
    {
        return icon;
    }
    public void setIconWidth(Long iconWidth) 
    {
        this.iconWidth = iconWidth;
    }

    public Long getIconWidth() 
    {
        return iconWidth;
    }
    public void setIconHeight(Long iconHeight) 
    {
        this.iconHeight = iconHeight;
    }

    public Long getIconHeight() 
    {
        return iconHeight;
    }
    public void setLabelName(String labelName) 
    {
        this.labelName = labelName;
    }

    public String getLabelName() 
    {
        return labelName;
    }
    public void setFlag(Integer flag) 
    {
        this.flag = flag;
    }

    public Integer getFlag() 
    {
        return flag;
    }
    public void setSmallIconDesc(String smallIconDesc) 
    {
        this.smallIconDesc = smallIconDesc;
    }

    public String getSmallIconDesc() 
    {
        return smallIconDesc;
    }
    public void setWord(String word) 
    {
        this.word = word;
    }

    public String getWord() 
    {
        return word;
    }
    public void setIconDesc(String iconDesc) 
    {
        this.iconDesc = iconDesc;
    }

    public String getIconDesc() 
    {
        return iconDesc;
    }
    public void setRank(Long rank) 
    {
        this.rank = rank;
    }

    public Long getRank() 
    {
        return rank;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("realpos", getRealpos())
            .append("iconDescColor", getIconDescColor())
            .append("note", getNote())
            .append("num", getNum())
            .append("topicFlag", getTopicFlag())
            .append("emoticon", getEmoticon())
            .append("wordScheme", getWordScheme())
            .append("smallIconDescColor", getSmallIconDescColor())
            .append("icon", getIcon())
            .append("iconWidth", getIconWidth())
            .append("iconHeight", getIconHeight())
            .append("labelName", getLabelName())
            .append("flag", getFlag())
            .append("smallIconDesc", getSmallIconDesc())
            .append("word", getWord())
            .append("iconDesc", getIconDesc())
            .append("rank", getRank())
            .toString();
    }
}
