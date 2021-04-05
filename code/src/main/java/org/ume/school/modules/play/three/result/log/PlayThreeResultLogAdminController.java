package org.ume.school.modules.play.three.result.log;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.entity.PlayThreeResultLog;

import com.bluesimon.wbf.AdminLogined;
import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.utils.AuthValidate;

/**
 * Created by Zz on 2018/11/1.
 */
@RestController
@RequestMapping("/v0.1/admin/play/three/log")
public class PlayThreeResultLogAdminController {

	@Resource
    private PlayThreeResultLogService playThreeResultLogService;
    
//	/**
//     * 列表（按时间倒序）
//     * @return
//     */
//    @RequestMapping(value = "", method = RequestMethod.GET)
//    public Response<List<PlayThreeResultLog>> getList(@RequestParam("userId") String userId,
//            @RequestParam(value = "offset", required = true) String offset,
//            @RequestParam(value = "limit", required = true) String limit, @AdminLogined IUser user) {
//        Response<List<PlayThreeResultLog>> result = new Response<List<PlayThreeResultLog>>();
//        final PlayThreeResultLog query = new PlayThreeResultLog();
//        Page<PlayThreeResultLog> pages = playThreeResultLogService.findAllDesc(query, offset, limit);
//        result.setTotal(pages.getTotalElements());
//        result.setRows(pages.getContent());
//        return result;
//    }
    
    
    /**
     * 获取
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<PlayThreeResultLog> get(@PathVariable("id") String id, @AdminLogined IUser user) {
    	Response<PlayThreeResultLog> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        Response<PlayThreeResultLog> result = new Response<PlayThreeResultLog>();
        PlayThreeResultLog model = playThreeResultLogService.get(id);
        result.setRows(model);
        result.setTotal(model!=null ?  1 : 0);
        return result;
    }
    
//    /**
//     * 提交
//     * @return
//     */
//     @RequestMapping(value = "", method = RequestMethod.POST)
//     public Response<PlayThreeResultLog> submitPlayThree(@RequestBody PlayThreeResultLog model, @AdminLogined IUser user) {
//         Response<PlayThreeResultLog> check = AuthValidate.checkAdmin(user);
//         if (check != null) {
//             return check;
//         }
//         Response<PlayThreeResultLog> resp = new Response<>();
//         if (StringUtils.isEmpty(model.getId())) { //新增
//        	 model.setId(UUID.randomUUID().toString());
//             model.setCreateTime(new Date());
//             model.setModifyTime(new Date());
//             resp.setRows(playThreeResultService.submit((model)));
//         }else {
//        	 PlayThreeResultLog modelDb = playThreeResultService.get(model.getId());
//             if(modelDb==null){
//            	 resp.setResultCode(ErrorTag.PLAY_REWARD_NOT_EXISTS.getCode());
//                 resp.setResultMsg(ErrorTag.PLAY_REWARD_NOT_EXISTS.getMessage());
//             }
//             else{
//            	 model.setCreateTime(modelDb.getCreateTime());
//            	 model.setModifyTime(new Date());
//                 resp.setRows(playThreeResultService.submit(model));
//             }
//         }
//         return resp;
//     }
//     
//     /**
//      * 删除
//      * @param PlayThreeResultLog
//      * @param user
//      * @return
//      */
//      @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//      public Response<PlayThreeResultLog> deletePlayThreeResultLog(@PathVariable("id") String id, @AdminLogined IUser user) {
//          Response<PlayThreeResultLog> check = AuthValidate.checkAdmin(user);
//          if (check != null) {
//              return check;
//          }
//          Response<PlayThreeResultLog> resp = new Response<>();
//          if (!StringUtils.isEmpty(id)) {
//              PlayThreeResultLog modelDb = playThreeResultService.get(id);
//              if(modelDb==null){
//                  resp.setResultCode(ErrorTag.PLAY_REWARD_NOT_EXISTS.getCode());
//                  resp.setResultMsg(ErrorTag.PLAY_REWARD_NOT_EXISTS.getMessage());
//              }
//              else{
//            	  playThreeResultService.delete(id);
//              }
//          }
//          else { 
//              resp.setResultCode(ErrorTag.PARAM_NULL.getCode());
//              resp.setResultMsg(ErrorTag.PARAM_NULL.getMessage());
//          }
//          return resp;
//      }
}
