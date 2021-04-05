package org.ume.school.modules.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 项目
 * Created by Zz on 2018/8/26.
 */
@Entity
@Table(name = "bs_project")
public class Project implements Serializable {
    
    /**	
     * Member Description
     */
    
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Column(name = "id", columnDefinition = "varchar(64) NOT NULL COMMENT '编号'")
    @Id
    private String id;

    /**
     * 名称
     */
    @Column(name = "title", columnDefinition = "varchar(255) DEFAULT NULL COMMENT '名称'")
    private String title;

    /**
     * 说明
     */
    @Column(name = "description", columnDefinition = "varchar(1024) DEFAULT NULL COMMENT '说明'")
    private String description;
    
    /**
     * 内容
     */
    @Column(name = "content", columnDefinition = "text COMMENT '内容'")
    private String content;
    
    /**
     * 封面
     */
    @Column(name = "image", columnDefinition = "varchar(255) DEFAULT NULL COMMENT '封面'")
    private String image;
        
    /**
     * 评级 1-5 星
     */
    @Column(name = "star", columnDefinition = "INT default 1 COMMENT '评级 1-5 星'")
    private Integer star = 5;
    
    /**
     * 投资币种
     */
    @Column(name = "money_type", columnDefinition = "varchar(64) NOT NULL COMMENT '投资币种'")
    private String moneyTypeId;
    
    /**
     * 手续费
     */
    @Column(name = "broke_percent", columnDefinition = "float DEFAULT 0 COMMENT '手续费'")
    private float  brokePercent;

    /**
     * 当前进度 0-100
     */
    @Column(name = "progress", columnDefinition = "DOUBLE(10,2) default 0 COMMENT '当前进度'")
    private Double progress = (double)0;
    
    /**
     * 投资用户数
     */
    @Column(name = "user_count", columnDefinition = "int default 0 COMMENT '投资用户数'")
    private Integer userCount = 0;
    
    /**
     * 状态，0 待发布  1 已发布（未开始\进行中\时间结束\已筹满）  2 下架
     */
    @Column(name = "status", columnDefinition = "INT default 0")
    private Integer status = 0;
    
    /**
     * 首页推荐
     */
    @Column(name = "is_recommend", columnDefinition = "INT default 0")
    private Integer isRecommend = 0;
    
    /**
     * 开始时间
     */
    @Column(name = "start_time", columnDefinition = "datetime DEFAULT NULL")
    private Date startTime;
    
    /**
     * 结束时间
     */
    @Column(name = "end_time", columnDefinition = "datetime DEFAULT NULL")
    private Date endTime;
    
    /**
     * 添加时间
     */
    @Column(name = "create_time", columnDefinition = "datetime DEFAULT NULL")
    private Date createTime;
    
    /**
     * 修改时间
     */
    @Column(name = "update_time", columnDefinition = "datetime DEFAULT NULL")
    private Date updateTime;
    
    /**
     * 管理员ID
     */
    @Column(name = "admin_id", columnDefinition = "varchar(255) DEFAULT NULL")
    private String adminId;

    /**
     * 管理员名称
     */
    @Column(name = "admin_name", columnDefinition = "varchar(255) DEFAULT NULL")
    private String adminName;
    
    @Transient
    private String moneyTypeName;
    @Transient
    private String moneyTypes;
    @Transient
    private String moneyAll;
    @Transient
    private String moneyLimit;
    
    @Transient
    private Integer timeType = null;
    @Transient
    private String timeTypeName = null;
    @Transient
    private List<ProjectMoney> projectMoneys = null;
    @Transient
    private List<UserMoneyProject> projectUsers = null;

    public List<ProjectMoney> getProjectMoneys() {
        return projectMoneys;
    }

    public void setProjectMoneys(List<ProjectMoney> projectMoneys) {
        this.projectMoneys = projectMoneys;
    }    

    public List<UserMoneyProject> getProjectUsers() {
        return projectUsers;
    }

    public void setProjectUsers(List<UserMoneyProject> projectUsers) {
        this.projectUsers = projectUsers;
    }

    public String getTimeTypeName() {
        Date now = new Date();
        if(this.startTime.getTime() > now.getTime()){
            timeTypeName = "即将开始";//即将开始
        }
        else if(this.endTime.getTime() >= now.getTime()){
            timeTypeName = "进行中"; //进行中
        }else{
            timeTypeName = "已结束"; //已截止
        }
        return timeTypeName;
    }

    public void setTimeTypeName(String timeTypeName) {
        this.timeTypeName = timeTypeName;
    }

    public Integer getTimeType() {
        Date now = new Date();
        if(this.startTime.getTime() > now.getTime()){
            timeType = 0;//即将开始
        }
        else if(this.endTime.getTime() >= now.getTime()){
            timeType = 1; //进行中
        }else{
            timeType = 2; //已截止
        }
        return timeType;
    }

    public void setTimeType(Integer timeType) {
        this.timeType = timeType;
    }

    public Project() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

    public String getMoneyAll() {
        if(this.projectMoneys!=null && !this.projectMoneys.isEmpty()){
            moneyAll = "";
            for(ProjectMoney m:this.projectMoneys){
                moneyAll += m.getAllMoney()+""+m.getTypeName()+"、";
            }
            moneyAll = moneyAll.substring(0, moneyAll.length()-1);
        }
        return moneyAll;
    }

    public void setMoneyAll(String moneyAll) {
        this.moneyAll = moneyAll;
    }
    
    public String getMoneyLimit() {
        if(this.projectMoneys!=null && !this.projectMoneys.isEmpty()){
            moneyLimit = "";
            for(ProjectMoney m:this.projectMoneys){
                moneyLimit += m.getMin()+"-"+m.getMax()+m.getTypeName()+"、";
            }
            moneyLimit = moneyLimit.substring(0, moneyLimit.length()-1);
        }
        return moneyLimit;
    }

    public void setMoneyLimit(String moneyLimit) {
        this.moneyLimit = moneyLimit;
    }

    public String getMoneyTypes() {
        moneyTypes = "";
        if(this.projectMoneys!=null && !this.projectMoneys.isEmpty()){            
            for(ProjectMoney m:this.projectMoneys){
                moneyTypes += m.getTypeName()+"、";
            }
            moneyTypes = moneyTypes.substring(0, moneyTypes.length()-1);
        }
        return moneyTypes;
    }

    public void setMoneyTypes(String moneyTypes) {
        this.moneyTypes = moneyTypes;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
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

    public Integer getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Integer isRecommend) {
        this.isRecommend = isRecommend;
    }

    public String getMoneyTypeId() {
        return moneyTypeId;
    }

    public void setMoneyTypeId(String moneyTypeId) {
        this.moneyTypeId = moneyTypeId;
    }

    public String getMoneyTypeName() {
        return moneyTypeName;
    }

    public void setMoneyTypeName(String moneyTypeName) {
        this.moneyTypeName = moneyTypeName;
    }

    public float getBrokePercent() {
        return brokePercent;
    }

    public void setBrokePercent(float brokePercent) {
        this.brokePercent = brokePercent;
    }

    
    
}
