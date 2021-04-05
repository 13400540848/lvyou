package com.bluesimon.wbf.modules.org;

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
import org.ume.school.modules.utils.PageUtils;

import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.enums.YesNoEnum;

/**
 * Created by Zz on 2021/3/20.
 */
@Service
public class OrgService {

//    @Resource
//    private OrgRepository attachmentRepository;

//    @SuppressWarnings("unchecked")
//    public AttachmentEntity getByCode(final String code) {
//        @SuppressWarnings("rawtypes")
//        Specification specification = new Specification<AttachmentEntity>() {
//            @Override
//            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
//                List<Predicate> predicates = new ArrayList<>();
//                predicates.add(cb.equal(root.get("code"), code));
//                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
//            }
//        };
//        return attachmentRepository.findOne(specification);
//    }
//    
//    @SuppressWarnings("unchecked")
//    public AttachmentEntity getByCodeNotId(final String code, final Long id) {
//        @SuppressWarnings("rawtypes")
//        Specification specification = new Specification<AttachmentEntity>() {
//            @Override
//            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
//                List<Predicate> predicates = new ArrayList<>();
//                predicates.add(cb.equal(root.get("code"), code));
//                predicates.add(cb.notEqual(root.get("id"), id));
//                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
//            }
//        };
//        return attachmentRepository.findOne(specification);
//    }
//    
//    /**
//     * 查询并分页
//     */
//    @SuppressWarnings("unchecked")
//    public Page<AttachmentEntity> findAll(final AttachmentEntity condition, String offset, String limit) {        
//        @SuppressWarnings("rawtypes")
//        Specification specification = new Specification<AttachmentEntity>() {
//            @Override
//            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
//                List<Predicate> predicates = new ArrayList<>();
//                if (StringUtils.isNotEmpty((condition.getName()))) {
//                    predicates.add(cb.like(root.get("name"), "%" + condition.getName() + "%"));
//                }
//                if (StringUtils.isNotEmpty((condition.getCode()))) {
//                    predicates.add(cb.like(root.get("code"), "%" + condition.getCode() + "%"));
//                }
//                if (condition.getIsHide()!=null) {
//                    predicates.add(cb.equal(root.get("isHide"), condition.getIsHide()));
//                }
//                if (condition.getPId()!=null) {
//                    predicates.add(cb.equal(root.get("pId"), condition.getPId()));
//                }
//                if (condition.getType()!=null) {
//                    predicates.add(cb.equal(root.get("type"), condition.getType()));
//                }
//                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
//            }
//        };
//        PageRequest pr = PageUtils.compositePage(offset, limit, "sort", "Asc");
//        return attachmentRepository.findAll(specification, pr);
//    }
//    
//    @Transactional
//    public Response<AttachmentEntity> get(Long id) {
//        AttachmentEntity menu = attachmentRepository.findOne(id);
//        if(menu==null){
//            return new Response<>(Response.NORMAL, "菜单不存在");
//        }
//        return new Response<AttachmentEntity>(menu);
//    }
//
//    @Transactional
//    public Response<AttachmentEntity> add(AttachmentEntity entity) {
//        AttachmentEntity menu = this.getByCode(entity.getCode());
//        if(menu!=null){
//            return new Response<>(Response.NORMAL, "编码已存在");
//        }
//        entity.setOpenMode(entity.getOpenMode()==null?OpenModeEnum.DEFAULT.getValue():entity.getOpenMode());
//        entity.setIsHide(entity.getIsHide()==null?YesNoEnum.NO.getValue():entity.getIsHide());
//        entity.setPId(entity.getPId()==null?0:entity.getPId());
//        entity.setType(entity.getType()==null?AttachmentTypeEnum.NAV.getValue():entity.getType());
//        entity.setSort(entity.getSort()==null?0:entity.getSort());
//        entity.setCreateTime(new Date());
//        entity = attachmentRepository.saveAndFlush(entity);
//        return new Response<>(entity);
//    }
//    
//    @Transactional
//    public Response<AttachmentEntity> edit(AttachmentEntity entity) {
//        AttachmentEntity menu = attachmentRepository.findOne(entity.getId());
//        if(menu!=null){
//            return new Response<>(Response.NORMAL, "菜单已存在");
//        }
//        menu = this.getByCodeNotId(entity.getCode(), entity.getId());
//        if(menu!=null){
//            return new Response<>(Response.NORMAL, "编码已存在");
//        }
//        entity = attachmentRepository.saveAndFlush(entity);
//        return new Response<AttachmentEntity>(entity);
//    }
//    
//    @Transactional
//    public Response<AttachmentEntity> delete(Long id) {
//        AttachmentEntity menu = attachmentRepository.findOne(id);
//        if(menu==null){
//            return new Response<>(Response.NORMAL, "菜单不存在");
//        }
//        attachmentRepository.delete(id);
//        return new Response<AttachmentEntity>();
//    }
}
