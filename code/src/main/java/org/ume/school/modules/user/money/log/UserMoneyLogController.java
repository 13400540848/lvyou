package org.ume.school.modules.user.money.log;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.entity.UserMoneyLog;
import org.ume.school.modules.project.ProjectService;
import org.ume.school.modules.project.money.ProjectMoneyService;
import org.ume.school.modules.user.money.UserMoneyService;

import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Logined;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.utils.AuthValidate;

/**
 * Created by Zz on 2018/9/12.
 */
@RestController
@RequestMapping("/v0.1/user/money/log")
public class UserMoneyLogController {

    @Resource
    private UserMoneyLogService userMoneyLogService;
    
    @Resource
    private ProjectService projectService;
    
    @Resource
    private ProjectMoneyService projectMoneyService;
    
    @Resource
    private UserMoneyService userMoneyService;

    /**
     * 获取我的交易记录
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Response<List<UserMoneyLog>> getList(
            @RequestParam(value = "typeId", required = false) String typeId,
            @RequestParam(value = "offset", required = true) String offset,
            @RequestParam(value = "limit", required = true) String limit, @Logined IUser user) {
        Response<List<UserMoneyLog>> check = AuthValidate.checkUser(user);
        if (check != null) {
            return check;
        }
        Response<List<UserMoneyLog>> result = new Response<List<UserMoneyLog>>();
        final UserMoneyLog query = new UserMoneyLog();
//        query.setUserId(user.getId());
        query.setTypeId(typeId);
        Page<UserMoneyLog> projects = userMoneyLogService.findAll(query, offset, limit);
        result.setTotal(projects.getTotalElements());
        result.setRows(userMoneyLogService.parse(projects.getContent()));
        return result;
    }
}
