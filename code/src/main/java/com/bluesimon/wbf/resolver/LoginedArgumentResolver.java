package com.bluesimon.wbf.resolver;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Logined;

public class LoginedArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * 检查解析器是否支持解析该参数
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(Logined.class) != null &&
                //如果该参数的类型为User
                parameter.getParameterType() == IUser.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
//        String token = request.getHeader("token");
//        if(userTokenService==null){
//            userTokenService = new UserTokenService();
//        }
//        return userTokenService.getUser(token);
//        return (IUser) request.getAttribute(IUser.LOGIN_USER);
//        String token = request.getHeader("token");
//        if(!StringUtils.isEmpty(token)){
//            return userTokenRepository.findByToken(token).getUser(token);
//        }
        //这里暂时把User对象放在session中
        return (IUser) request.getSession().getAttribute(IUser.LOGIN_USER);
//        return null;
    }
}