package com.re.fintechotpaop.service;

import com.re.fintechotpaop.annotation.RequiresOTP;
import org.springframework.stereotype.Service;

@Service
public class FintechService {

    @RequiresOTP(otpParameterIndex = 1, failureMessage = "Rút tiền thất bại: Sai OTP")
    public String withdraw(double amount, String otp) {
        return "Rút tiền thành công";
    }

    @RequiresOTP(otpParameterIndex = 2, failureMessage = "Chuyển khoản thất bại: Sai OTP")
    public String transfer(String toUser, double amount, String otp) {
        return "Chuyển khoản thành công";
    }

    public String getBalance() {
        return "Số dư hiện tại";
    }
}
