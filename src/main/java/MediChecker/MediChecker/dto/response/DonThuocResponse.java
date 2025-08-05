package MediChecker.MediChecker.dto.response;

import MediChecker.MediChecker.entity.DonThuoc;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "Thông tin đơn thuốc")
public class DonThuocResponse {
    
    @Schema(description = "ID đơn thuốc", example = "1")
    private Long id;
    
    @Schema(description = "Mã đơn thuốc", example = "DT001")
    private String maDonThuoc;
    
    @Schema(description = "Thông tin bệnh nhân")
    private BenhNhanResponse benhNhan;
    
    @Schema(description = "Bác sĩ kê đơn", example = "BS. Nguyễn Văn B")
    private String bacSiKeDon;
    
    @Schema(description = "Ghi chú", example = "Uống sau ăn")
    private String ghiChu;
    
    @Schema(description = "Trạng thái đơn thuốc", example = "DA_DUYET")
    private DonThuoc.TrangThaiDonThuoc trangThai;
    
    @Schema(description = "Ngày kê đơn", example = "2024-01-15T10:30:00")
    private LocalDateTime ngayKeDon;
    
    @Schema(description = "Danh sách thuốc trong đơn")
    private List<ChiTietDonThuocResponse> danhSachThuoc;
}
