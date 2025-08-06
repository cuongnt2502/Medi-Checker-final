package MediChecker.MediChecker.dto.response;

import MediChecker.MediChecker.entity.BenhNhan;
import MediChecker.MediChecker.entity.DiUngThuoc;
import MediChecker.MediChecker.entity.Thuoc;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Schema(description = "Di ứng thuốc")
public class DiUngThuocRespone {
    private Long id;

    private ThuocResponse thuoc;

    private String trieuChung;

    private DiUngThuoc.MucDoNghiemTrong mucDoNghiemTrong;

    private LocalDateTime ngayPhatHien;

    public enum MucDoNghiemTrong {
        NHE, VUA, NANG, NGUY_HIEM
    }
}
