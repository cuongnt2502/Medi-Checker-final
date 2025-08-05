package MediChecker.MediChecker.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Thông tin thuốc trong đơn")
public class ChiTietDonThuocRequest {
    
    @NotNull(message = "ID thuốc không được để trống")
    @Schema(description = "ID thuốc", example = "1")
    private Long thuocId;
    
    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    @Schema(description = "Số lượng", example = "30")
    private Integer soLuong;
    
    @NotBlank(message = "Liều dùng không được để trống")
    @Size(max = 200, message = "Liều dùng không được vượt quá 200 ký tự")
    @Schema(description = "Liều dùng", example = "1 viên/lần")
    private String lieuDung;
    
    @Size(max = 100, message = "Đường dùng không được vượt quá 100 ký tự")
    @Schema(description = "Đường dùng", example = "Uống")
    private String duongDung;
    
    @Size(max = 100, message = "Tần suất không được vượt quá 100 ký tự")
    @Schema(description = "Tần suất", example = "3 lần/ngày")
    private String tanSuat;
    
    @Size(max = 100, message = "Thời gian dùng không được vượt quá 100 ký tự")
    @Schema(description = "Thời gian dùng", example = "7 ngày")
    private String thoiGianDung;
    
    @Size(max = 500, message = "Hướng dẫn sử dụng không được vượt quá 500 ký tự")
    @Schema(description = "Hướng dẫn sử dụng", example = "Uống sau ăn 30 phút")
    private String huongDanSuDung;
}
