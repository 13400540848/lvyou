package org.ume.school.modules.user.money.buy;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.entity.UserMoneyBuy;
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
@RequestMapping("/v0.1/admin/user/money/buy")
public class UserMoneyBuyAdminController {

    @Resource
    private UserMoneyBuyService userMoneyBuyService;
    
    @Resource
    private UserMoneyService userMoneyService;
    
    @Resource
    private UserMoneyLogService userMoneyLogService;
    
    /**
     * 获取用户买入记录
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response<List<UserMoneyBuy>> getList(@RequestParam(value = "sellId", required = false) String sellId,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "offset", required = true) String offset,
            @RequestParam(value = "limit", required = true) String limit, @AdminLogined IUser user) {
        Response<List<UserMoneyBuy>> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        Response<List<UserMoneyBuy>> result = new Response<List<UserMoneyBuy>>();
        final UserMoneyBuy query = new UserMoneyBuy();
        query.setSellId(sellId);
        query.setUserId(userId);
        Page<UserMoneyBuy> projects = userMoneyBuyService.findAll(query, offset, limit);
        result.setTotal(projects.getTotalElements());
        result.setRows(userMoneyBuyService.parse(projects.getContent()));
        return result;
    }
}
