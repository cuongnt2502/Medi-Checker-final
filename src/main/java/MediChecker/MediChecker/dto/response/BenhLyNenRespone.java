package MediChecker.MediChecker.dto.response;

import MediChecker.MediChecker.entity.BenhLyNen;
import MediChecker.MediChecker.entity.BenhNhan;
import MediChecker.MediChecker.enumer.MucDoNghiemTrong;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Schema(description = "Thông tin bệnh nền")
public class BenhLyNenRespone {
    @Schema(description = "ID bệnh nền", example = "1")
    private Long id;

    private String maBenh;

    private String tenBenh;

    private String moTa;

    private MucDoNghiemTrong mucDoNghiemTrong;

    private LocalDateTime ngayChanDoan;

}
