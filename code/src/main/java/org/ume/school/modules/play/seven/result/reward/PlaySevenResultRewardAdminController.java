package org.ume.school.modules.play.seven.result.reward;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.entity.PlaySevenResultReward;

import com.bluesimon.wbf.AdminLogined;
import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.utils.AuthValidate;

/**
 * Created by Zz on 2018/10/25.
 */
@RestController
@RequestMapping("/v0.1/admin/play/seven/result/reward")
public class PlaySevenResultRewardAdminController {

	@Resource
    private PlaySevenResultRewardService playSevenResultRewardService;
    
	/**
     * 列表
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response<List<PlaySevenResultReward>> getList(
            @RequestParam(value = "playTime", required = true) Integer playTime, @AdminLogined IUser user) {
        Response<List<PlaySevenResultReward>> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        Response<List<PlaySevenResultReward>> result = new Response<List<PlaySevenResultReward>>();
        List<PlaySevenResultReward> list = playSevenResultRewardService.findByPlayTimeOrderByRewardCodeAsc(playTime);
        result.setRows(list);
        return result;
    }
    
    
    /**
     * 获取
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<PlaySevenResultReward> get(@PathVariable("id") String id, @AdminLogined IUser user) {
    	Response<PlaySevenResultReward> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        Response<PlaySevenResultReward> result = new Response<PlaySevenResultReward>();
        PlaySevenResultReward model = playSevenResultRewardService.get(id);
        result.setRows(model);
        result.setTotal(model!=null ?  1 : 0);
        return result;
    }
    
//    /**
//     * 提交
//     * @return
//     */
//     @RequestMapping(value = "", method = RequestMethod.POST)
//     public Response<PlaySevenResultReward> submitPlaySeven(@RequestBody PlaySevenResultReward model, @AdminLogined IUser user) {
//         Response<PlaySevenResultReward> check = AuthValidate.checkAdmin(user);
//         if (check != null) {
//             return check;
//         }
//         Response<PlaySevenResultReward> resp = new Response<>();
//         if (StringUtils.isEmpty(model.getId())) { //新增
//        	 model.setId(UUID.randomUUID().toString());
//             model.setCreateTime(new Date());
//             model.setModifyTime(new Date());
//             resp.setRows(PlaySevenResultRewardService.submit((model)));
//         }else {
//        	 PlaySevenResultReward modelDb = PlaySevenResultRewardService.get(model.getId());
//             if(modelDb==null){
//            	 resp.setResultCode(ErrorTag.PLAY_REWARD_NOT_EXISTS.getCode());
//                 resp.setResultMsg(ErrorTag.PLAY_REWARD_NOT_EXISTS.getMessage());
//             }
//             else{
//            	 model.setCreateTime(modelDb.getCreateTime());
//            	 model.setModifyTime(new Date());
//                 resp.setRows(PlaySevenResultRewardService.submit(model));
//             }
//         }
//         return resp;
//     }
//     
//     /**
//      * 删除
//      * @param PlaySevenResultReward
//      * @param user
//      * @return
//      */
//      @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//      public Response<PlaySevenResultReward> deletePlaySevenResultReward(@PathVariable("id") String id, @AdminLogined IUser user) {
//          Response<PlaySevenResultReward> check = AuthValidate.checkAdmin(user);
//          if (check != null) {
//              return check;
//          }
//          Response<PlaySevenResultReward> resp = new Response<>();
//          if (!StringUtils.isEmpty(id)) {
//              PlaySevenResultReward modelDb = PlaySevenResultRewardService.get(id);
//              if(modelDb==null){
//                  resp.setResultCode(ErrorTag.PLAY_REWARD_NOT_EXISTS.getCode());
//                  resp.setResultMsg(ErrorTag.PLAY_REWARD_NOT_EXISTS.getMessage());
//              }
//              else{
//            	  PlaySevenResultRewardService.delete(id);
//              }
//          }
//          else { 
//              resp.setResultCode(ErrorTag.PARAM_NULL.getCode());
//              resp.setResultMsg(ErrorTag.PARAM_NULL.getMessage());
//          }
//          return resp;
//      }
}
