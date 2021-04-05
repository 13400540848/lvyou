package org.ume.school.modules.play.seven.result.log;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.entity.PlaySevenResultLog;

import com.bluesimon.wbf.AdminLogined;
import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.utils.AuthValidate;

/**
 * Created by Zz on 2018/10/25.
 */
@RestController
@RequestMapping("/v0.1/admin/play/seven/log")
public class PlaySevenResultLogAdminController {

	@Resource
    private PlaySevenResultLogService playSevenResultLogService;
    
//	/**
//     * 列表（按时间倒序）
//     * @return
//     */
//    @RequestMapping(value = "", method = RequestMethod.GET)
//    public Response<List<PlaySevenResultLog>> getList(@RequestParam("userId") String userId,
//            @RequestParam(value = "offset", required = true) String offset,
//            @RequestParam(value = "limit", required = true) String limit, @AdminLogined IUser user) {
//        Response<List<PlaySevenResultLog>> result = new Response<List<PlaySevenResultLog>>();
//        final PlaySevenResultLog query = new PlaySevenResultLog();
//        Page<PlaySevenResultLog> pages = playSevenResultLogService.findAllDesc(query, offset, limit);
//        result.setTotal(pages.getTotalElements());
//        result.setRows(pages.getContent());
//        return result;
//    }
    
    
    /**
     * 获取
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<PlaySevenResultLog> get(@PathVariable("id") String id, @AdminLogined IUser user) {
    	Response<PlaySevenResultLog> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        Response<PlaySevenResultLog> result = new Response<PlaySevenResultLog>();
        PlaySevenResultLog model = playSevenResultLogService.get(id);
        result.setRows(model);
        result.setTotal(model!=null ?  1 : 0);
        return result;
    }
    
//    /**
//     * 提交
//     * @return
//     */
//     @RequestMapping(value = "", method = RequestMethod.POST)
//     public Response<PlaySevenResultLog> submitPlaySeven(@RequestBody PlaySevenResultLog model, @AdminLogined IUser user) {
//         Response<PlaySevenResultLog> check = AuthValidate.checkAdmin(user);
//         if (check != null) {
//             return check;
//         }
//         Response<PlaySevenResultLog> resp = new Response<>();
//         if (StringUtils.isEmpty(model.getId())) { //新增
//        	 model.setId(UUID.randomUUID().toString());
//             model.setCreateTime(new Date());
//             model.setModifyTime(new Date());
//             resp.setRows(playSevenResultService.submit((model)));
//         }else {
//        	 PlaySevenResultLog modelDb = playSevenResultService.get(model.getId());
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
//      * @param PlaySevenResultLog
//      * @param user
//      * @return
//      */
//      @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//      public Response<PlaySevenResultLog> deletePlaySevenResultLog(@PathVariable("id") String id, @AdminLogined IUser user) {
//          Response<PlaySevenResultLog> check = AuthValidate.checkAdmin(user);
//          if (check != null) {
//              return check;
//          }
//          Response<PlaySevenResultLog> resp = new Response<>();
//          if (!StringUtils.isEmpty(id)) {
//              PlaySevenResultLog modelDb = playSevenResultService.get(id);
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
