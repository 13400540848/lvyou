package org.ume.school.modules.user.play.seven.info;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.entity.UserPlaySevenInfo;

import com.bluesimon.wbf.AdminLogined;
import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.utils.AuthValidate;

/**
 * Created by Zz on 2018/9/13.
 */
@RestController
@RequestMapping("/v0.1/admin/user/play/seven/info")
public class UserPlaySevenInfoAdminController {

    @Resource
    private UserPlaySevenInfoService userPlaySevenInfoService;
    
    /**
     * 获取列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Response<List<UserPlaySevenInfo>> getList(@AdminLogined IUser user) {
        Response<List<UserPlaySevenInfo>> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        Response<List<UserPlaySevenInfo>> result = new Response<List<UserPlaySevenInfo>>();
//        List<UserPlaySevenInfo> list = userPlaySevenInfoService.findByUserIdAndCheckStatus(userId, checkStatus)(user.getId());
//        result.setRows(list);
//        result.setTotal(list.isEmpty() ?  0 : list.size());
        return result;
    }
}
