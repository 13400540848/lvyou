package com.bluesimon.wbf.modules.dict.item;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.CriteriaBuilder.In;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ume.school.modules.utils.PageUtils;

import com.bluesimon.wbf.RequestPager;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.modules.dict.DictEntity;

/**
 * Created by Zz on 2021/3/20.
 */
@Service
public class DictItemService {

    @Resource
    private DictItemRepository dictItemRepository;
    
    @SuppressWarnings("unchecked")
    public DictItemEntity getByCode(final String code) {
        @SuppressWarnings("rawtypes")
        Specification specification = new Specification<DictItemEntity>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.equal(root.get("code"), code));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return dictItemRepository.findOne(specification);
    }
    
    @SuppressWarnings("unchecked")
    public DictItemEntity getByCodeNotId(final String code, final Long id) {
        @SuppressWarnings("rawtypes")
        Specification specification = new Specification<DictItemEntity>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.equal(root.get("code"), code));
                predicates.add(cb.notEqual(root.get("id"), id));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return dictItemRepository.findOne(specification);
    }
    @Transactional
    public List<DictItemEntity> getByDictIds(final List<Long> ids) {
        return dictItemRepository.findByDictIdInOrderBySortAsc(ids);
    }
    
    /**
     * 查询并分页
     */
    @SuppressWarnings("unchecked")
    public Page<DictItemEntity> findAll(RequestPager<DictItemEntity> req) {    
        final DictItemEntity condition = req.getCondition();
        @SuppressWarnings("rawtypes")
        Specification specification = new Specification<DictItemEntity>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.isNotEmpty((condition.getName()))) {
                    predicates.add(cb.like(root.get("name"), "%" + condition.getName() + "%"));
                }
                if (StringUtils.isNotEmpty((condition.getCode()))) {
                    predicates.add(cb.like(root.get("code"), "%" + condition.getCode() + "%"));
                }
                if (condition.getDictId()!=null) {
                    predicates.add(cb.equal(root.get("dictId"), condition.getDictId()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        PageRequest pr = PageUtils.compositePage(req.getPageIndex(), req.getPageSize(), "sort", "Asc");
        return dictItemRepository.findAll(specification, pr);
    }
    
    @Transactional
    public Response<DictItemEntity> get(Long id) {
        DictItemEntity menu = dictItemRepository.findOne(id);
        if(menu==null){
            return new Response<>(Response.NORMAL, "字典项不存在");
        }
        return new Response<DictItemEntity>(menu);
    }

    @Transactional
    public Response<DictItemEntity> add(DictItemEntity entity) {
        DictItemEntity menu = this.getByCode(entity.getCode());
        if(menu!=null){
            return new Response<>(Response.NORMAL, "编号已存在");
        }
        entity.setCreateTime(new Date());
        entity = dictItemRepository.saveAndFlush(entity);
        return new Response<>(entity);
    }
    
    @Transactional
    public Response<DictItemEntity> edit(DictItemEntity entity) {
        DictItemEntity menu = dictItemRepository.findOne(entity.getId());
        if(menu==null){
            return new Response<>(Response.NORMAL, "字典项不存在");
        }
        menu = this.getByCodeNotId(entity.getCode(), entity.getId());
        if(menu!=null){
            return new Response<>(Response.NORMAL, "编码已存在");
        }
        entity = dictItemRepository.saveAndFlush(entity);
        return new Response<DictItemEntity>(entity);
    }
    
    @Transactional
    public Response<DictItemEntity> delete(Long id) {
        DictItemEntity menu = dictItemRepository.findOne(id);
        if(menu==null){
            return new Response<>(Response.NORMAL, "字典项不存在");
        }
        dictItemRepository.delete(id);
        return new Response<DictItemEntity>();
    }
}
