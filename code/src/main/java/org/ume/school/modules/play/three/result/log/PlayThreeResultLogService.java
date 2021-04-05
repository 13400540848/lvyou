package org.ume.school.modules.play.three.result.log;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ume.school.modules.model.entity.PlayThreeResultLog;
import org.ume.school.modules.utils.PageUtils;

/**
 * Created by Zz on 2018/11/1.
 */
@Service("playThreeResultLogService")
public class PlayThreeResultLogService {

    @Resource
    private PlayThreeResultLogRepository playThreeResultLogRepository;

    @Transactional
    public PlayThreeResultLog submit(PlayThreeResultLog model) {
        return playThreeResultLogRepository.saveAndFlush(model);
    }

    public PlayThreeResultLog get(String id) {
        return playThreeResultLogRepository.findOne(id);
    }
    
    public Page<PlayThreeResultLog> findAll(final PlayThreeResultLog condition, String offset, String limit){
    	return findAll(condition, offset, limit, "playTime", "Asc");
    }
    
    public Page<PlayThreeResultLog> findAllDesc(final PlayThreeResultLog condition, String offset, String limit){
    	return findAll(condition, offset, limit, "playTime", "Desc");
    }
    
    /**
     * 查询并分页
     */
    @SuppressWarnings("unchecked")
    public Page<PlayThreeResultLog> findAll(final PlayThreeResultLog condition, String offset, String limit, String orderBy, String direction) {
        @SuppressWarnings("rawtypes")
        Specification specification = new Specification<PlayThreeResultLog>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (condition.getPlayTime() != null) {
                    predicates.add(cb.equal(root.get("playTime"), condition.getPlayTime()));
                }
                if(condition.getPlayTimeStart()!=null){
                    predicates.add(cb.greaterThanOrEqualTo(root.get("playTime"), condition.getPlayTimeStart()));
                }
                if(condition.getPlayTimeEnd()!=null){
                    predicates.add(cb.lessThanOrEqualTo(root.get("playTime"), condition.getPlayTimeEnd()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        PageRequest pr = PageUtils.compositePage(offset, limit, orderBy, direction);
        return playThreeResultLogRepository.findAll(specification, pr);
    }
    
    public PlayThreeResultLog getFrontLog() {
        final PlayThreeResultLog condition = new PlayThreeResultLog();
        Page<PlayThreeResultLog> pages = findAll(condition, "1", "1", "playTime", "Desc");
        return pages != null && pages.getContent()!=null && pages.getContent().size() > 0 ? pages.getContent().get(0) : null;
    }
}
