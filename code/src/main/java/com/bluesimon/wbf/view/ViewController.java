package com.bluesimon.wbf.view;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.ume.school.modules.model.ErrorTag;

import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.modules.user.UserEntity;
import com.bluesimon.wbf.utils.RandomUtil;
import com.bluesimon.wbf.utils.SMSUtil;

/**
 * Created by Django on 2017/7/15.
 */
@RestController
public class ViewController {

//    @Resource
//    private IPageDataTunnel pageDataTunnel;
//
//    @Resource
//    private AuthenticationService authenticationService;

    @RequestMapping("/")
    public ModelAndView goHome() {
        return new ModelAndView("redirect:/web/index.html");
    }

//    /**
//     * 通用的页面跳转，跳转的页面在pathVariable中定义，如果频道代码为空，则默认此page就是一个模块。
//     * 一个页面可对应不同模块块，使用: “${page}-${channel}”
//     *
//     * @param page
//     * @param request
//     * @param model
//     * @param channel
//     * @param user
//     * @return
//     */
//    @RequestMapping(value = "/{page}", method = RequestMethod.GET)
//    public ModelAndView doDispatch(@PathVariable("page") String page,
//                                   HttpServletRequest request,
//                                   ModelAndView model,
//                                   @RequestParam(value = "channel", defaultValue = "", required = false) String channel,
//                                   @Logined IUser user,
//                                   @ModelAttribute("RedirectMap") Object redirectMap) {
//        //页面授权检测
//        boolean letpass = authenticationService.checkBackList(page, user);
//        if (!letpass) {
//            return new ModelAndView("redirect:/login");
//        }
//
//        String queryString = request.getQueryString();
//        Map<String, String> params = parseQueryString(queryString);
//        String module = page;
//        //channel指定，则模块值为 page-channel;否则，模块值为page ； module==channel，是指一个页面单频道模块
//        if (!StringUtils.isEmpty(channel)) {
//            module = module + "-" + channel;
//        } else {
//            channel = page;
//        }
//        params.put("channel", channel);
//        params.put("module", module);
//
//        //参数传递
//        model.getModel().putAll(params);
//
//        //设置目标page
//        model.setViewName(page);
//
//        //设置用户
//        if (null != user) {
//            model.getModel().put("user", user);
//        }
//
//        //设置Redirect参数
//        if (null != redirectMap) {
//            model.getModel().put("redirectMap", redirectMap);
//        }
//
//        //默认给页面增加初始化数据
//        if (null != pageDataTunnel) {
//            List<JSON> response = pageDataTunnel.assemblePageData(page, new Principle(user, params));
//            model.getModel().put("datas", response);
//        }
//        return model;
//    }

    private Map<String, String> parseQueryString(String queryString) {
        Map<String, String> params = new HashMap<>();
        if (!StringUtils.isEmpty(queryString)) {
            String[] qs = queryString.split("&");
            for (String q : qs) {
                String[] qe = q.split("=");
                if (qe.length > 1) {
                    params.put(qe[0], qe[1]);
                }
            }
        }
        return params;
    }

    /**
     * 发送手机验证码（开放）
     */
    @RequestMapping(value = "/v0.1/sms", method = RequestMethod.POST)
    public Response<Integer> smsCode(@RequestBody UserEntity userReq, HttpServletRequest request) {       
        Response<Integer> resp = new Response<>();
        if (userReq.getMobilePhone() == null || userReq.getMobilePhone().isEmpty()) {
            resp.setResultCode(ErrorTag.PARAM_NULL.getCode());
            resp.setResultMsg(ErrorTag.PARAM_NULL.getMessage());
            return resp;
        }
        //生成6位数字验证码
        String code = RandomUtil.createNumber(6);
        //发送验证码到手机
        SMSUtil sms = new SMSUtil();
        try {
            sms.send(userReq.getMobilePhone(), code);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //设置session
        request.getSession().setAttribute(IUser.SMS_CODE, code);
        return resp;
    }
}
