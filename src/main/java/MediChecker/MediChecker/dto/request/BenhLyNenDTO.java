package MediChecker.MediChecker.dto.request;
import MediChecker.MediChecker.enumer.MucDoNghiemTrong;
import lombok.Data;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Data
public class BenhLyNenDTO {
    private Long id;
    
    @NotNull(message = "Bệnh nhân ID không được để trống")
    private Long benhNhanId;
    
    @NotBlank(message = "Mã bệnh không được để trống")
    @Size(max = 10, message = "Mã bệnh không được vượt quá 10 ký tự")
    private String maBenh;
    
    @NotBlank(message = "Tên bệnh không được để trống")
    @Size(max = 200, message = "Tên bệnh không được vượt quá 200 ký tự")
    private String tenBenh;
    
    @Size(max = 500, message = "Mô tả không được vượt quá 500 ký tự")
    private String moTa;
    private LocalDateTime ngayChanDoan;

    private MucDoNghiemTrong mucDoNghiemTrong;


}
