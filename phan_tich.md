# Phân tích bảo mật động bằng Custom Annotation

## Input / Output

| Hàm | Input | Output thành công | Output khi OTP sai, rỗng hoặc `null` |
| --- | --- | --- | --- |
| `withdraw` | `amount`, `otp` | `Rút tiền thành công` | `Rút tiền thất bại: Sai OTP` |
| `transfer` | `toUser`, `amount`, `otp` | `Chuyển khoản thành công` | `Chuyển khoản thất bại: Sai OTP` |
| `getBalance` | Không có | `Số dư hiện tại` | Không yêu cầu OTP |

OTP hợp lệ trong bài demo là `123456`. OTP `null`, chuỗi rỗng, chuỗi chỉ chứa khoảng trắng hoặc OTP khác giá trị hợp lệ đều bị từ chối.

## Tại sao dùng `@RequiresOTP`?

Annotation biểu diễn trực tiếp quy tắc nghiệp vụ: hàm nào có `@RequiresOTP` thì bắt buộc xác thực OTP. Aspect chỉ quét annotation và xử lý bảo mật tập trung trước khi chạy logic giao dịch. Service không còn chứa đoạn `if (!verifyOtp(otp))` lặp lại.

So với pointcut quét theo tên hàm như `withdraw*` hoặc `transfer*`, annotation tối ưu hơn vì:

1. Không phụ thuộc quy ước đặt tên. Đổi tên hàm không vô tình làm mất bảo mật.
2. Khi thêm loại giao dịch nhạy cảm mới, dev chủ động gắn `@RequiresOTP`; không cần sửa biểu thức pointcut.
3. Code dễ review: nhìn vào khai báo hàm là biết chính sách bảo mật.
4. Tránh quét nhầm một hàm có tên giống giao dịch nhưng không cần OTP.
5. Thuộc tính `otpParameterIndex` xác định chính xác vị trí OTP, tránh nhầm với tham số chuỗi khác như `toUser`.

## Luồng xử lý

1. Caller gọi method của Spring bean `FintechService`.
2. `OtpSecurityAspect` chặn các method có `@RequiresOTP`.
3. Aspect lấy OTP theo `otpParameterIndex` và gọi `OtpVerifier`.
4. Nếu OTP không hợp lệ, aspect trả về thông báo lỗi và không chạy method service.
5. Nếu OTP hợp lệ, aspect gọi `proceed()` để thực thi nghiệp vụ.

`getBalance()` không có annotation nên không bị aspect chặn.
