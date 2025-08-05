package MediChecker.MediChecker.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "di_ung_thuoc")
@Data
public class DiUngThuoc {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "benh_nhan_id", nullable = false)
    private BenhNhan benhNhan;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thuoc_id", nullable = false)
    private Thuoc thuoc;
    
    @Column(name = "trieu_chung", length = 500)
    private String trieuChung;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "muc_do_nghiem_trong")
    private MucDoNghiemTrong mucDoNghiemTrong;
    
    @CreationTimestamp
    @Column(name = "ngay_phat_hien")
    private LocalDateTime ngayPhatHien;
    
    public enum MucDoNghiemTrong {
        NHE, VUA, NANG, NGUY_HIEM
    }
}
