package org.ume.school.modules.advert;

import java.util.ArrayList;
import java.util.Date;
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
import org.ume.school.modules.model.entity.Advert;
import org.ume.school.modules.model.enums.AdvertLocation;
import org.ume.school.modules.model.enums.AdvertStatus;
import org.ume.school.modules.utils.PageUtils;

/**
 * Created by Zz on 2018/8/30.
 */
@Service
public class AdvertService {

    @Resource
    private AdvertRepository advertRepository;

     @Transactional
     public Advert save(Advert model) {
         return advertRepository.saveAndFlush(model);
     }
     
     @Transactional
     public void delete(Advert model) {
         advertRepository.delete(model);
     }
     
     /**
      * 查询并分页
      */
     @SuppressWarnings("unchecked")
     public Page<Advert> findAll(final Advert condition, String offset, String limit) {        
         @SuppressWarnings("rawtypes")
         Specification specification = new Specification<Advert>() {
             @Override
             public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                 List<Predicate> predicates = new ArrayList<>();
                 if (StringUtils.isNotEmpty((condition.getTitle()))) {
                     predicates.add(cb.like(root.get("title"), "%" + condition.getTitle() + "%"));
                 }
                 if (condition.getStatus()!=null) {
                     predicates.add(cb.equal(root.get("status"), condition.getStatus()));
                 }
                 if (condition.getLocation()!=null) {
                     predicates.add(cb.equal(root.get("location"),  condition.getLocation()));
                 }
                 if(condition.getSearchTime()!=null){
                     predicates.add(cb.lessThanOrEqualTo(root.get("startTime"),  condition.getSearchTime()));
                     predicates.add(cb.greaterThanOrEqualTo(root.get("endTime"),  condition.getSearchTime()));
                 }
                 return cb.and(predicates.toArray(new Predicate[predicates.size()]));
             }
         };
         PageRequest pr = PageUtils.compositePage(offset, limit, "showOrder", "ASC");
         return advertRepository.findAll(specification, pr);
     }
     
     public List<Advert> findIndexAdverts() {
         final Advert condition = new Advert();
         condition.setLocation(AdvertLocation.INDEX_PLAY.getValue());
         condition.setStatus(AdvertStatus.PUBLISHED.getValue());
         condition.setSearchTime(new Date());
         Page<Advert> pages = findAll(condition, "1", "20"); 
         return pages.getContent();
     }
     
     public List<Advert> findNoticeAdverts() {
         final Advert condition = new Advert();
         condition.setLocation(AdvertLocation.INDEX_NOTICE.getValue());
         condition.setStatus(AdvertStatus.PUBLISHED.getValue());
         condition.setSearchTime(new Date());
         Page<Advert> pages = findAll(condition, "1", "20"); 
         return pages.getContent();
     }
     
     public List<Advert> findOfficialNoticeAdverts() {
         final Advert condition = new Advert();
         condition.setLocation(AdvertLocation.INDEX_OFFICIAL_NOTICE.getValue());
         condition.setStatus(AdvertStatus.PUBLISHED.getValue());
         condition.setSearchTime(new Date());
         Page<Advert> pages = findAll(condition, "1", "5"); 
         return pages.getContent();
     }
     
     public List<Advert> findLatestActiveAdverts() {
         final Advert condition = new Advert();
         condition.setLocation(AdvertLocation.INDEX_LATEST_ACTIVE.getValue());
         condition.setStatus(AdvertStatus.PUBLISHED.getValue());
         condition.setSearchTime(new Date());
         Page<Advert> pages = findAll(condition, "1", "5"); 
         return pages.getContent();
     }
     
     public List<Advert> findLinkAdverts() {
         final Advert condition = new Advert();
         condition.setLocation(AdvertLocation.INDEX_LINK.getValue());
         condition.setStatus(AdvertStatus.PUBLISHED.getValue());
         condition.setSearchTime(new Date());
         Page<Advert> pages = findAll(condition, "1", "50"); 
         return pages.getContent();
     }
     
     
     
     public Advert findAboutUsAdvert() {
         final Advert condition = new Advert();
         condition.setLocation(AdvertLocation.ABOUT_US.getValue());
         condition.setStatus(AdvertStatus.PUBLISHED.getValue());
         condition.setSearchTime(new Date());
         Page<Advert> pages = findAll(condition, "1", "1"); 
         List<Advert> list =  pages.getContent();
         return list.isEmpty() ? null : list.get(0);
     }

    public List<Advert> findList() {
        return advertRepository.findAll();
    }

    public Advert findById(String id) {
        return advertRepository.findOne(id);
    }
}
