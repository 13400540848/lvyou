package org.ume.school.modules.user.invite.reward;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.entity.UserInviteReward;

import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Logined;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.utils.AuthValidate;

/**
 * Created by Zz on 2018/10/11.
 */
@RestController
@RequestMapping("/v0.1/user/invite/reward")
public class UserInviteRewardController {

    @Resource
    private UserInviteRewardService userInviteRewardService;

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Response<List<UserInviteReward>> getUserInviteRewards(@Logined IUser user) {
        Response<List<UserInviteReward>> check = AuthValidate.checkUser(user);
        if (check != null) {
            return check;
        }
//        List<UserInviteReward> list = userInviteRewardService.findByReferrerId(user.getId());
//        return new Response<>(userInviteRewardService.parse(list));
        return null;
    }
    
    /**
     * 获取
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<UserInviteReward> getUserInviteReward(@PathVariable("id") String id, @Logined IUser user) {
        Response<UserInviteReward> check = AuthValidate.checkUser(user);
        if (check != null) {
            return check;
        }
        UserInviteReward model = userInviteRewardService.findById(id);
        if(model.getUserId().equals(user.getId())){
            return new Response<>(userInviteRewardService.parse(model));
        }
        return new Response<>();
    }
}
