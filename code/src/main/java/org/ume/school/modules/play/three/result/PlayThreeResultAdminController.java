package org.ume.school.modules.play.three.result;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.entity.PlayThreeResult;

import com.bluesimon.wbf.AdminLogined;
import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.utils.AuthValidate;

/**
 * Created by Zz on 2018/11/21.
 */
@RestController
@RequestMapping("/v0.1/admin/play/three/result")
public class PlayThreeResultAdminController {

	@Resource
    private PlayThreeResultService playThreeResultService;
    
    /**
     * 列表
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response<List<PlayThreeResult>> getList(
            @RequestParam(value = "offset", required = true) String offset,
            @RequestParam(value = "limit", required = true) String limit, @AdminLogined IUser user) {
        Response<List<PlayThreeResult>> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        Response<List<PlayThreeResult>> result = new Response<List<PlayThreeResult>>();
        final PlayThreeResult query = new PlayThreeResult();
        Page<PlayThreeResult> pages = playThreeResultService.findAll(query, offset, limit);
        result.setTotal(pages.getTotalElements());
        result.setRows(pages.getContent());
        return result;
    }
    
    
    /**
     * 获取
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<PlayThreeResult> get(@PathVariable("id") String id, @AdminLogined IUser user) {
    	Response<PlayThreeResult> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        Response<PlayThreeResult> result = new Response<PlayThreeResult>();
        PlayThreeResult model = playThreeResultService.get(id);
        result.setRows(model);
        result.setTotal(model!=null ?  1 : 0);
        return result;
    }
    
//    /**
//     * 提交
//     * @return
//     */
//     @RequestMapping(value = "", method = RequestMethod.POST)
//     public Response<PlayThreeResult> submitPlayThree(@RequestBody PlayThreeResult model, @AdminLogined IUser user) {
//         Response<PlayThreeResult> check = AuthValidate.checkAdmin(user);
//         if (check != null) {
//             return check;
//         }
//         Response<PlayThreeResult> resp = new Response<>();
//         if (StringUtils.isEmpty(model.getId())) { //新增
//        	 model.setId(UUID.randomUUID().toString());
//             model.setCreateTime(new Date());
//             model.setModifyTime(new Date());
//             resp.setRows(playThreeResultService.submit((model)));
//         }else {
//        	 PlayThreeResult modelDb = playThreeResultService.get(model.getId());
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
//      * @param PlayThreeResult
//      * @param user
//      * @return
//      */
//      @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//      public Response<PlayThreeResult> deletePlayThreeResult(@PathVariable("id") String id, @AdminLogined IUser user) {
//          Response<PlayThreeResult> check = AuthValidate.checkAdmin(user);
//          if (check != null) {
//              return check;
//          }
//          Response<PlayThreeResult> resp = new Response<>();
//          if (!StringUtils.isEmpty(id)) {
//              PlayThreeResult modelDb = playThreeResultService.get(id);
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
