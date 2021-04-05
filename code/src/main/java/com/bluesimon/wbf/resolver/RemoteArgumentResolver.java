package com.bluesimon.wbf.resolver;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.bluesimon.wbf.Remote;
import com.bluesimon.wbf.RemoteClient;

public class RemoteArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * 检查解析器是否支持解析该参数
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(Remote.class) != null &&
                parameter.getParameterType() == RemoteClient.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        RemoteClient remoteClient = new RemoteClient();
        remoteClient.setRemoteAddr(request.getRemoteAddr());
        remoteClient.setRemoteHost(request.getRemoteHost());
        remoteClient.setRemotePort(request.getRemotePort());
        return remoteClient;
    }
}