package MediChecker.MediChecker.dto.response;

import MediChecker.MediChecker.entity.BenhNhan;
import MediChecker.MediChecker.entity.DieuTri;
import MediChecker.MediChecker.entity.DonThuoc;
import MediChecker.MediChecker.enumer.TrangThaiDieuTri;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "Điều trị")
public class DieuTriResponse {
    private Long id;

    private String maChanDoan;

    private String chanDoanChinh;

    private String chanDoanPhu;

    private String trieuChung;

    private String bacSiDieuTri;

    private TrangThaiDieuTri trangThai;

    private LocalDateTime ngayBatDau;

    private LocalDateTime ngayKetThuc;

    private LocalDateTime ngayCapNhat;

    private DonThuocResponse DonThuocDieuTri;


}
