package MediChecker.MediChecker.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "benh_nhan")
@Data
@EqualsAndHashCode(callSuper = false)
public class BenhNhan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "ho_ten", nullable = false, length = 100)
    private String hoTen;
    
    @Column(name = "ngay_sinh")
    private LocalDate ngaySinh;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "gioi_tinh")
    private GioiTinh gioiTinh;
    
    @Column(name = "so_dien_thoai", length = 15)
    private String soDienThoai;
    
    @Column(name = "email", length = 100)
    private String email;
    
    @Column(name = "dia_chi", length = 500)
    private String diaChi;
    
    @Column(name = "so_bao_hiem", length = 20)
    private String soBaoHiem;
    
    @Column(name = "ma_benh_nhan", unique = true, length = 20)
    private String maBenhNhan;
    
    @CreationTimestamp
    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;
    
    @UpdateTimestamp
    @Column(name = "ngay_cap_nhat")
    private LocalDateTime ngayCapNhat;
    
    @OneToMany(mappedBy = "benhNhan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BenhLyNen> danhSachBenhLyNen;
    
    @OneToMany(mappedBy = "benhNhan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DiUngThuoc> danhSachDiUng;
    
    @OneToMany(mappedBy = "benhNhan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DieuTri> danhSachDieuTri;
    
    public enum GioiTinh {
        NAM, NU, KHAC
    }
}
