package com.re.fintechotpaop.aspect;

import com.re.fintechotpaop.annotation.RequiresOTP;
import com.re.fintechotpaop.security.OtpVerifier;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class OtpSecurityAspect {

    private final OtpVerifier otpVerifier;

    public OtpSecurityAspect(OtpVerifier otpVerifier) {
        this.otpVerifier = otpVerifier;
    }

    @Around("@annotation(requiresOTP)")
    public Object verifyOtp(ProceedingJoinPoint joinPoint, RequiresOTP requiresOTP) throws Throwable {
        Object[] arguments = joinPoint.getArgs();
        int otpParameterIndex = requiresOTP.otpParameterIndex();

        if (otpParameterIndex < 0 || otpParameterIndex >= arguments.length) {
            throw new IllegalStateException("@RequiresOTP has an invalid otpParameterIndex");
        }

        Object otpArgument = arguments[otpParameterIndex];
        if (!(otpArgument instanceof String otp) || !otpVerifier.verify(otp)) {
            return requiresOTP.failureMessage();
        }

        return joinPoint.proceed();
    }
}
