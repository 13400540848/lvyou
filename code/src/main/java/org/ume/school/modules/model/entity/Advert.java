package org.ume.school.modules.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.ume.school.modules.model.enums.AdvertLocation;
import org.ume.school.modules.model.enums.AdvertStatus;

/**
 * 广告
 * Created by Zz on 2018/9/1.
 */
@Entity
@Table(name = "bs_advert")
public class Advert implements Serializable {
    
    /**	
     * Member Description
     */
    
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Column(name = "id", columnDefinition = "varchar(64) not null")
    @Id
    private String id;

    /**
     * 广告位  0首页轮播  1首页通知
     */
    @Column(name = "location", columnDefinition = "int default 0 not null")
    private Integer location;
    
    /**
     * 标题
     */
    @Column(name = "title", columnDefinition = "varchar(255) default null")
    private String title;

    /**
     * 说明
     */
    @Column(name = "description", columnDefinition = "varchar(512) default null")
    private String description;
    
    /**
     * 内容
     */
    @Column(name = "content", columnDefinition = "text")
    private String content;
    
    /**
     * 图片
     */
    @Column(name = "image", columnDefinition = "varchar(255) default null")
    private String image;
    
    /**
     * 链接
     */
    @Column(name = "link_url", columnDefinition = "varchar(255) default null")
    private String linkUrl;
    
    /**
     * 链接打开方式
     */
    @Column(name = "link_type", columnDefinition = "int default null")
    private Integer linkType;
    
    /**
     * 链接项目
     */
    @Column(name = "link_project", columnDefinition = "varchar(64) default null")
    private String linkProject;
    
    /**
     * 显示顺序
     */
    @Column(name = "show_order", columnDefinition = "int default null")
    private Integer showOrder;
    
    /**
     * 状态，0 待发布  1 已发布  2 下架
     */
    @Column(name = "status", columnDefinition = "int default 0")
    private Integer status = 0;
    
    /**
     * 开始时间
     */
    @Column(name = "start_time", columnDefinition = "datetime default null")
    private Date startTime;
    
    /**
     * 结束时间
     */
    @Column(name = "end_time", columnDefinition = "datetime default null")
    private Date endTime;
    
    /**
     * 添加时间
     */
    @Column(name = "create_time", columnDefinition = "datetime default null")
    private Date createTime;
    
    /**
     * 修改时间
     */
    @Column(name = "update_time", columnDefinition = "datetime default null")
    private Date updateTime;
    
    /**
     * 管理员ID
     */
    @Column(name = "admin_id", columnDefinition = "varchar(255) default null")
    private String adminId;

    /**
     * 管理员名称
     */
    @Column(name = "admin_name", columnDefinition = "varchar(255) default null")
    private String adminName;
    
    
    /**
     * 搜索时间
     */
    @Transient
    private Date searchTime = null;
    
    @Transient
    private String locationName = null;
    @Transient
    private String statusName = null;
        
    public String getStatusName() {
        if(this.status!=null)
            statusName = "";
        for(AdvertStatus p : AdvertStatus.values()){
            if(this.status.intValue() == p.getValue()){
                this.statusName = p.getText();
                break;
            }
        }
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getLocationName() {
        if(this.location!=null)
            locationName = "";
        for(AdvertLocation p : AdvertLocation.values()){
            if(this.location.intValue() == p.getValue()){
                this.locationName = p.getText();
                break;
            }
        }
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Advert() {
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public Integer getLinkType() {
        return linkType;
    }

    public void setLinkType(Integer linkType) {
        this.linkType = linkType;
    }

    public String getLinkProject() {
        return linkProject;
    }

    public void setLinkProject(String linkProject) {
        this.linkProject = linkProject;
    }

    public Integer getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public Date getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(Date searchTime) {
        this.searchTime = searchTime;
    }
    
}
