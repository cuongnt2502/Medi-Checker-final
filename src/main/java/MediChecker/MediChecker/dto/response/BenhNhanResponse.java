package MediChecker.MediChecker.dto.response;

import MediChecker.MediChecker.entity.BenhNhan;
import MediChecker.MediChecker.enumer.GioiTinh;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Schema(description = "Thông tin bệnh nhân")
public class BenhNhanResponse {
    
    @Schema(description = "ID bệnh nhân", example = "1")
    private Long id;
    
    @Schema(description = "Mã bệnh nhân", example = "BN001")
    private String maBenhNhan;
    
    @Schema(description = "Họ và tên", example = "Nguyễn Văn An")
    private String hoTen;
    
    @Schema(description = "Ngày sinh", example = "1990-01-15")
    private LocalDate ngaySinh;
    
    @Schema(description = "Giới tính", example = "NAM")
    private GioiTinh gioiTinh;
    
    @Schema(description = "Số điện thoại", example = "0901234567")
    private String soDienThoai;
    
    @Schema(description = "Email", example = "nguyenvanan@email.com")
    private String email;
    
    @Schema(description = "Địa chỉ", example = "123 Đường ABC, Quận 1, TP.HCM")
    private String diaChi;
    
    @Schema(description = "Số bảo hiểm", example = "DN1234567890")
    private String soBaoHiem;
    
    @Schema(description = "Ngày tạo", example = "2024-01-15T10:30:00")
    private LocalDateTime ngayTao;
    
    @Schema(description = "Ngày cập nhật", example = "2024-01-15T10:30:00")
    private LocalDateTime ngayCapNhat;

}
