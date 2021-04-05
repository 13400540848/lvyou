package org.ume.school.modules.project.money;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ume.school.modules.model.entity.MoneyType;
import org.ume.school.modules.model.entity.ProjectMoney;
import org.ume.school.modules.moneytype.MoneyTypeRepository;

/**
 * Created by Zz on 2018/9/1.
 */
@Service
public class ProjectMoneyService {

    @Resource
    private ProjectMoneyRepository projectMoneyRepository;
    
    @Resource
    private MoneyTypeRepository moneyTypeRepository;    

     @Transactional
     public ProjectMoney submitProjectMoney(ProjectMoney model) {
         model = projectMoneyRepository.saveAndFlush(model);
         return model;
     }
     
     @Transactional
     public void deleteProjectMoney(ProjectMoney project) {
         projectMoneyRepository.delete(project);
     }

    public List<ProjectMoney> findByProjectId(String projectId) {
        return projectMoneyRepository.findByProjectId(projectId);
    }
    
    public List<ProjectMoney> findByTypeId(String typeId) {
        return projectMoneyRepository.findByTypeId(typeId);
    }
    
    public ProjectMoney findByProjectIdAndTypeId(String projectId, String typeId) {
        List<ProjectMoney> list = projectMoneyRepository.findByProjectIdAndTypeId(projectId, typeId);
        return list.isEmpty() ? null : list.get(0); 
    }

    public ProjectMoney findById(String id) {
        return projectMoneyRepository.findOne(id);
    }
    
    public ProjectMoney save(ProjectMoney entity) {
        return projectMoneyRepository.saveAndFlush(entity);
    }
    
    public void delete(String id) {
        projectMoneyRepository.delete(id);
    }
    
    public List<ProjectMoney> parse(List<ProjectMoney> list){
        if(!list.isEmpty()){
            for(ProjectMoney item : list){
                item = this.parse(item);
            }
        }
        return list;
    }
    public ProjectMoney parse(ProjectMoney model){
        if(model!=null){
            MoneyType t = moneyTypeRepository.findOne(model.getTypeId());
            if(t!=null){
                model.setTypeName(t.getTypeName());
            }
        }
        return model;
    }
}
