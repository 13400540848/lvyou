package org.ume.school.modules.play.three.result;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
import org.ume.school.modules.model.entity.PlayThree;
import org.ume.school.modules.model.entity.PlayThreeResult;
import org.ume.school.modules.model.enums.PlayResultStatus;
import org.ume.school.modules.utils.PageUtils;

import com.bluesimon.wbf.Response;

/**
 * Created by Zz on 2018/11/1.
 */
@Service("playThreeResultService")
public class PlayThreeResultService {

    @Resource
    private PlayThreeResultRepository playThreeResultRepository;

    @Transactional
    public PlayThreeResult submit(PlayThreeResult model) {
        return playThreeResultRepository.saveAndFlush(model);
    }

    public PlayThreeResult get(String id) {
        return playThreeResultRepository.findOne(id);
    }
    
    public Page<PlayThreeResult> findAll(final PlayThreeResult condition, String offset, String limit){
        return findAll(condition, offset, limit, "playTime", "Asc");
    }
    
    public Page<PlayThreeResult> findAllDesc(final PlayThreeResult condition, String offset, String limit){
        return findAll(condition, offset, limit, "playTime", "Desc");
    }

    /**
     * 查询并分页
     */
    @SuppressWarnings("unchecked")
    public Page<PlayThreeResult> findAll(final PlayThreeResult condition, String offset, String limit, String orderBy, String direction) {
        @SuppressWarnings("rawtypes")
        Specification specification = new Specification<PlayThreeResult>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (condition.getStatus() != null) {
                    predicates.add(cb.equal(root.get("status"), condition.getStatus()));
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
        return playThreeResultRepository.findAll(specification, pr);
    }
    
    public PlayThreeResult getTodayOpenLast() {
        final PlayThreeResult query = new PlayThreeResult();
        query.setStatus(PlayResultStatus.FINISHED.getValue());
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());        
        query.setPlayTimeStart(Double.parseDouble(date+"000"));
        query.setPlayTimeEnd(Double.parseDouble(date+"999"));
        Page<PlayThreeResult> pages = findAllDesc(query, "1", "1");
        if(pages.getContent()!=null && pages.getContent().size()>0){
            return pages.getContent().get(0);
        }
        return null;
    }
    
    public PlayThreeResult getFrontFinishedResult() {
        final PlayThreeResult condition = new PlayThreeResult();
        condition.setStatus(PlayResultStatus.FINISHED.getValue());
        Page<PlayThreeResult> pages = findAllDesc(condition, "1", "1");
        return pages != null && pages.getContent()!=null && pages.getContent().size() > 0 ? pages.getContent().get(0) : null;
    }
    
    public PlayThreeResult getLatestResult() {
        final PlayThreeResult condition = new PlayThreeResult();
        Page<PlayThreeResult> pages = findAllDesc(condition, "1", "1");
        return pages != null && pages.getContent()!=null && pages.getContent().size() > 0 ? pages.getContent().get(0) : null;
    }
    
    public PlayThreeResult getWaitResult() {
        List<PlayThreeResult> list = playThreeResultRepository.findByStatusOrderByPlayTimeAsc(PlayResultStatus.WAIT.getValue());
        if( list != null && list.size() > 0){
            for(PlayThreeResult item : list){
                if(item.getEndTime().after(new Date())){
                    return item;
                }
            }
        }
        return null;
    }
    
    public  List<PlayThreeResult> getWaitList() {
        return playThreeResultRepository.findByStatusOrderByPlayTimeAsc(PlayResultStatus.WAIT.getValue());
    }

    /**
     * 生成78期记录（某一天）
     */
    public PlayThreeResult createWaitResult(PlayThree ps, Date date) throws ParseException {
        PlayThreeResult result = new PlayThreeResult();
        String dateNow = new SimpleDateFormat("yyyyMMdd").format(date);
        double playTime = Double.parseDouble(dateNow + "000");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = sdf.parse(new SimpleDateFormat("yyyy-MM-dd ").format(date) + ps.getStartTime());
        Calendar cal = Calendar.getInstance();
        cal.setTime(startTime);
        for(int i=0;i<ps.getCountTime();i++){
            playTime++;
            cal.add(Calendar.MINUTE, ps.getPlayRate());
            PlayThreeResult model = new PlayThreeResult(); 
            model.setId(UUID.randomUUID().toString());
            model.setCountNumber(0);
            model.setRewardMoney((double)0);
            model.setCreateTime(new Date());            
            model.setPlayTime(playTime);            
            model.setDayIndex(i+1);
            model.setPublishTime(cal.getTime());
            model.setEndTime(new Date(model.getPublishTime().getTime()-ps.getEndTime()*1000));
            model.setPerMoney(ps.getPerMoney());
            model.setStatus(PlayResultStatus.WAIT.getValue());
            model.setSumMoney((double) 0);
            model.setLeaveMoney((double)0);
            try {
                model = playThreeResultRepository.saveAndFlush(model);
            }catch(Exception ex) {
                ex.printStackTrace();
                return null;
            }
            if(i == ps.getCountTime() - 1){
                result = model;
            }
        }
        return result;
    }
}
