package org.ume.school.modules.play.three.result.log;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.entity.PlayThreeResultLog;

import com.bluesimon.wbf.Response;

/**
 * Created by Zz on 2018/11/1.
 */
@RestController
@RequestMapping("/v0.1/play/three/result/log")
public class PlayThreeResultLogController {

    @Resource
    private PlayThreeResultLogService playThreeResultLogService;
    
    /**
     * 列表（按时间倒序）
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response<List<PlayThreeResultLog>> getList(
            @RequestParam(value = "offset", required = true) String offset,
            @RequestParam(value = "limit", required = true) String limit) {
        Response<List<PlayThreeResultLog>> result = new Response<List<PlayThreeResultLog>>();
        final PlayThreeResultLog query = new PlayThreeResultLog();
        Page<PlayThreeResultLog> pages = playThreeResultLogService.findAllDesc(query, offset, limit);
        result.setTotal(pages.getTotalElements());
        result.setRows(pages.getContent());
        return result;
    }
    
    /**
     * 列表（按天升序）
     * @return
     */
    @RequestMapping(value = "/day", method = RequestMethod.GET)
    public Response<List<PlayThreeResultLog>> getDayList(
            @RequestParam(value = "date", required = true) String date) {
        Response<List<PlayThreeResultLog>> result = new Response<List<PlayThreeResultLog>>();
        if(date.isEmpty()){
            return result;
        }
        final PlayThreeResultLog query = new PlayThreeResultLog();
        date = date.replace("-", "");
        query.setPlayTimeStart(Double.parseDouble(date+"000"));
        query.setPlayTimeEnd(Double.parseDouble(date+"999"));
        Page<PlayThreeResultLog> pages = playThreeResultLogService.findAll(query, "1", "999");
        result.setTotal(pages.getTotalElements());
        result.setRows(pages.getContent());
        return result;
    }
    
    
    /**
     * 获取
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<PlayThreeResultLog> get(@PathVariable("id") String id) {
        Response<PlayThreeResultLog> result = new Response<PlayThreeResultLog>();
        PlayThreeResultLog model = playThreeResultLogService.get(id);
        result.setRows(model);
        result.setTotal(model!=null ?  1 : 0);
        return result;
    }
}
