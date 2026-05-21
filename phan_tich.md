- Kiến trúc phân lớp xử lý giao dịch tập trung
Khi Client gửi một yêu cầu giao dịch, luồng xử lý sẽ đi qua 4 chặng độc lập như sau:

Client ---> [1. Controller] ---> [2. AOP Aspect] ---> [3. Service]
^                      |
|                      v
[4. GlobalExceptionHandler] <--- (Nếu có lỗi/Exception bắn ra)

- Nhiệm vụ từng tầng:
1. Tầng Controller: Nhận request từ Client, chuyển tiếp dữ liệu xuống tầng dưới. Không xử lý logic, không kiểm tra dữ liệu.
2. Tầng AOP Aspect: Đứng chặn trước Service để thực hiện:
    + Kiểm tra bẫy dữ liệu (Số tiền âm, Địa chỉ ví quá ngắn).
    + Kiểm tra rủi ro (Giao dịch lớn hơn 10,000 USD).
    + Nếu vi phạm -> Chủ động ném Exception thích hợp để chặt đứt luồng chạy.
3. Tầng Service: Chỉ chạy khi dữ liệu đã "sạch hoàn toàn". Thực hiện duy nhất tác vụ cốt lõi: Cập nhật số dư/Ghi nhận giao dịch thành công.
4. Tầng GlobalExceptionHandler: Nơi gom tất cả các lỗi xảy ra ở tầng AOP hoặc Service, chuyển đổi thành định dạng JSON chuẩn trả về cho Client.