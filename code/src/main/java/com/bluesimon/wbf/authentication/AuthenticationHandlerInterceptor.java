package com.bluesimon.wbf.authentication;

import java.lang.annotation.Annotation;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bluesimon.wbf.modules.user.UserRepository;

/**
 * 拦截器实现请求鉴权; 默认通过，只有带有 @Authentication注解的请求才需要权限控制
 * 对Controller层的 @RestController api做校验
 * Created by Django on 2017/11/29.
 */
public class AuthenticationHandlerInterceptor extends HandlerInterceptorAdapter {

    @Resource
    AuthenticationService authenticationService; // 引入权限服务接口
    
    @Resource
    private UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS");
//        response.setHeader("Access-Control-Max-Age", "86400");
//        response.setHeader("Access-Control-Allow-Headers", "*");
//        // 如果是OPTIONS则结束请求
//        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
//            response.setStatus(HttpStatus.NO_CONTENT.value());
//            return false;
//        }
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;utf-8");
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Annotation clsAnno = handlerMethod.getBeanType().getAnnotation(RestController.class);
            if (clsAnno instanceof RestController) {
                Annotation annotation = handlerMethod.getMethod().getAnnotation(Authentication.class);
                if (annotation instanceof Authentication) {
                    Authentication authentication = (Authentication) annotation;
                    Authentication.Authority authority = authentication.name();
                    // TODO: 谁 什么请求(模块，业务) 什么权限; 此处交由权限控制中心处理
                    if (null != authenticationService) {
                        boolean hasAuth = authenticationService.checkApi();
                        if (!hasAuth) {
                            throw new Exception("无权限");
                        }
                    }
                }

//                String token = request.getHeader("token");
//                if (!StringUtils.isEmpty(token)) {
//                    UserToken ut = userTokenRepository.getOne(token);
//                    if(ut!=null && !ut.getIsExpire()){
//                        User u = userRepository.getOne(ut.getUserId());
//                    }
                    
                    
//                    User u = userTokenService.getUser(token);
//                    request.setAttribute(IUser.LOGIN_USER, u);
//                }
            }
        }
        return true;
    }

}
