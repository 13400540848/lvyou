package org.ume.school.modules.play.seven.result;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.entity.PlaySevenResult;

import com.bluesimon.wbf.AdminLogined;
import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.utils.AuthValidate;

/**
 * Created by Zz on 2018/10/25.
 */
@RestController
@RequestMapping("/v0.1/admin/play/seven/result")
public class PlaySevenResultAdminController {

	@Resource
    private PlaySevenResultService playSevenResultService;
    
    /**
     * 列表
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response<List<PlaySevenResult>> getList(
            @RequestParam(value = "offset", required = true) String offset,
            @RequestParam(value = "limit", required = true) String limit, @AdminLogined IUser user) {
        Response<List<PlaySevenResult>> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        Response<List<PlaySevenResult>> result = new Response<List<PlaySevenResult>>();
        final PlaySevenResult query = new PlaySevenResult();
        Page<PlaySevenResult> pages = playSevenResultService.findAll(query, offset, limit);
        result.setTotal(pages.getTotalElements());
        result.setRows(pages.getContent());
        return result;
    }
    
    
    /**
     * 获取
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<PlaySevenResult> get(@PathVariable("id") String id, @AdminLogined IUser user) {
    	Response<PlaySevenResult> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        Response<PlaySevenResult> result = new Response<PlaySevenResult>();
        PlaySevenResult model = playSevenResultService.get(id);
        result.setRows(model);
        result.setTotal(model!=null ?  1 : 0);
        return result;
    }
    
//    /**
//     * 提交
//     * @return
//     */
//     @RequestMapping(value = "", method = RequestMethod.POST)
//     public Response<PlaySevenResult> submitPlaySeven(@RequestBody PlaySevenResult model, @AdminLogined IUser user) {
//         Response<PlaySevenResult> check = AuthValidate.checkAdmin(user);
//         if (check != null) {
//             return check;
//         }
//         Response<PlaySevenResult> resp = new Response<>();
//         if (StringUtils.isEmpty(model.getId())) { //新增
//        	 model.setId(UUID.randomUUID().toString());
//             model.setCreateTime(new Date());
//             model.setModifyTime(new Date());
//             resp.setRows(playSevenResultService.submit((model)));
//         }else {
//        	 PlaySevenResult modelDb = playSevenResultService.get(model.getId());
//             if(modelDb==null){
//            	 resp.setResultCode(ErrorTag.PLAY_REWARD_NOT_EXISTS.getCode());
//                 resp.setResultMsg(ErrorTag.PLAY_REWARD_NOT_EXISTS.getMessage());
//             }
//             else{
//            	 model.setCreateTime(modelDb.getCreateTime());
//            	 model.setModifyTime(new Date());
//                 resp.setRows(playSevenResultService.submit(model));
//             }
//         }
//         return resp;
//     }
//     
//     /**
//      * 删除
//      * @param PlaySevenResult
//      * @param user
//      * @return
//      */
//      @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//      public Response<PlaySevenResult> deletePlaySevenResult(@PathVariable("id") String id, @AdminLogined IUser user) {
//          Response<PlaySevenResult> check = AuthValidate.checkAdmin(user);
//          if (check != null) {
//              return check;
//          }
//          Response<PlaySevenResult> resp = new Response<>();
//          if (!StringUtils.isEmpty(id)) {
//              PlaySevenResult modelDb = playSevenResultService.get(id);
//              if(modelDb==null){
//                  resp.setResultCode(ErrorTag.PLAY_REWARD_NOT_EXISTS.getCode());
//                  resp.setResultMsg(ErrorTag.PLAY_REWARD_NOT_EXISTS.getMessage());
//              }
//              else{
//            	  playSevenResultService.delete(id);
//              }
//          }
//          else { 
//              resp.setResultCode(ErrorTag.PARAM_NULL.getCode());
//              resp.setResultMsg(ErrorTag.PARAM_NULL.getMessage());
//          }
//          return resp;
//      }
}
