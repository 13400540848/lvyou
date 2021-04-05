package org.ume.school.modules.user.play.seven;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.entity.UserPlaySeven;
import org.ume.school.modules.user.money.UserMoneyService;
import org.ume.school.modules.user.money.log.UserMoneyLogService;

import com.bluesimon.wbf.AdminLogined;
import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.modules.user.UserEntity;
import com.bluesimon.wbf.modules.user.UserService;
import com.bluesimon.wbf.utils.AuthValidate;

/**
 * Created by Zz on 2018/9/13.
 */
@RestController
@RequestMapping("/v0.1/admin/user/play/seven")
public class UserPlaySevenAdminController {

    @Resource
    private UserPlaySevenService userPlaySevenService;
    
    @Resource
    private UserMoneyService userMoneyService;
    
    @Resource
    private UserMoneyLogService userMoneyLogService;
    
    @Resource
    private UserService userService;
    
    /**
     * 获取记录
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response<List<UserPlaySeven>> getList(@RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "userName", required = false) String userName,
            @RequestParam(value = "orderId", required = false) String orderId,
            @RequestParam(value = "playTime", required = false) Double playTime,            
            @RequestParam(value = "offset", required = true) String offset,
            @RequestParam(value = "limit", required = true) String limit, @AdminLogined IUser user) {
        Response<List<UserPlaySeven>> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        Response<List<UserPlaySeven>> result = new Response<List<UserPlaySeven>>();
        final UserPlaySeven query = new UserPlaySeven();
        query.setStatus(status);
        query.setOrderId(orderId);
        query.setPlayTime(playTime);
        if(userName!=null && !userName.isEmpty()) {
        	int count = userService.countByAccount(userName);
        	if(count > 0) {
//        		query.setUserId(u.getId());
        	}else {
        		query.setUserId(userName);
        	}
        }
        Page<UserPlaySeven> projects = userPlaySevenService.findAll(query, offset, limit);
        result.setTotal(projects.getTotalElements());
        result.setRows(userPlaySevenService.parse(projects.getContent()));
        return result;
    }
}
