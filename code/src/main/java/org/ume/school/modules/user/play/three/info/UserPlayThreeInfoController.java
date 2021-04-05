package org.ume.school.modules.user.play.three.info;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.entity.UserPlayThreeInfo;
import org.ume.school.modules.moneytype.MoneyTypeService;

import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Logined;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.modules.user.UserService;
import com.bluesimon.wbf.utils.AuthValidate;

/**
 * Created by Zz on 2018/11/1.
 */
@RestController
@RequestMapping("/v0.1/user/play/three/info")
public class UserPlayThreeInfoController {

    @Resource
    private UserPlayThreeInfoService userPlayThreeInfoService;
        
    @Resource
    private MoneyTypeService moneyTypeService; 
    
    @Resource
    private UserService userService;

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Response<List<UserPlayThreeInfo>> getList(@Logined IUser user) {
        Response<List<UserPlayThreeInfo>> check = AuthValidate.checkUser(user);
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
