package org.ume.school.modules.user.money;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.entity.UserMoney;

import com.bluesimon.wbf.AdminLogined;
import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.utils.AuthValidate;

/**
 * Created by Zz on 2018/9/3.
 */
@RestController
@RequestMapping("/v0.1/admin/user/money")
public class UserMoneyAdminController {

    @Resource
    private UserMoneyService userMoneyService;

    /**
     * 获取用户的钱包
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response<List<UserMoney>> getUserMoneys(@RequestParam("userId") String userId, @AdminLogined IUser user) {
        Response<List<UserMoney>> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        List<UserMoney> list = userMoneyService.findByUserId(userId);
        return new Response<>(userMoneyService.parse(list));
    }
}
