package MediChecker.MediChecker.controller;

import MediChecker.MediChecker.dto.request.ChiTietDonThuocRequest;
import MediChecker.MediChecker.dto.request.DonThuocRequest;
import MediChecker.MediChecker.dto.response.DonThuocResponse;
import MediChecker.MediChecker.service.DonThuocService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prescriptions")
@RequiredArgsConstructor
@Tag(name = "Quản lý đơn thuốc ", description = "API quản lý đơn thuốc")
public class DonThuocController {
    
    private final DonThuocService donThuocService;
    
    @GetMapping
    @Operation(summary = "Danh sách đơn thuốc", description = "Lấy danh sách tất cả đơn thuốc với phân trang")
    public ResponseEntity<Page<DonThuocResponse>> getDonThuocDieuTri(
            @Parameter(description = "Thông tin phân trang") Pageable pageable,
            @Parameter(description = "ID bệnh nhân") @RequestParam(required = false) Long benhNhanId) {
        Page<DonThuocResponse> result = donThuocService.getDonThuocDieuTri(pageable, benhNhanId);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Chi tiết đơn thuốc", description = "Lấy thông tin chi tiết của một đơn thuốc")
    public ResponseEntity<DonThuocResponse> getChiTietDonThuoc(
            @Parameter(description = "ID đơn thuốc") @PathVariable Long id) {
        DonThuocResponse result = donThuocService.getChiTietDonThuoc(id);
        return ResponseEntity.ok(result);
    }
    
    @PostMapping
    @Operation(summary = "Tạo đơn thuốc", description = "Tạo đơn thuốc mới")
    public ResponseEntity<DonThuocResponse> taoDonThuoc(
            @Valid @RequestBody DonThuocRequest request) {
        DonThuocResponse result = donThuocService.taoDonThuoc(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật đơn thuốc", description = "Cập nhật thông tin đơn thuốc")
    public ResponseEntity<DonThuocResponse> capNhatDonThuoc(
            @Parameter(description = "ID đơn thuốc") @PathVariable Long id,
            @Valid @RequestBody DonThuocRequest request) {
        DonThuocResponse result = donThuocService.capNhatDonThuoc(id, request);
        return ResponseEntity.ok(result);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa đơn thuốc", description = "Xóa đơn thuốc")
    public ResponseEntity<Void> xoaDonThuoc(
            @Parameter(description = "ID đơn thuốc") @PathVariable Long id) {
        donThuocService.xoaDonThuoc(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{id}/items")
    @Operation(summary = "Thêm thuốc vào đơn", description = "Thêm thuốc vào đơn thuốc")
    public ResponseEntity<DonThuocResponse> themThuocVaoDon(
            @Parameter(description = "ID đơn thuốc") @PathVariable Long id,
            @Valid @RequestBody ChiTietDonThuocRequest request) {
        DonThuocResponse result = donThuocService.themThuocVaoDon(id, request);
        return ResponseEntity.ok(result);
    }
    
    @DeleteMapping("/{id}/items/{drugId}")
    @Operation(summary = "Xóa thuốc khỏi đơn", description = "Xóa thuốc khỏi đơn thuốc")
    public ResponseEntity<DonThuocResponse> xoaThuocKhoiDon(
            @Parameter(description = "ID đơn thuốc") @PathVariable Long id,
            @Parameter(description = "ID thuốc") @PathVariable Long drugId) {
        DonThuocResponse result = donThuocService.xoaThuocKhoiDon(id, drugId);
        return ResponseEntity.ok(result);
    }
    
    @PutMapping("/{id}/items/{drugId}")
    @Operation(summary = "Cập nhật thuốc trong đơn", description = "Cập nhật liều lượng, đường dùng thuốc trong đơn")
    public ResponseEntity<DonThuocResponse> capNhatThuocTrongDon(
            @Parameter(description = "ID đơn thuốc") @PathVariable Long id,
            @Parameter(description = "ID thuốc") @PathVariable Long drugId,
            @Valid @RequestBody ChiTietDonThuocRequest request) {
        DonThuocResponse result = donThuocService.capNhatThuocTrongDon(id, drugId, request);
        return ResponseEntity.ok(result);
    }
}
