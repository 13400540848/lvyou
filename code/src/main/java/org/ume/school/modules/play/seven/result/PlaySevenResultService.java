package org.ume.school.modules.play.seven.result;

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
import org.ume.school.modules.model.entity.PlaySeven;
import org.ume.school.modules.model.entity.PlaySevenResult;
import org.ume.school.modules.model.entity.PlaySevenResult;
import org.ume.school.modules.model.entity.PlayThreeResult;
import org.ume.school.modules.model.enums.PlayResultStatus;
import org.ume.school.modules.utils.PageUtils;

/**
 * Created by Zz on 2018/10/25.
 */
@Service("playSevenResultService")
public class PlaySevenResultService {

    @Resource
    private PlaySevenResultRepository playSevenResultRepository;

    @Transactional
    public PlaySevenResult submit(PlaySevenResult model) {
        return playSevenResultRepository.saveAndFlush(model);
    }

    public PlaySevenResult get(String id) {
        return playSevenResultRepository.findOne(id);
    }
    
    public Page<PlaySevenResult> findAll(final PlaySevenResult condition, String offset, String limit){
        return findAll(condition, offset, limit, "playTime", "Asc");
    }
    
    public Page<PlaySevenResult> findAllDesc(final PlaySevenResult condition, String offset, String limit){
        return findAll(condition, offset, limit, "playTime", "Desc");
    }

    /**
     * 查询并分页
     */
    @SuppressWarnings("unchecked")
    public Page<PlaySevenResult> findAll(final PlaySevenResult condition, String offset, String limit, String orderBy, String direction) {
        @SuppressWarnings("rawtypes")
        Specification specification = new Specification<PlaySevenResult>() {
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
        return playSevenResultRepository.findAll(specification, pr);
    }
    
    public PlaySevenResult getTodayOpenLast() {
        final PlaySevenResult query = new PlaySevenResult();
        query.setStatus(PlayResultStatus.FINISHED.getValue());
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());        
        query.setPlayTimeStart(Double.parseDouble(date+"000"));
        query.setPlayTimeEnd(Double.parseDouble(date+"999"));
        Page<PlaySevenResult> pages = findAllDesc(query, "1", "1");
        if(pages.getContent()!=null && pages.getContent().size()>0){
            return pages.getContent().get(0);
        }
        return null;
    }
    
    
    public PlaySevenResult getFrontFinishedResult() {
        final PlaySevenResult condition = new PlaySevenResult();
        condition.setStatus(PlayResultStatus.FINISHED.getValue());
        Page<PlaySevenResult> pages = findAllDesc(condition, "1", "1");
        return pages != null && pages.getContent()!=null && pages.getContent().size() > 0 ? pages.getContent().get(0) : null;
    }

    public  List<PlaySevenResult> getWaitList() {
        return playSevenResultRepository.findByStatusOrderByPlayTimeAsc(PlayResultStatus.WAIT.getValue());
    }
    
    public PlaySevenResult getLatestResult() {
        final PlaySevenResult condition = new PlaySevenResult();
        Page<PlaySevenResult> pages = findAllDesc(condition, "1", "1");
        return pages != null && pages.getContent()!=null && pages.getContent().size() > 0 ? pages.getContent().get(0) : null;
    }
    
    public PlaySevenResult getWaitResult() {
        List<PlaySevenResult> list = playSevenResultRepository.findByStatusOrderByPlayTimeAsc(PlayResultStatus.WAIT.getValue());
        if( list != null && list.size() > 0){
            for(PlaySevenResult item : list){
                if(item.getEndTime().after(new Date())){
                    return item;
                }
            }
        }
        return null;
    }
    
    /**
     * 生成78期记录（某一天）
     */
    public PlaySevenResult createWaitResult(PlaySeven ps, Date date) throws ParseException {
        PlaySevenResult result = new PlaySevenResult();
        String dateNow = new SimpleDateFormat("yyyyMMdd").format(date);
        double playTime = Double.parseDouble(dateNow + "000");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = sdf.parse(new SimpleDateFormat("yyyy-MM-dd ").format(date) + ps.getStartTime());
        Calendar cal = Calendar.getInstance();
        cal.setTime(startTime);
        for(int i=0;i<ps.getCountTime();i++){
            playTime++;
            cal.add(Calendar.MINUTE, ps.getPlayRate());
            PlaySevenResult model = new PlaySevenResult(); 
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
                model = playSevenResultRepository.saveAndFlush(model);
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
    

//    public PlaySevenResult createWaitResult(PlaySeven ps) throws ParseException {
//        PlaySevenResult model = new PlaySevenResult();
//
//        // 期数
//        final PlaySevenResult condition = new PlaySevenResult();
//        Page<PlaySevenResult> pagePsr = findAll(condition, "1", "1");
//        if (pagePsr != null && pagePsr.getContent() != null && pagePsr.getContent().size() > 0) {
//            PlaySevenResult psr = pagePsr.getContent().get(0);
//            model.setPlayTime(psr.getPlayTime() + 1);
//        }
//        else {
//            Calendar cal = Calendar.getInstance();
//            int year = cal.get(Calendar.YEAR);// 获取当前年份第一期
//            model.setPlayTime(year * 1000 + 1);
//        }
//        model.setId(UUID.randomUUID().toString());
//        model.setCountNumber(0);
//        model.setRewardMoney((double)0);
//        model.setCreateTime(new Date());
//        
//        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "★★★★★★★★★★★开奖时间");
//        // 开奖时间
//        Date nextDate = getNextDate(ps);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        model.setPublishTime(sdf1.parse(sdf.format(nextDate) + " " + ps.getPublishTime()));
//
//        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "★★★★★★★★★★★截止时间");
//        // 截止时间
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(nextDate);
//        cal.add(Calendar.SECOND, -ps.getEndTime());
//        model.setEndTime(cal.getTime());
//
//        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "★★★★★★★★★★★其他");
//        model.setPerMoney(ps.getPerMoney());
//        model.setStatus(PlayResultStatus.WAIT.getValue());
//        model.setSumMoney((double) 0);
//        model.setLeaveMoney((double)0);
//        
//        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "★★★★★★★★★★★保存");
//        try {
//        	return playSevenResultRepository.saveAndFlush(model);
//        }catch(Exception ex) {
//        	ex.printStackTrace();
//        	return null;
//        }
//    }
//
//    private Date getNextDate(PlaySeven ps) {
//        // 获取下一期的时间
//        String playRate = ps.getPlayRate();
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(new Date());
//        int WeekOfYear = cal.get(Calendar.DAY_OF_WEEK);// 一周的第几天(1-7)
//        WeekOfYear = (WeekOfYear == 0 ? 7 : (WeekOfYear - 1));
//        String[] arrPr = playRate.split(",");
//        int days = 7 - WeekOfYear + Integer.parseInt(arrPr[0]);
//        for (int i = 0; i < arrPr.length; i++) {
//            if (Integer.parseInt(arrPr[i]) == WeekOfYear) {
//                if (i == arrPr.length - 1) {
//                    days = Integer.parseInt(arrPr[0]);
//                }
//                else {
//                    days = Integer.parseInt(arrPr[i +1]) - WeekOfYear;
//                }
//                break;
//            }
//            else if (Integer.parseInt(arrPr[i]) > WeekOfYear) {
//                days = Integer.parseInt(arrPr[i]) - WeekOfYear;
//                break;
//            }
//        }
//        cal.add(Calendar.DAY_OF_MONTH, days);
//        return cal.getTime();
//    }
}
