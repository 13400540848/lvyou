package org.ume.school.modules.play.seven.result;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.entity.PlaySevenResult;
import org.ume.school.modules.model.entity.PlaySevenResult;
import org.ume.school.modules.model.entity.PlaySevenResult;
import org.ume.school.modules.model.entity.PlaySevenResult;
import org.ume.school.modules.model.entity.PlaySevenResult;
import org.ume.school.modules.model.enums.PlayResultStatus;

import com.bluesimon.wbf.Response;

/**
 * Created by Zz on 2018/10/25.
 */
@RestController
@RequestMapping("/v0.1/play/seven/result")
public class PlaySevenResultController {

    @Resource
    private PlaySevenResultService playSevenResultService;
    
    /**
     * 列表
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response<List<PlaySevenResult>> getList(
            @RequestParam(value = "offset", required = true) String offset,
            @RequestParam(value = "limit", required = true) String limit) {
        Response<List<PlaySevenResult>> result = new Response<List<PlaySevenResult>>();
        final PlaySevenResult query = new PlaySevenResult();
        query.setStatus(PlayResultStatus.WAIT.getValue());
        Page<PlaySevenResult> pages = playSevenResultService.findAll(query, offset, limit);
        result.setTotal(pages.getTotalElements());
        result.setRows(pages.getContent());
        return result;
    }
    
    /**
     * 列表（按天升序）
     * @return
     */
    @RequestMapping(value = "/day", method = RequestMethod.GET)
    public Response<List<PlaySevenResult>> getDayList(
            @RequestParam(value = "date", required = true) String date) {
        Response<List<PlaySevenResult>> result = new Response<List<PlaySevenResult>>();
        if(date.isEmpty()){
            return result;
        }
        final PlaySevenResult query = new PlaySevenResult();
        date = date.replace("-", "");
        query.setPlayTimeStart(Double.parseDouble(date+"000"));
        query.setPlayTimeEnd(Double.parseDouble(date+"999"));
        Page<PlaySevenResult> pages = playSevenResultService.findAll(query, "1", "999");
        result.setTotal(pages.getTotalElements());
        result.setRows(pages.getContent());
        return result;
    }
    
    /**
     * 获取今天已開最後一期
     */
    @RequestMapping(value = "/today/last", method = RequestMethod.GET)
    public Response<PlaySevenResult> getTodayLast() {
        Response<PlaySevenResult> result = new Response<PlaySevenResult>();
        PlaySevenResult model = playSevenResultService.getTodayOpenLast();
        result.setRows(model);
        result.setTotal(1);
        return result;
    }
    
    /**
     * 获取上一期开奖情况
     */
    @RequestMapping(value = "/front", method = RequestMethod.GET)
    public Response<PlaySevenResult> getFront() {
        Response<PlaySevenResult> result = new Response<PlaySevenResult>();
        PlaySevenResult model = playSevenResultService.getFrontFinishedResult();
        result.setRows(model);
        result.setTotal(model!=null ?  1 : 0);
        return result;
    }
    
    /**
     * 获取当前可投注的一期
     */
    @RequestMapping(value = "/join", method = RequestMethod.GET)
    public Response<PlaySevenResult> getJoin() {
        Response<PlaySevenResult> result = new Response<PlaySevenResult>();
        List<PlaySevenResult> list = playSevenResultService.getWaitList();
        if(list!=null &&list.size()>0){
            PlaySevenResult ptr = null;
            for(PlaySevenResult model : list){
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
    public Response<PlaySevenResult> getWait() {
        Response<PlaySevenResult> result = new Response<PlaySevenResult>();
        List<PlaySevenResult> list = playSevenResultService.getWaitList();
        if(list!=null &&list.size()>0){
            PlaySevenResult ptr = null;
            for(PlaySevenResult model : list){
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
    public Response<PlaySevenResult> get(@PathVariable("id") String id) {
        Response<PlaySevenResult> result = new Response<PlaySevenResult>();
        PlaySevenResult model = playSevenResultService.get(id);
        result.setRows(model);
        result.setTotal(model!=null ?  1 : 0);
        return result;
    }
    
    /**
     * 最近的已开奖的5期
     * @return
     */
    @RequestMapping(value = "/finished", method = RequestMethod.GET)
    public Response<List<PlaySevenResult>> getLatestOpenList(
            @RequestParam(value = "offset", required = true) String offset,
            @RequestParam(value = "limit", required = true) String limit) {
        Response<List<PlaySevenResult>> result = new Response<List<PlaySevenResult>>();
        final PlaySevenResult query = new PlaySevenResult();
        query.setStatus(PlayResultStatus.FINISHED.getValue());
        Page<PlaySevenResult> pages = playSevenResultService.findAllDesc(query, offset, limit);
        result.setTotal(pages.getTotalElements());
        result.setRows(pages.getContent());
        return result;
    }
}
