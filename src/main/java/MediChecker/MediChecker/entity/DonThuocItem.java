package MediChecker.MediChecker.entity;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "don_thuoc_item", schema = "public")
@Data
public class DonThuocItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ten_thuoc", nullable = false, length = 200)
    private String tenThuoc;

    @Column(name = "lieu_luong", nullable = false, length = 20)
    private String lieuLuong;


}
