package com.aegis.image_shop.common.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.aegis.image_shop.common.domain.AccessLog;
import com.aegis.image_shop.common.service.AccessLogService;
import com.aegis.image_shop.common.util.NetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AccessLoggingInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AccessLogService service;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    // 접근 로깅 처리
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String requestUri = request.getRequestURI();

        String remoteAddr = NetUtils.getIp(request);

        log.info("requestURL : " + requestUri);
        log.info("remoteAddr : " + remoteAddr);

        if(handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();

            Class<?> clazz = method.getDeclaringClass();

            String className = clazz.getName();
            String classSimpleName = clazz.getSimpleName();
            String methodName = method.getName();

            log.info("[ACCESS CONTROLLER] " + className + "." + methodName);

            AccessLog accessLog = new AccessLog();

            accessLog.setRequestUri(requestUri);
            accessLog.setRemoteAddr(remoteAddr);
            accessLog.setClassName(className);
            accessLog.setClassSimpleName(classSimpleName);
            accessLog.setMethodName(methodName);

            if(service != null) {
                log.info("service != null");

                service.register(accessLog);
            }
            else {
                log.info("service == null");
            }
        }
        else {
            log.info("handler : " + handler);
        }

    }

}
