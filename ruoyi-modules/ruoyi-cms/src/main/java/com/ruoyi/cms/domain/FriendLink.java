package com.ruoyi.cms.domain;


import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 友情链接对象 cms_friend_link
 *
 * @author wujiyue
 * @date 2019-11-16
 */
public class FriendLink extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 友情链接ID */
    private Long id;

    /** 友链名称 */
    @Excel(name = "友链名称")
    private String name;

    /** 链接 */
    @Excel(name = "链接")
    private String link;
    @Excel(name = "站点描述")
    /** description*/
    private String description;
    /** logo*/
    private String logo;
    /** email*/
    private String email;
    /** 状态 */
    @Excel(name = "状态")
    private Integer auditState;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setLink(String link)
    {
        this.link = link;
    }

    public String getLink()
    {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setAuditState(Integer auditState)
    {
        this.auditState = auditState;
    }

    public Integer getAuditState()
    {
        return auditState;
    }

    @Override
    public String toString() {
        return "FriendLink{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", logo='" + logo + '\'' +
                ", email='" + email + '\'' +
                ", auditState=" + auditState +
                '}';
    }
}
