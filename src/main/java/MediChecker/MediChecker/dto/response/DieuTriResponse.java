package MediChecker.MediChecker.dto.response;

import MediChecker.MediChecker.entity.BenhNhan;
import MediChecker.MediChecker.entity.DieuTri;
import MediChecker.MediChecker.entity.DonThuoc;
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

    private DieuTri.TrangThaiDieuTri trangThai;

    private LocalDateTime ngayBatDau;

    private LocalDateTime ngayKetThuc;

    private LocalDateTime ngayCapNhat;

    private List<DonThuocResponse> danhSachDonThuoc;

    public enum TrangThaiDieuTri {
        DANG_DIEU_TRI, HOAN_THANH, TAM_DUNG, HUY_BO
    }
}
