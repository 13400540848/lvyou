package org.ume.school.modules.play.three.result;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.entity.PlayThreeResult;
import org.ume.school.modules.model.enums.PlayResultStatus;

import com.bluesimon.wbf.Response;

/**
 * Created by Zz on 2018/11/1.
 */
@RestController
@RequestMapping("/v0.1/play/three/result")
public class PlayThreeResultController {

    @Resource
    private PlayThreeResultService playThreeResultService;
    
    /**
     * 列表
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response<List<PlayThreeResult>> getList(
            @RequestParam(value = "offset", required = true) String offset,
            @RequestParam(value = "limit", required = true) String limit) {
        Response<List<PlayThreeResult>> result = new Response<List<PlayThreeResult>>();
        final PlayThreeResult query = new PlayThreeResult();
        query.setStatus(PlayResultStatus.WAIT.getValue());
        Page<PlayThreeResult> pages = playThreeResultService.findAll(query, offset, limit);
        result.setTotal(pages.getTotalElements());
        result.setRows(pages.getContent());
        return result;
    }
    
    /**
     * 列表（按天升序）
     * @return
     */
    @RequestMapping(value = "/day", method = RequestMethod.GET)
    public Response<List<PlayThreeResult>> getDayList(
            @RequestParam(value = "date", required = true) String date) {
        Response<List<PlayThreeResult>> result = new Response<List<PlayThreeResult>>();
        if(date.isEmpty()){
            return result;
        }
        final PlayThreeResult query = new PlayThreeResult();
        date = date.replace("-", "");
        query.setPlayTimeStart(Double.parseDouble(date+"000"));
        query.setPlayTimeEnd(Double.parseDouble(date+"999"));
        Page<PlayThreeResult> pages = playThreeResultService.findAll(query, "1", "999");
        result.setTotal(pages.getTotalElements());
        result.setRows(pages.getContent());
        return result;
    }
    
    /**
     * 获取今天已開最後一期
     */
    @RequestMapping(value = "/today/last", method = RequestMethod.GET)
    public Response<PlayThreeResult> getTodayLast() {
        Response<PlayThreeResult> result = new Response<PlayThreeResult>();
        PlayThreeResult model = playThreeResultService.getTodayOpenLast();
        result.setRows(model);
        result.setTotal(1);
        return result;
    }
    
    
    /**
     * 获取上一期开奖情况
     */
    @RequestMapping(value = "/front", method = RequestMethod.GET)
    public Response<PlayThreeResult> getFront() {
        Response<PlayThreeResult> result = new Response<PlayThreeResult>();
        PlayThreeResult model = playThreeResultService.getFrontFinishedResult();
        result.setRows(model);
        result.setTotal(model!=null ?  1 : 0);
        return result;
    }
    
    /**
     * 获取当前可投注的一期
     */
    @RequestMapping(value = "/join", method = RequestMethod.GET)
    public Response<PlayThreeResult> getJoin() {
        Response<PlayThreeResult> result = new Response<PlayThreeResult>();
        List<PlayThreeResult> list = playThreeResultService.getWaitList();
        if(list!=null &&list.size()>0){
            PlayThreeResult ptr = null;
            for(PlayThreeResult model : list){
                if(model.getEndTime().before(new Date())){//过了截止时间，则取下一期
                    continue;
                }
                if(model.getPublishTime().before(new Date())){//过了发布时间，则取下一期
                    continue;
                }
                ptr = model;
                break;
            }
            if(ptr!=null){
                ptr.setNowTime(new Date());
                result.setRows(ptr);
                result.setTotal(1);
            }
        }
        return result;
    }
    
    /**
     * 获取待开奖的一期
     */
    @RequestMapping(value = "/wait", method = RequestMethod.GET)
    public Response<PlayThreeResult> getWait() {
        Response<PlayThreeResult> result = new Response<PlayThreeResult>();
        List<PlayThreeResult> list = playThreeResultService.getWaitList();
        if(list!=null &&list.size()>0){
            PlayThreeResult ptr = null;
            for(PlayThreeResult model : list){
                if(model.getPublishTime().after(new Date())){
                    ptr = model;
                    break;
                }
            }
            if(ptr!=null){
                ptr.setNowTime(new Date());
                result.setRows(ptr);
                result.setTotal(1);
            }
        }
        return result;
    }
    
    /**
     * 获取
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<PlayThreeResult> get(@PathVariable("id") String id) {
        Response<PlayThreeResult> result = new Response<PlayThreeResult>();
        PlayThreeResult model = playThreeResultService.get(id);
        result.setRows(model);
        result.setTotal(model!=null ?  1 : 0);
        return result;
    }
    
    /**
     * 最近的已开奖的5期
     * @return
     */
    @RequestMapping(value = "/finished", method = RequestMethod.GET)
    public Response<List<PlayThreeResult>> getLatestOpenList(
            @RequestParam(value = "offset", required = true) String offset,
            @RequestParam(value = "limit", required = true) String limit) {
        Response<List<PlayThreeResult>> result = new Response<List<PlayThreeResult>>();
        final PlayThreeResult query = new PlayThreeResult();
        query.setStatus(PlayResultStatus.FINISHED.getValue());
        Page<PlayThreeResult> pages = playThreeResultService.findAllDesc(query, offset, limit);
        result.setTotal(pages.getTotalElements());
        result.setRows(pages.getContent());
        return result;
    }
}
