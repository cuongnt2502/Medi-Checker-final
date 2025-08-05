package MediChecker.MediChecker.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Thông tin tạo đơn thuốc")
public class DonThuocRequest {
    
    @NotNull(message = "ID bệnh nhân không được để trống")
    @Schema(description = "ID bệnh nhân", example = "1")
    private Long benhNhanId;
    
    @Schema(description = "ID điều trị", example = "1")
    private Long dieuTriId;
    
    @NotBlank(message = "Bác sĩ kê đơn không được để trống")
    @Size(max = 100, message = "Tên bác sĩ không được vượt quá 100 ký tự")
    @Schema(description = "Bác sĩ kê đơn", example = "BS. Nguyễn Văn B")
    private String bacSiKeDon;
    
    @Size(max = 1000, message = "Ghi chú không được vượt quá 1000 ký tự")
    @Schema(description = "Ghi chú", example = "Uống sau ăn")
    private String ghiChu;
}
