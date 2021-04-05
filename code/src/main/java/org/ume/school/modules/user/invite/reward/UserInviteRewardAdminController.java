package org.ume.school.modules.user.invite.reward;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.entity.UserInviteReward;

import com.bluesimon.wbf.AdminLogined;
import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.utils.AuthValidate;

/**
 * Created by Zz on 2018/10/11.
 */
@RestController
@RequestMapping("/v0.1/admin/user/invite/reward")
public class UserInviteRewardAdminController {

    @Resource
    private UserInviteRewardService userInviteRewardService;

    /**
     * 获取用户的邀请奖励
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response<List<UserInviteReward>> getUserInviteRewards(@RequestParam("userId") String userId, @AdminLogined IUser user) {
        Response<List<UserInviteReward>> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        List<UserInviteReward> list = userInviteRewardService.findByReferrerId(userId);
        return new Response<>(userInviteRewardService.parse(list));
    }
}
