package com.re.fintechotpaop.security;

import org.springframework.stereotype.Component;

@Component
public class OtpVerifier {

    private static final String VALID_OTP = "123456";

    public boolean verify(String otp) {
        return otp != null && !otp.isBlank() && VALID_OTP.equals(otp);
    }
}
