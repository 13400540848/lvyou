package org.ume.school.modules.user.play.three.info;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.entity.UserPlayThreeInfo;

import com.bluesimon.wbf.AdminLogined;
import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.utils.AuthValidate;

/**
 * Created by Zz on 2018/11/1.
 */
@RestController
@RequestMapping("/v0.1/admin/user/play/three/info")
public class UserPlayThreeInfoAdminController {

    @Resource
    private UserPlayThreeInfoService userPlayThreeInfoService;
    
    /**
     * 获取列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Response<List<UserPlayThreeInfo>> getList(@AdminLogined IUser user) {
        Response<List<UserPlayThreeInfo>> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        Response<List<UserPlayThreeInfo>> result = new Response<List<UserPlayThreeInfo>>();
//        List<UserPlayThreeInfo> list = userPlayThreeInfoService.findByUserIdAndCheckStatus(userId, checkStatus)(user.getId());
//        result.setRows(list);
//        result.setTotal(list.isEmpty() ?  0 : list.size());
        return result;
    }
}
