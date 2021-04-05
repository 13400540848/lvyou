package com.bluesimon.wbf.modules.dict;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ume.school.modules.utils.PageUtils;

import com.bluesimon.wbf.RequestPager;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.enums.YesNoEnum;
import com.bluesimon.wbf.modules.role.RoleEntity;

/**
 * Created by Zz on 2021/3/20.
 */
@Service
public class DictService {

    @Resource
    private DictRepository dictRepository;
    
    @SuppressWarnings("unchecked")
    public List<DictEntity> getByCodes(List<String> codes) {
        return dictRepository.findByCodeIn(codes);
    }

    
    /**
     * 查询并分页
     */
    @SuppressWarnings("unchecked")
    public Page<DictEntity> findAll(RequestPager<DictEntity> req) {    
        final DictEntity condition = req.getCondition();
        @SuppressWarnings("rawtypes")
        Specification specification = new Specification<DictEntity>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.isNotEmpty((condition.getName()))) {
                    predicates.add(cb.like(root.get("name"), "%" + condition.getName() + "%"));
                }
                if (StringUtils.isNotEmpty((condition.getCode()))) {
                    predicates.add(cb.like(root.get("code"), "%" + condition.getCode() + "%"));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        PageRequest pr = PageUtils.compositePage(req.getPageIndex(), req.getPageSize(), "", "");
        return dictRepository.findAll(specification, pr);
    }
    
    @Transactional
    public Response<DictEntity> get(Long id) {
        DictEntity db = dictRepository.findOne(id);
        if(db==null){
            return new Response<>(Response.NORMAL, "字典不存在");
        }
        return new Response<>(db);
    }

    @Transactional
    public Response<DictEntity> add(DictEntity entity) {
        int count = dictRepository.countByCode(entity.getCode());
        if(count > 0){
            return new Response<>(Response.NORMAL, "编号已存在");
        }
        entity.setCreateTime(new Date());
        entity = dictRepository.saveAndFlush(entity);
        return new Response<>(entity);
    }
    
    @Transactional
    public Response<DictEntity> edit(DictEntity entity) {
        DictEntity db = dictRepository.findOne(entity.getId());
        if(db==null){
            return new Response<>(Response.NORMAL, "字典不存在");
        }
        int count = dictRepository.countByCodeAndIdNot(entity.getCode(), entity.getId());
        if(count > 0){
            return new Response<>(Response.NORMAL, "编码已存在");
        }
        dictRepository.saveAndFlush(entity);
        return new Response<>();
    }
    
    @Transactional
    public Response<DictEntity> delete(Long id) {
        DictEntity db = dictRepository.findOne(id);
        if(db==null){
            return new Response<>(Response.NORMAL, "字典不存在");
        }
        dictRepository.delete(id);
        return new Response<>();
    }
}
