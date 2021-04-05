package org.ume.school.modules.school;

import com.bluesimon.wbf.RequestPager;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.utils.StringUtil;
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
public class SchoolService {

    @Resource
    private SchoolRepository schoolRepository;

    @SuppressWarnings("unchecked")
    public SchoolEntity getByCode(final String code) {
        @SuppressWarnings("rawtypes")
        Specification specification = new Specification<SchoolEntity>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.equal(root.get("code"), code));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return schoolRepository.findOne(specification);
    }
    
    @SuppressWarnings("unchecked")
    public SchoolEntity getByCodeNotId(final String code, final Long id) {
        @SuppressWarnings("rawtypes")
        Specification specification = new Specification<SchoolEntity>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.equal(root.get("code"), code));
                predicates.add(cb.notEqual(root.get("id"), id));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return schoolRepository.findOne(specification);
    }
    
    @Transactional
    public List<SchoolEntity> getAll() {
        return schoolRepository.findAll();
    }
    
    /**
     * 查询并分页
     */
    @SuppressWarnings("unchecked")
    public Page<SchoolEntity> findAll(RequestPager<SchoolEntity> req) {
        final SchoolEntity condition = req.getCondition();
        @SuppressWarnings("rawtypes")
        Specification specification = new Specification<SchoolEntity>() {
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
        return schoolRepository.findAll(specification, pr);
    }
    
    @Transactional
    public Response<SchoolEntity> get(Long id) {
        SchoolEntity menu = schoolRepository.findOne(id);
        if(menu==null){
            return new Response<>(Response.NORMAL, "学校不存在");
        }
        return new Response<>(menu);
    }

    @Transactional
    public Response<SchoolEntity> add(SchoolEntity entity) {
        SchoolEntity menu = this.getByCode(entity.getCode());
        if(menu!=null){
            return new Response<>(Response.NORMAL, "编号已存在");
        }
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        entity = schoolRepository.saveAndFlush(entity);
        return new Response<>(entity);
    }
    
    @Transactional
    public Response<SchoolEntity> edit(SchoolEntity entity) {
        SchoolEntity menu = schoolRepository.findOne(entity.getId());
        if(menu==null){
            return new Response<>(Response.NORMAL, "学校不存在");
        }
        menu = this.getByCodeNotId(entity.getCode(), entity.getId());
        if(menu!=null){
            return new Response<>(Response.NORMAL, "编号已存在");
        }
        entity.setUpdateTime(new Date());
        entity = schoolRepository.saveAndFlush(entity);
        return new Response<SchoolEntity>(entity);
    }
    
    @Transactional
    public Response<SchoolEntity> delete(Long id) {
        SchoolEntity menu = schoolRepository.findOne(id);
        if(menu==null){
            return new Response<>(Response.NORMAL, "学校不存在");
        }
//        List<SchoolMenuEntity> rms = schoolMenuService.getBySchoolId(id);
//        if(!StringUtil.isEmpty(rms)){
//            return new Response<>(Response.NORMAL, "请先删除学校关联的菜单！");
//        }
        schoolRepository.delete(id);
        return new Response<SchoolEntity>();
    }
}
