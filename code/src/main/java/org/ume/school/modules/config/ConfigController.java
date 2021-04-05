package org.ume.school.modules.config;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.entity.Config;

import com.bluesimon.wbf.Response;

/**
 * Created by Zz on 2018/10/13.
 */
@RestController
@RequestMapping("/v0.1/config")
public class ConfigController {

    @Resource
    private ConfigService configService;

//    /**
//     * 获取列表
//     */
//    @RequestMapping(value = "/list", method = RequestMethod.GET)
//    public Response<List<Config>> getConfigs() {
//        List<Config> list = configService.findList();
//        return new Response<>(list);
//    }
//    
//    /**
//     * 获取详情
//     */
//    @RequestMapping(value = "/{code}", method = RequestMethod.GET)
//    public Response<Config> getConfigByCode(@PathVariable("code") String code) {
//        Config model = configService.findByCode(code);
//        return new Response<>(model);
//    }
}
