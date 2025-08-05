package MediChecker.MediChecker.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "thuoc")
@Data
public class Thuoc {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "ma_thuoc", unique = true, nullable = false, length = 20)
    private String maThuoc;
    
    @Column(name = "ten_thuoc", nullable = false, length = 200)
    private String tenThuoc;
    
    @Column(name = "ten_hoat_chat", length = 200)
    private String tenHoatChat;
    
    @Column(name = "nong_do", length = 50)
    private String nongDo;
    
    @Column(name = "dang_bao_che", length = 100)
    private String dangBaoChe;
    
    @Column(name = "hang_san_xuat", length = 100)
    private String hangSanXuat;
    
    @Column(name = "nuoc_san_xuat", length = 50)
    private String nuocSanXuat;
    
    @Column(name = "gia_ban", precision = 10, scale = 2)
    private BigDecimal giaBan;
    
    @Column(name = "don_vi_tinh", length = 20)
    private String donViTinh;
    
    @Column(name = "chi_dinh", length = 1000)
    private String chiDinh;
    
    @Column(name = "chong_chi_dinh", length = 1000)
    private String chongChiDinh;
    
    @Column(name = "tac_dung_phu", length = 1000)
    private String tacDungPhu;
    
    @Column(name = "lieu_dung_nguoi_lon", length = 500)
    private String lieuDungNguoiLon;
    
    @Column(name = "lieu_dung_tre_em", length = 500)
    private String lieuDungTreEm;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "nhom_thuoc")
    private NhomThuoc nhomThuoc;
    
    @Column(name = "kich_hoat", nullable = false)
    private Boolean kichHoat = true;
    
    @CreationTimestamp
    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;
    
    @UpdateTimestamp
    @Column(name = "ngay_cap_nhat")
    private LocalDateTime ngayCapNhat;
    
    @OneToMany(mappedBy = "thuoc", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DiUngThuoc> danhSachDiUng;
    
    public enum NhomThuoc {
        KHANG_SINH, GIAM_DAU, CHONG_VIEM, TIM_MACH, TIEU_HOA, HOI_SUC, KHAC
    }
}
