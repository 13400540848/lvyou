package org.ume.school.modules.play.seven.reward;

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
import org.ume.school.modules.model.entity.PlaySevenReward;

import com.bluesimon.wbf.AdminLogined;
import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.utils.AuthValidate;

/**
 * Created by Zz on 2018/10/25.
 */
@RestController
@RequestMapping("/v0.1/admin/play/seven/reward")
public class PlaySevenRewardAdminController {

    @Resource
    private PlaySevenRewardService playSevenRewardService;
    
    /**
     * 获取配置列表
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response<List<PlaySevenReward>> getList() {
        Response<List<PlaySevenReward>> result = new Response<List<PlaySevenReward>>();
        List<PlaySevenReward> list = playSevenRewardService.findAll();
        result.setRows(list);
        result.setTotal(list!=null ?  list.size() : 0);
        return result;
    }
    
    /**
     * 获取配置
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<PlaySevenReward> get(@PathVariable("id") String id) {
        Response<PlaySevenReward> result = new Response<PlaySevenReward>();
        PlaySevenReward model = playSevenRewardService.get(id);
        result.setRows(model);
        result.setTotal(model!=null ?  1 : 0);
        return result;
    }
    
    /**
     * 提交
     * @return
     */
     @RequestMapping(value = "", method = RequestMethod.POST)
     public Response<PlaySevenReward> submitPlaySeven(@RequestBody PlaySevenReward model, @AdminLogined IUser user) {
         Response<PlaySevenReward> check = AuthValidate.checkAdmin(user);
         if (check != null) {
             return check;
         }
         Response<PlaySevenReward> resp = new Response<>();
         if (StringUtils.isEmpty(model.getId())) { //新增
        	 model.setId(UUID.randomUUID().toString());
             model.setCreateTime(new Date());
             model.setModifyTime(new Date());
             resp.setRows(playSevenRewardService.submit((model)));
         }else {
        	 PlaySevenReward modelDb = playSevenRewardService.get(model.getId());
             if(modelDb==null){
            	 resp.setResultCode(ErrorTag.PLAY_REWARD_NOT_EXISTS.getCode());
                 resp.setResultMsg(ErrorTag.PLAY_REWARD_NOT_EXISTS.getMessage());
             }
             else{
            	 model.setCreateTime(modelDb.getCreateTime());
            	 model.setModifyTime(new Date());
                 resp.setRows(playSevenRewardService.submit(model));
             }
         }
         return resp;
     }
     
     /**
      * 删除
      * @param PlaySevenReward
      * @param user
      * @return
      */
      @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
      public Response<PlaySevenReward> deletePlaySevenReward(@PathVariable("id") String id, @AdminLogined IUser user) {
          Response<PlaySevenReward> check = AuthValidate.checkAdmin(user);
          if (check != null) {
              return check;
          }
          Response<PlaySevenReward> resp = new Response<>();
          if (!StringUtils.isEmpty(id)) {
              PlaySevenReward modelDb = playSevenRewardService.get(id);
              if(modelDb==null){
                  resp.setResultCode(ErrorTag.PLAY_REWARD_NOT_EXISTS.getCode());
                  resp.setResultMsg(ErrorTag.PLAY_REWARD_NOT_EXISTS.getMessage());
              }
              else{
            	  playSevenRewardService.delete(id);
              }
          }
          else { 
              resp.setResultCode(ErrorTag.PARAM_NULL.getCode());
              resp.setResultMsg(ErrorTag.PARAM_NULL.getMessage());
          }
          return resp;
      }
}
