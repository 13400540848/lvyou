package org.ume.school.modules.user.money.log;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.entity.UserMoneyLog;

import com.bluesimon.wbf.AdminLogined;
import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.utils.AuthValidate;

/**
 * Created by Zz on 2018/9/12.
 */
@RestController
@RequestMapping("/v0.1/admin/user/money/log")
public class UserMoneyLogAdminController {

    @Resource
    private UserMoneyLogService userMoneyLogService;

    /**
     * 获取交易记录
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response<List<UserMoneyLog>> getUserProjectMoneys(@RequestParam("userId") String userId,
            @RequestParam(value = "offset", required = true) String offset,
            @RequestParam(value = "limit", required = true) String limit, @AdminLogined IUser user) {
        Response<List<UserMoneyLog>> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        Response<List<UserMoneyLog>> result = new Response<List<UserMoneyLog>>();
        final UserMoneyLog query = new UserMoneyLog();
        query.setUserId(userId);
        Page<UserMoneyLog> projects = userMoneyLogService.findAll(query, offset, limit);
        result.setTotal(projects.getTotalElements());
        result.setRows(userMoneyLogService.parse(projects.getContent()));
        return result;
    }
}
