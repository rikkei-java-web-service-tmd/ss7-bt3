package com.re.fintechotpaop.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FintechServiceTests {

    @Autowired
    private FintechService fintechService;

    @Test
    void withdrawSucceedsWithValidOtp() {
        assertThat(fintechService.withdraw(100_000, "123456"))
                .isEqualTo("Rút tiền thành công");
    }

    @Test
    void withdrawFailsWithNullOtp() {
        assertThat(fintechService.withdraw(100_000, null))
                .isEqualTo("Rút tiền thất bại: Sai OTP");
    }

    @Test
    void withdrawFailsWithEmptyOtp() {
        assertThat(fintechService.withdraw(100_000, ""))
                .isEqualTo("Rút tiền thất bại: Sai OTP");
    }

    @Test
    void transferFailsWithBlankOtp() {
        assertThat(fintechService.transfer("customer-b", 100_000, "  "))
                .isEqualTo("Chuyển khoản thất bại: Sai OTP");
    }

    @Test
    void getBalanceDoesNotRequireOtp() {
        assertThat(fintechService.getBalance())
                .isEqualTo("Số dư hiện tại");
    }
}
