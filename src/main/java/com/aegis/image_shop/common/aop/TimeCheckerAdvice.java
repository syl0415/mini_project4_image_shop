package com.aegis.image_shop.common.aop;

import java.util.Arrays;

import com.aegis.image_shop.common.domain.PerformanceLog;
import com.aegis.image_shop.common.service.PerformanceLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Aspect
public class TimeCheckerAdvice {

    @Autowired
    private PerformanceLogService service;

    // 서비스 성능 로깅 처리
    @Around("execution(* com.aegis.image_shop.service.*Service*.*(..))")
    public Object timeLog(ProceedingJoinPoint pjp) throws Throwable {

        long startTime = System.currentTimeMillis();
        log.info(Arrays.toString(pjp.getArgs()));

        Signature signature = pjp.getSignature();
        Object target = pjp.getTarget();

        String signatureName = signature.getName();
        String signatureDeclaringTypeName = signature.getDeclaringTypeName();

        log.info("signature.getName() = " + signatureName);
        log.info("signature.getDeclaringTypeName() = " + signatureDeclaringTypeName);
        log.info("target = " + target);

        Object result = pjp.proceed();

        long endTime = System.currentTimeMillis();

        long durationTime = endTime - startTime;

        log.info(pjp.getSignature().getName() + " : " + durationTime);

        PerformanceLog performanceLog = new PerformanceLog();
        performanceLog.setSignatureName(signatureName);
        performanceLog.setSignatureTypeName(signatureDeclaringTypeName);
        performanceLog.setDurationTime(durationTime);

        service.register(performanceLog);

        return result;
    }

}
