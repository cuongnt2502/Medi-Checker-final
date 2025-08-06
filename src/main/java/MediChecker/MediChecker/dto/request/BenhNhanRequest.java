package MediChecker.MediChecker.dto.request;

import MediChecker.MediChecker.entity.BenhNhan;
import MediChecker.MediChecker.enumer.GioiTinh;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "Thông tin tạo/cập nhật bệnh nhân")
public class BenhNhanRequest {
    
    @NotBlank(message = "Họ tên không được để trống")
    @Size(max = 100, message = "Họ tên không được vượt quá 100 ký tự")
    @Schema(description = "Họ và tên bệnh nhân", example = "Nguyễn Văn An")
    private String hoTen;
    
    @Past(message = "Ngày sinh phải là ngày trong quá khứ")
    @Schema(description = "Ngày sinh", example = "1990-01-15")
    private LocalDate ngaySinh;
    
    @Schema(description = "Giới tính", example = "NAM")
    private GioiTinh gioiTinh;
    
    @Pattern(regexp = "^[0-9+\\-\\s()]{10,15}$", message = "Số điện thoại không hợp lệ")
    @Schema(description = "Số điện thoại", example = "0901234567")
    private String soDienThoai;
    
    @Email(message = "Email không hợp lệ")
    @Schema(description = "Địa chỉ email", example = "nguyenvanan@email.com")
    private String email;
    
    @Size(max = 500, message = "Địa chỉ không được vượt quá 500 ký tự")
    @Schema(description = "Địa chỉ", example = "123 Đường ABC, Quận 1, TP.HCM")
    private String diaChi;
    
    @Size(max = 20, message = "Số bảo hiểm không được vượt quá 20 ký tự")
    @Schema(description = "Số bảo hiểm y tế", example = "DN1234567890")
    private String soBaoHiem;
}
