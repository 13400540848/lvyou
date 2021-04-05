package org.ume.school.modules.user.money.sell;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.entity.UserMoneySell;
import org.ume.school.modules.user.money.UserMoneyService;
import org.ume.school.modules.user.money.log.UserMoneyLogService;

import com.bluesimon.wbf.AdminLogined;
import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.utils.AuthValidate;

/**
 * Created by Zz on 2018/9/16.
 */
@RestController
@RequestMapping("/v0.1/admin/user/money/sell")
public class UserMoneySellAdminController {

    @Resource
    private UserMoneySellService userMoneySellService;
    
    @Resource
    private UserMoneyService userMoneyService;
    
    @Resource
    private UserMoneyLogService userMoneyLogService;
    
    /**
     * 获取用户卖出记录
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response<List<UserMoneySell>> getList(@RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "offset", required = true) String offset,
            @RequestParam(value = "limit", required = true) String limit, @AdminLogined IUser user) {
        Response<List<UserMoneySell>> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        Response<List<UserMoneySell>> result = new Response<List<UserMoneySell>>();
        final UserMoneySell query = new UserMoneySell();
        query.setUserId(userId);
        Page<UserMoneySell> projects = userMoneySellService.findAll(query, offset, limit);
        result.setTotal(projects.getTotalElements());
        result.setRows(userMoneySellService.parse(projects.getContent()));
        return result;
    }
}
