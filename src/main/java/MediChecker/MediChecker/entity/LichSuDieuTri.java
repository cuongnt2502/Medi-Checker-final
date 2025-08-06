package MediChecker.MediChecker.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "lich_su_dieu_tri", schema = "public")
@Data
public class LichSuDieuTri {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ngay_dieu_tri", nullable = false)
    private LocalDate ngayDieuTri;

    @Column(name = "chuan_doan", nullable = false)
    private String chanDoan;

    @OneToMany(mappedBy = "lichSuDieuTri", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DonThuocItem> donThuoc;

    @Column(name = "bac_si", nullable = false)
    private String bacSi;

    @Column(name = "ghi_chu", nullable = false)
    private String ghiChu;

    @Column(name = "trang_thai", nullable = false)
    private String trangThai;
}
