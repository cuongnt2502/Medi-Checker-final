package MediChecker.MediChecker.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "don_thuoc", schema = "public")
@Data
public class DonThuoc {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "ma_don_thuoc", unique = true, nullable = false, length = 20)
    private String maDonThuoc;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "benh_nhan_id", nullable = false)
    private BenhNhan benhNhan;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dieu_tri_id")
    private DieuTri dieuTri;
    
    @Column(name = "bac_si_ke_don", nullable = false, length = 100)
    private String bacSiKeDon;
    
    @Column(name = "ghi_chu", length = 1000)
    private String ghiChu;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "trang_thai")
    private TrangThaiDonThuoc trangThai;
    
    @CreationTimestamp
    @Column(name = "ngay_ke_don")
    private LocalDateTime ngayKeDon;
    
    @UpdateTimestamp
    @Column(name = "ngay_cap_nhat")
    private LocalDateTime ngayCapNhat;
    
    @OneToMany(mappedBy = "donThuoc", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChiTietDonThuoc> danhSachThuoc;
    
    public enum TrangThaiDonThuoc {
        MOI_TAO, DA_DUYET, DANG_THUC_HIEN, HOAN_THANH, HUY_BO
    }
}
