package org.ume.school.modules.project.money.send;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ume.school.modules.model.entity.ProjectMoneySend;
import org.ume.school.modules.moneytype.MoneyTypeRepository;

/**
 * Created by Zz on 2018/10/14.
 */
@Service
public class ProjectMoneySendService {

    @Resource
    private ProjectMoneySendRepository projectMoneySendRepository;
    
    @Resource
    private MoneyTypeRepository moneyTypeRepository;    

     @Transactional
     public ProjectMoneySend submit(ProjectMoneySend model) {
         model = projectMoneySendRepository.saveAndFlush(model);
         return model;
     }
     
     @Transactional
     public void deleteProjectMoney(ProjectMoneySend project) {
         projectMoneySendRepository.delete(project);
     }

    public List<ProjectMoneySend> findByProjectId(String projectId) {
        return projectMoneySendRepository.findByProjectIdOrderByCreateTimeDesc(projectId);
    }
    public ProjectMoneySend findById(String id) {
        return projectMoneySendRepository.findOne(id);
    }
    
    public List<ProjectMoneySend> parse(List<ProjectMoneySend> list){
        if(!list.isEmpty()){
            for(ProjectMoneySend item : list){
                item = this.parse(item);
            }
        }
        return list;
    }
    public ProjectMoneySend parse(ProjectMoneySend model){
        if(model!=null){
        }
        return model;
    }
}
