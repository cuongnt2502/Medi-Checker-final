package MediChecker.MediChecker.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Chi tiết thuốc trong đơn")
public class ChiTietDonThuocResponse {
    
    @Schema(description = "ID chi tiết", example = "1")
    private Long id;
    
    @Schema(description = "Thông tin thuốc")
    private ThuocResponse thuoc;
    
    @Schema(description = "Số lượng", example = "30")
    private Integer soLuong;
    
    @Schema(description = "Liều dùng", example = "1 viên/lần")
    private String lieuDung;
    
    @Schema(description = "Đường dùng", example = "Uống")
    private String duongDung;
    
    @Schema(description = "Tần suất", example = "3 lần/ngày")
    private String tanSuat;
    
    @Schema(description = "Thời gian dùng", example = "7 ngày")
    private String thoiGianDung;
    
    @Schema(description = "Hướng dẫn sử dụng", example = "Uống sau ăn 30 phút")
    private String huongDanSuDung;
    
    @Schema(description = "Giá đơn vị", example = "5000.00")
    private BigDecimal giaDonVi;
    
    @Schema(description = "Thành tiền", example = "150000.00")
    private BigDecimal thanhTien;
}
