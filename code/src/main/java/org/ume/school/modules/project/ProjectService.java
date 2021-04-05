package org.ume.school.modules.project;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ume.school.modules.model.entity.MoneyType;
import org.ume.school.modules.model.entity.Project;
import org.ume.school.modules.model.entity.ProjectMoney;
import org.ume.school.modules.model.enums.ProjectRecommend;
import org.ume.school.modules.model.enums.ProjectStatus;
import org.ume.school.modules.moneytype.MoneyTypeRepository;
import org.ume.school.modules.project.money.ProjectMoneyRepository;
import org.ume.school.modules.user.money.project.UserMoneyProjectRepository;
import org.ume.school.modules.utils.PageUtils;

/**
 * Created by Zz on 2018/8/26.
 */
@Service
public class ProjectService {

    @Resource
    private ProjectRepository projectRepository;
    @Resource
    private ProjectMoneyRepository projectMoneyRepository;
    @Resource
    private MoneyTypeRepository moneyTypeRepository;
    @Resource
    private UserMoneyProjectRepository userMoneyProjectRepository;
    

    @Transactional
    public Project saveProgress(String projectId) {
        Project project = projectRepository.findOne(projectId);
        if(project!=null){
            project = parse(project);
            double allMoney = 0, money = 0;
            for(ProjectMoney pm : project.getProjectMoneys()){
                allMoney+=pm.getAllMoney();
                money +=pm.getMoney();
            }
            double progress = (money*100/allMoney);
            project.setProgress(progress);
            projectRepository.save(project);
        }
        return project;
    }
    
     @Transactional
     public Project submitProject(Project project) {
         project = projectRepository.saveAndFlush(project);
         return project;
     }
     
     @Transactional
     public void deleteProject(Project project) {
         projectRepository.delete(project);
     }

    public List<Project> findRecommend() {
        List<Project> list = projectRepository.findByIsRecommendAndStatus(ProjectRecommend.YES.getValue(),
                ProjectStatus.PUBLISHED.getValue());
        return list;
    }

    public List<Project> findByPublished() {
        List<Project> list = projectRepository.findByStatus(ProjectStatus.PUBLISHED.getValue());
        return list;
    }

    public Project findById(String projectId) {
        return projectRepository.findOne(projectId);
    }

    /**
     * 查询并分页
     */
    @SuppressWarnings("unchecked")
    public Page<Project> findAll(final Project condition, String offset, String limit) {        
        @SuppressWarnings("rawtypes")
        Specification specification = new Specification<Project>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.isNotEmpty((condition.getTitle()))) {
                    predicates.add(cb.like(root.get("title"), "%" + condition.getTitle() + "%"));
                }
                if (condition.getStatus()!=null) {
                    predicates.add(cb.equal(root.get("status"), condition.getStatus()));
                }
                if (condition.getStar()!=null) {
                    predicates.add(cb.equal(root.get("star"),  condition.getStar()));
                }
                if (condition.getIsRecommend()!=null) {
                    predicates.add(cb.equal(root.get("isRecommend"),  condition.getIsRecommend()));
                }                
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        PageRequest pr = PageUtils.compositePage(offset, limit, "", "");
        return projectRepository.findAll(specification, pr);
    }
    public List<Project> parse(List<Project> list){
        if(list!=null && !list.isEmpty()){
            for(Project item : list){
                item = this.parse(item);
            }
        }
        return list;
    }
    public Project parse(Project item){
        if(item!=null){
//            item.setUserCount(userMoneyProjectRepository.countByProjectId(item.getId()));
            item.setProjectMoneys(projectMoneyRepository.findByProjectId(item.getId()));
            item.setProjectUsers(userMoneyProjectRepository.findByProjectId(item.getId()));
            MoneyType mt = moneyTypeRepository.findOne(item.getMoneyTypeId());
            if(mt!=null){
                item.setMoneyTypeName(mt.getTypeName());
            }
        }
        return item;
    }
}
