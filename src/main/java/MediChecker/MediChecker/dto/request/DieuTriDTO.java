package MediChecker.MediChecker.dto.request;

import MediChecker.MediChecker.dto.response.DonThuocResponse;
import lombok.Data;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import MediChecker.MediChecker.enumer.TrangThaiDieuTri;

@Data
public class DieuTriDTO {
    private Long id;
    
    @NotNull(message = "Bệnh nhân ID không được để trống")
    private Long benhNhanId;
    
    @Size(max = 10, message = "Mã chẩn đoán không được vượt quá 10 ký tự")
    private String maChanDoan;
    
    @NotBlank(message = "Chẩn đoán chính không được để trống")
    @Size(max = 500, message = "Chẩn đoán chính không được vượt quá 500 ký tự")
    private String chanDoanChinh;
    
    @Size(max = 500, message = "Chẩn đoán phụ không được vượt quá 500 ký tự")
    private String chanDoanPhu;
    
    @Size(max = 1000, message = "Triệu chứng không được vượt quá 1000 ký tự")
    private String trieuChung;
    
    @Size(max = 100, message = "Bác sĩ điều trị không được vượt quá 100 ký tự")
    private String bacSiDieuTri;

    private TrangThaiDieuTri trangThai;
    private LocalDateTime ngayBatDau;
    private LocalDateTime ngayKetThuc;
    private LocalDateTime ngayCapNhat;
    private DonThuocRequest DonThuocDieuTri;

}
