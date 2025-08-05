package MediChecker.MediChecker.dto.request;

import MediChecker.MediChecker.entity.Thuoc;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Thông tin tạo/cập nhật thuốc")
public class ThuocRequest {
    
    @NotBlank(message = "Tên thuốc không được để trống")
    @Size(max = 200, message = "Tên thuốc không được vượt quá 200 ký tự")
    @Schema(description = "Tên thuốc", example = "Paracetamol 500mg")
    private String tenThuoc;
    
    @Size(max = 200, message = "Tên hoạt chất không được vượt quá 200 ký tự")
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
    
    @DecimalMin(value = "0.0", message = "Giá bán phải lớn hơn 0")
    @Schema(description = "Giá bán", example = "5000.00")
    private BigDecimal giaBan;
    
    @Schema(description = "Đơn vị tính", example = "Viên")
    private String donViTinh;
    
    @Schema(description = "Chỉ định", example = "Giảm đau, hạ sốt")
    private String chiDinh;
    
    @Schema(description = "Chống chỉ định", example = "Suy gan nặng")
    private String chongChiDinh;
    
    @Schema(description = "Tác dụng phụ", example = "Buồn nôn, chóng mặt")
    private String tacDungPhu;
    
    @Schema(description = "Liều dùng người lớn", example = "1-2 viên/lần, 3-4 lần/ngày")
    private String lieuDungNguoiLon;
    
    @Schema(description = "Liều dùng trẻ em", example = "0.5-1 viên/lần, 2-3 lần/ngày")
    private String lieuDungTreEm;
    
    @Schema(description = "Nhóm thuốc", example = "GIAM_DAU")
    private Thuoc.NhomThuoc nhomThuoc;
}
