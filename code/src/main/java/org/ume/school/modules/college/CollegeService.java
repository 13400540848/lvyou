package org.ume.school.modules.college;

import com.bluesimon.wbf.RequestPager;
import com.bluesimon.wbf.Response;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ume.school.modules.utils.PageUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Zz on 2021/3/21.
 */
@Service
public class CollegeService {

    @Resource
    private CollegeRepository collegeRepository;

    @SuppressWarnings("unchecked")
    public CollegeEntity getByCode(final String code) {
        @SuppressWarnings("rawtypes")
        Specification specification = new Specification<CollegeEntity>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.equal(root.get("code"), code));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return collegeRepository.findOne(specification);
    }
    
    @SuppressWarnings("unchecked")
    public CollegeEntity getByCodeNotId(final String code, final Long id) {
        @SuppressWarnings("rawtypes")
        Specification specification = new Specification<CollegeEntity>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.equal(root.get("code"), code));
                predicates.add(cb.notEqual(root.get("id"), id));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return collegeRepository.findOne(specification);
    }
    
    @Transactional
    public List<CollegeEntity> getAll() {
        return collegeRepository.findAll();
    }
    
    /**
     * 查询并分页
     */
    @SuppressWarnings("unchecked")
    public Page<CollegeEntity> findAll(RequestPager<CollegeEntity> req) {
        final CollegeEntity condition = req.getCondition();
        @SuppressWarnings("rawtypes")
        Specification specification = new Specification<CollegeEntity>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.isNotEmpty((condition.getName()))) {
                    predicates.add(cb.like(root.get("name"), "%" + condition.getName() + "%"));
                }
                if (StringUtils.isNotEmpty((condition.getCode()))) {
                    predicates.add(cb.like(root.get("code"), "%" + condition.getCode() + "%"));
                }
                if (condition.getStatus()!=null) {
                    predicates.add(cb.equal(root.get("status"), condition.getStatus()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        PageRequest pr = PageUtils.compositePage(req.getPageIndex(), req.getPageSize(), "", "");
        return collegeRepository.findAll(specification, pr);
    }
    
    @Transactional
    public Response<CollegeEntity> get(Long id) {
        CollegeEntity menu = collegeRepository.findOne(id);
        if(menu==null){
            return new Response<>(Response.NORMAL, "学院不存在");
        }
        return new Response<>(menu);
    }

    @Transactional
    public Response<CollegeEntity> add(CollegeEntity entity) {
        CollegeEntity menu = this.getByCode(entity.getCode());
        if(menu!=null){
            return new Response<>(Response.NORMAL, "编号已存在");
        }
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        entity = collegeRepository.saveAndFlush(entity);
        return new Response<>(entity);
    }
    
    @Transactional
    public Response<CollegeEntity> edit(CollegeEntity entity) {
        CollegeEntity menu = collegeRepository.findOne(entity.getId());
        if(menu==null){
            return new Response<>(Response.NORMAL, "学院不存在");
        }
        menu = this.getByCodeNotId(entity.getCode(), entity.getId());
        if(menu!=null){
            return new Response<>(Response.NORMAL, "编号已存在");
        }
        entity.setUpdateTime(new Date());
        entity = collegeRepository.saveAndFlush(entity);
        return new Response<CollegeEntity>(entity);
    }
    
    @Transactional
    public Response<CollegeEntity> delete(Long id) {
        CollegeEntity menu = collegeRepository.findOne(id);
        if(menu==null){
            return new Response<>(Response.NORMAL, "学院不存在");
        }
//        List<CollegeMenuEntity> rms = collegeMenuService.getByCollegeId(id);
//        if(!StringUtil.isEmpty(rms)){
//            return new Response<>(Response.NORMAL, "请先删除学院关联的菜单！");
//        }
        collegeRepository.delete(id);
        return new Response<CollegeEntity>();
    }
}
