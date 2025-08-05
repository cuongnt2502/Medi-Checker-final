package MediChecker.MediChecker.controller;

import MediChecker.MediChecker.dto.response.PhanTichResponse;
import MediChecker.MediChecker.service.PhanTichService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/analysis")
@RequiredArgsConstructor
@Tag(name = "Phân tích đơn thuốc", description = "API phân tích tương tác và chống chỉ định thuốc")
public class PhanTichController {
    
    private final PhanTichService phanTichService;
    
    @PostMapping("/contraindications")
    @Operation(summary = "Phân tích chống chỉ định", description = "Phân tích chống chỉ định theo bệnh nền của bệnh nhân")
    public ResponseEntity<PhanTichResponse> phanTichChongChiDinh(
            @RequestBody Map<String, Object> request) {
        Long patientId = Long.valueOf(request.get("patientId").toString());
        Long prescriptionId = Long.valueOf(request.get("prescriptionId").toString());
        
        PhanTichResponse result = phanTichService.phanTichChongChiDinh(patientId, prescriptionId);
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/interactions")
    @Operation(summary = "Phân tích tương tác thuốc", description = "Phân tích tương tác thuốc - thuốc trong đơn")
    public ResponseEntity<PhanTichResponse> phanTichTuongTac(
            @RequestBody Map<String, Object> request) {
        Long prescriptionId = Long.valueOf(request.get("prescriptionId").toString());
        
        PhanTichResponse result = phanTichService.phanTichTuongTac(prescriptionId);
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/duplicate-indications")
    @Operation(summary = "Phân tích trùng chỉ định", description = "Phân tích thuốc trùng lặp tác dụng trong đơn")
    public ResponseEntity<PhanTichResponse> phanTichTrungChiDinh(
            @RequestBody Map<String, Object> request) {
        Long prescriptionId = Long.valueOf(request.get("prescriptionId").toString());
        
        PhanTichResponse result = phanTichService.phanTichTrungChiDinh(prescriptionId);
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/inappropriate-drugs")
    @Operation(summary = "Phân tích thuốc không hợp lý", description = "Phân tích thuốc không hợp lý theo chẩn đoán")
    public ResponseEntity<PhanTichResponse> phanTichKhongHopLy(
            @RequestBody Map<String, Object> request) {
        String diagnosisCode = request.get("diagnosisCode").toString();
        Long prescriptionId = Long.valueOf(request.get("prescriptionId").toString());
        
        PhanTichResponse result = phanTichService.phanTichKhongHopLy(diagnosisCode, prescriptionId);
        return ResponseEntity.ok(result);
    }
}
