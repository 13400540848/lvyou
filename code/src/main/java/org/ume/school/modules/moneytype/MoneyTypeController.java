package org.ume.school.modules.moneytype;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.ErrorTag;
import org.ume.school.modules.model.entity.MoneyType;

import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Logined;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.utils.AuthValidate;

/**
 * Created by Zz on 2018/8/30.
 */
@RestController
@RequestMapping("/v0.1/moneytype")
public class MoneyTypeController {

    @Resource
    private MoneyTypeService moneyTypeService;

    /**
     * 获取币种列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Response<List<MoneyType>> getMoneyTypes() {
        List<MoneyType> list = moneyTypeService.findList();
        return new Response<>(list);
    }
    
    /**
     * 获取币种
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<MoneyType> getMoneyType(@PathVariable("id") String id, @Logined IUser user) {
        Response<MoneyType> check = AuthValidate.checkUser(user);
        if (check != null) {
            return check;
        }
        MoneyType model = moneyTypeService.findById(id);
        Response<MoneyType> resp = new Response<MoneyType>();
        if(model==null){
            resp.setResultMsg(ErrorTag.PARAM_NULL.getMessage());
            resp.setResultCode(ErrorTag.PARAM_NULL.getCode());
            return resp;
        }
        return new Response<>(model);
    }
}
