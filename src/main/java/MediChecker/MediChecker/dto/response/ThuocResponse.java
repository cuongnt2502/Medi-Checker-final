package MediChecker.MediChecker.dto.response;

import MediChecker.MediChecker.entity.Thuoc;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "Thông tin thuốc")
public class ThuocResponse {
    
    @Schema(description = "ID thuốc", example = "1")
    private Long id;
    
    @Schema(description = "Mã thuốc", example = "T001")
    private String maThuoc;
    
    @Schema(description = "Tên thuốc", example = "Paracetamol 500mg")
    private String tenThuoc;
    
    @Schema(description = "Tên hoạt chất", example = "Paracetamol")
    private String tenHoatChat;
    
    @Schema(description = "Nồng độ", example = "500mg")
    private String nongDo;
    
    @Schema(description = "Dạng bào chế", example = "Viên nén")
    private String dangBaoChe;
    
    @Schema(description = "Hãng sản xuất", example = "Công ty Dược ABC")
    private String hangSanXuat;
    
    @Schema(description = "Nước sản xuất", example = "Việt Nam")
    private String nuocSanXuat;
    
    @Schema(description = "Giá bán", example = "5000.00")
    private BigDecimal giaBan;
    
    @Schema(description = "Đơn vị tính", example = "Viên")
    private String donViTinh;
    
    @Schema(description = "Chỉ định", example = "Giảm đau, hạ sốt")
    private String chiDinh;
    
    @Schema(description = "Chống chỉ định", example = "Suy gan nặng")
    private String chongChiDinh;
    
    @Schema(description = "Nhóm thuốc", example = "GIAM_DAU")
    private Thuoc.NhomThuoc nhomThuoc;
    
    @Schema(description = "Trạng thái kích hoạt", example = "true")
    private Boolean kichHoat;
    
    @Schema(description = "Ngày tạo", example = "2024-01-15T10:30:00")
    private LocalDateTime ngayTao;
}
