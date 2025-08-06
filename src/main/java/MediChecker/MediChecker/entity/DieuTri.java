package MediChecker.MediChecker.entity;

import MediChecker.MediChecker.enumer.TrangThaiDieuTri;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "dieu_tri", schema = "public")
@Data
public class DieuTri {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "benh_nhan_id", nullable = false)
    private BenhNhan benhNhan;
    
    @Column(name = "ma_chan_doan", length = 10)
    private String maChanDoan;
    
    @Column(name = "chan_doan_chinh", nullable = false, length = 500)
    private String chanDoanChinh;
    
    @Column(name = "chan_doan_phu", length = 500)
    private String chanDoanPhu;
    
    @Column(name = "trieu_chung", length = 1000)
    private String trieuChung;
    
    @Column(name = "bac_si_dieu_tri", length = 100)
    private String bacSiDieuTri;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "trang_thai")
    private TrangThaiDieuTri trangThai;
    
    @CreationTimestamp
    @Column(name = "ngay_bat_dau")
    private LocalDateTime ngayBatDau;
    
    @Column(name = "ngay_ket_thuc")
    private LocalDateTime ngayKetThuc;
    
    @UpdateTimestamp
    @Column(name = "ngay_cap_nhat")
    private LocalDateTime ngayCapNhat;

    @OneToOne(mappedBy = "dieuTri", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private DonThuoc DonThuocDieuTri;
    

}
