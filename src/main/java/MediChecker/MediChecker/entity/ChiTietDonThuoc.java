package MediChecker.MediChecker.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "chi_tiet_don_thuoc")
@Data
public class ChiTietDonThuoc {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "don_thuoc_id", nullable = false)
    private DonThuoc donThuoc;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thuoc_id", nullable = false)
    private Thuoc thuoc;
    
    @Column(name = "so_luong", nullable = false)
    private Integer soLuong;
    
    @Column(name = "lieu_dung", nullable = false, length = 200)
    private String lieuDung;
    
    @Column(name = "duong_dung", length = 100)
    private String duongDung;
    
    @Column(name = "tan_suat", length = 100)
    private String tanSuat;
    
    @Column(name = "thoi_gian_dung", length = 100)
    private String thoiGianDung;
    
    @Column(name = "huong_dan_su_dung", length = 500)
    private String huongDanSuDung;
    
    @Column(name = "gia_don_vi", precision = 10, scale = 2)
    private BigDecimal giaDonVi;
    
    @Column(name = "thanh_tien", precision = 12, scale = 2)
    private BigDecimal thanhTien;
}
