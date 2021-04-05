package org.ume.school.modules.play.three.reward;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.ErrorTag;
import org.ume.school.modules.model.entity.PlayThreeReward;

import com.bluesimon.wbf.AdminLogined;
import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.utils.AuthValidate;

/**
 * Created by Zz on 2018/11/1.
 */
@RestController
@RequestMapping("/v0.1/admin/play/three/reward")
public class PlayThreeRewardAdminController {

    @Resource
    private PlayThreeRewardService playThreeRewardService;
    
    /**
     * 获取配置列表
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response<List<PlayThreeReward>> getList() {
        Response<List<PlayThreeReward>> result = new Response<List<PlayThreeReward>>();
        List<PlayThreeReward> list = playThreeRewardService.findAll();
        result.setRows(list);
        result.setTotal(list!=null ?  list.size() : 0);
        return result;
    }
    
    /**
     * 获取配置
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<PlayThreeReward> get(@PathVariable("id") String id) {
        Response<PlayThreeReward> result = new Response<PlayThreeReward>();
        PlayThreeReward model = playThreeRewardService.get(id);
        result.setRows(model);
        result.setTotal(model!=null ?  1 : 0);
        return result;
    }
    
    /**
     * 提交
     * @return
     */
     @RequestMapping(value = "", method = RequestMethod.POST)
     public Response<PlayThreeReward> submitPlayThree(@RequestBody PlayThreeReward model, @AdminLogined IUser user) {
         Response<PlayThreeReward> check = AuthValidate.checkAdmin(user);
         if (check != null) {
             return check;
         }
         Response<PlayThreeReward> resp = new Response<>();
         if (StringUtils.isEmpty(model.getId())) { //新增
        	 model.setId(UUID.randomUUID().toString());
             model.setCreateTime(new Date());
             model.setModifyTime(new Date());
             resp.setRows(playThreeRewardService.submit((model)));
         }else {
        	 PlayThreeReward modelDb = playThreeRewardService.get(model.getId());
             if(modelDb==null){
            	 resp.setResultCode(ErrorTag.PLAY_REWARD_NOT_EXISTS.getCode());
                 resp.setResultMsg(ErrorTag.PLAY_REWARD_NOT_EXISTS.getMessage());
             }
             else{
            	 model.setCreateTime(modelDb.getCreateTime());
            	 model.setModifyTime(new Date());
                 resp.setRows(playThreeRewardService.submit(model));
             }
         }
         return resp;
     }
     
     /**
      * 删除
      * @param PlayThreeReward
      * @param user
      * @return
      */
      @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
      public Response<PlayThreeReward> deletePlayThreeReward(@PathVariable("id") String id, @AdminLogined IUser user) {
          Response<PlayThreeReward> check = AuthValidate.checkAdmin(user);
          if (check != null) {
              return check;
          }
          Response<PlayThreeReward> resp = new Response<>();
          if (!StringUtils.isEmpty(id)) {
              PlayThreeReward modelDb = playThreeRewardService.get(id);
              if(modelDb==null){
                  resp.setResultCode(ErrorTag.PLAY_REWARD_NOT_EXISTS.getCode());
                  resp.setResultMsg(ErrorTag.PLAY_REWARD_NOT_EXISTS.getMessage());
              }
              else{
            	  playThreeRewardService.delete(id);
              }
          }
          else { 
              resp.setResultCode(ErrorTag.PARAM_NULL.getCode());
              resp.setResultMsg(ErrorTag.PARAM_NULL.getMessage());
          }
          return resp;
      }
}
