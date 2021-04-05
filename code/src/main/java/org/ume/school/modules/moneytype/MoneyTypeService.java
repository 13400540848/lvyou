package org.ume.school.modules.moneytype;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ume.school.modules.model.entity.MoneyType;
import org.ume.school.modules.model.enums.MoneyTypeMode;

/**
 * Created by Zz on 2018/8/30.
 */
@Service("moneyTypeService")
public class MoneyTypeService {

    @Resource
    private MoneyTypeRepository moneyTypeRepository;

     @Transactional
     public MoneyType submitMoneyType(MoneyType project) {
         project = moneyTypeRepository.saveAndFlush(project);
         return project;
     }
     
     @Transactional
     public void deleteMoneyType(MoneyType project) {
         moneyTypeRepository.delete(project);
     }

    public List<MoneyType> findList() {
        return moneyTypeRepository.findAll();
    }
    
    public MoneyType findBySystem() {
        List<MoneyType> list = moneyTypeRepository.findByTypeMode(MoneyTypeMode.SYSTEM.getValue());
        return list!=null && !list.isEmpty() ? list.get(0) : null;
    }

    public MoneyType findById(String id) {
        return moneyTypeRepository.findOne(id);
    }
}
