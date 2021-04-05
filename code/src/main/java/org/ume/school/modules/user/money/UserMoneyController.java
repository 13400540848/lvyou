package org.ume.school.modules.user.money;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.entity.UserMoney;

import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Logined;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.utils.AuthValidate;

/**
 * Created by Zz on 2018/8/30.
 */
@RestController
@RequestMapping("/v0.1/user/money")
public class UserMoneyController {

    @Resource
    private UserMoneyService userMoneyService;

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Response<List<UserMoney>> getUserMoneys(@Logined IUser user) {
        Response<List<UserMoney>> check = AuthValidate.checkUser(user);
        if (check != null) {
            return check;
        }
//        List<UserMoney> list = userMoneyService.findByUserId(user.getId());
//        return new Response<>(userMoneyService.parse(list));
        return null;
    }
    
    /**
     * 获取
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<UserMoney> getUserMoney(@PathVariable("id") String id, @Logined IUser user) {
        Response<UserMoney> check = AuthValidate.checkUser(user);
        if (check != null) {
            return check;
        }
        UserMoney model = userMoneyService.findById(id);
        if(model.getUserId().equals(user.getId())){
            return new Response<>(userMoneyService.parse(model));
        }
        return new Response<>();
    }
}
