package MediChecker.MediChecker.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "benh_ly_nen", schema = "public")
@Data
public class BenhLyNen {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "benh_nhan_id", nullable = false)
    private BenhNhan benhNhan;
    
    @Column(name = "ma_benh", nullable = false, length = 10)
    private String maBenh;
    
    @Column(name = "ten_benh", nullable = false, length = 200)
    private String tenBenh;
    
    @Column(name = "mo_ta", length = 500)
    private String moTa;
    
    @Column(name = "muc_do_nghiem_trong")
    @Enumerated(EnumType.STRING)
    private MucDoNghiemTrong mucDoNghiemTrong;
    
    @CreationTimestamp
    @Column(name = "ngay_chan_doan")
    private LocalDateTime ngayChanDoan;
    
    public enum MucDoNghiemTrong {
        NHE, VUA, NANG, RAT_NANG
    }
}
