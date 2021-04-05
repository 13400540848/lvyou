package org.ume.school.modules.user.money.project;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.entity.UserMoneyProject;

import com.bluesimon.wbf.AdminLogined;
import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.utils.AuthValidate;

/**
 * Created by Zz on 2018/9/3.
 */
@RestController
@RequestMapping("/v0.1/admin/user/money/project")
public class UserMoneyProjectAdminController {

    @Resource
    private UserMoneyProjectService userMoneyProjectService;
    
    /**
     * 获取用户的项目投资情况
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response<List<UserMoneyProject>> getUserProjectMoneys(@RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "offset", required = true) String offset,
            @RequestParam(value = "limit", required = true) String limit, @AdminLogined IUser user) {
        Response<List<UserMoneyProject>> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        Response<List<UserMoneyProject>> result = new Response<List<UserMoneyProject>>();
        final UserMoneyProject query = new UserMoneyProject();
        query.setUserId(userId);
        Page<UserMoneyProject> projects = userMoneyProjectService.findAll(query, offset, limit);
        result.setTotal(projects.getTotalElements());
        result.setRows(userMoneyProjectService.parse(projects.getContent()));
        return result;
    }
}
