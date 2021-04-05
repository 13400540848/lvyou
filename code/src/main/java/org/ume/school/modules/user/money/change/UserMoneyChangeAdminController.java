package org.ume.school.modules.user.money.change;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.entity.UserMoneyChange;

import com.bluesimon.wbf.AdminLogined;
import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.utils.AuthValidate;

/**
 * Created by Zz on 2018/10/15.
 */
@RestController
@RequestMapping("/v0.1/admin/user/money/change")
public class UserMoneyChangeAdminController {

    @Resource
    private UserMoneyChangeService userMoneyChangeService;
    
    /**
     * 获取记录
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response<List<UserMoneyChange>> getList(@RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "offset", required = true) String offset,
            @RequestParam(value = "limit", required = true) String limit, @AdminLogined IUser user) {
        Response<List<UserMoneyChange>> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        Response<List<UserMoneyChange>> result = new Response<List<UserMoneyChange>>();
        final UserMoneyChange query = new UserMoneyChange();
        query.setUserId(userId);
        Page<UserMoneyChange> projects = userMoneyChangeService.findAll(query, offset, limit);
        result.setTotal(projects.getTotalElements());
        result.setRows(userMoneyChangeService.parse(projects.getContent()));
        return result;
    }
}
