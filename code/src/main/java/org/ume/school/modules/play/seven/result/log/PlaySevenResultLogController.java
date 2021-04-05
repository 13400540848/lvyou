package org.ume.school.modules.play.seven.result.log;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.entity.PlaySevenResultLog;
import org.ume.school.modules.model.entity.PlaySevenResultLog;

import com.bluesimon.wbf.Response;

/**
 * Created by Zz on 2018/10/27.
 */
@RestController
@RequestMapping("/v0.1/play/seven/result/log")
public class PlaySevenResultLogController {

    @Resource
    private PlaySevenResultLogService playSevenResultLogService;
    
    /**
     * 列表（按时间增序）
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response<List<PlaySevenResultLog>> getList(
            @RequestParam(value = "offset", required = true) String offset,
            @RequestParam(value = "limit", required = true) String limit) {
        Response<List<PlaySevenResultLog>> result = new Response<List<PlaySevenResultLog>>();
        final PlaySevenResultLog query = new PlaySevenResultLog();
        Page<PlaySevenResultLog> pages = playSevenResultLogService.findAll(query, offset, limit);
        result.setTotal(pages.getTotalElements());
        result.setRows(pages.getContent());
        return result;
    }
    
    /**
     * 列表（按天升序）
     * @return
     */
    @RequestMapping(value = "/day", method = RequestMethod.GET)
    public Response<List<PlaySevenResultLog>> getDayList(
            @RequestParam(value = "date", required = true) String date) {
        Response<List<PlaySevenResultLog>> result = new Response<List<PlaySevenResultLog>>();
        if(date.isEmpty()){
            return result;
        }
        final PlaySevenResultLog query = new PlaySevenResultLog();
        date = date.replace("-", "");
        query.setPlayTimeStart(Double.parseDouble(date+"000"));
        query.setPlayTimeEnd(Double.parseDouble(date+"999"));
        Page<PlaySevenResultLog> pages = playSevenResultLogService.findAll(query, "1", "999");
        result.setTotal(pages.getTotalElements());
        result.setRows(pages.getContent());
        return result;
    }
    
    
    /**
     * 获取
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<PlaySevenResultLog> get(@PathVariable("id") String id) {
        Response<PlaySevenResultLog> result = new Response<PlaySevenResultLog>();
        PlaySevenResultLog model = playSevenResultLogService.get(id);
        result.setRows(model);
        result.setTotal(model!=null ?  1 : 0);
        return result;
    }
}
