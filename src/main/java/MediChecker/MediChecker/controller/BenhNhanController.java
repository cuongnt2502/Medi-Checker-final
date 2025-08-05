package MediChecker.MediChecker.controller;

import MediChecker.MediChecker.dto.request.BenhNhanRequest;
import MediChecker.MediChecker.dto.response.BenhNhanResponse;
import MediChecker.MediChecker.service.BenhNhanService;
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
@RequestMapping("/patients")
@RequiredArgsConstructor
@Tag(name = "Quản lý bệnh nhân", description = "API quản lý thông tin bệnh nhân")
public class BenhNhanController {
    
    private final BenhNhanService benhNhanService;
    
    @GetMapping
    @Operation(summary = "Danh sách bệnh nhân", description = "Lấy danh sách tất cả bệnh nhân với phân trang")
    public ResponseEntity<Page<BenhNhanResponse>> getDanhSachBenhNhan(
            @Parameter(description = "Thông tin phân trang") Pageable pageable,
            @Parameter(description = "Từ khóa tìm kiếm") @RequestParam(required = false) String keyword) {
        Page<BenhNhanResponse> result = benhNhanService.getDanhSachBenhNhan(pageable, keyword);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Chi tiết bệnh nhân", description = "Lấy thông tin chi tiết của một bệnh nhân")
    public ResponseEntity<BenhNhanResponse> getChiTietBenhNhan(
            @Parameter(description = "ID bệnh nhân") @PathVariable Long id) {
        BenhNhanResponse result = benhNhanService.getChiTietBenhNhan(id);
        return ResponseEntity.ok(result);
    }
    
    @PostMapping
    @Operation(summary = "Tạo mới bệnh nhân", description = "Tạo mới thông tin bệnh nhân")
    public ResponseEntity<BenhNhanResponse> taoMoiBenhNhan(
            @Valid @RequestBody BenhNhanRequest request) {
        BenhNhanResponse result = benhNhanService.taoMoiBenhNhan(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật bệnh nhân", description = "Cập nhật thông tin bệnh nhân")
    public ResponseEntity<BenhNhanResponse> capNhatBenhNhan(
            @Parameter(description = "ID bệnh nhân") @PathVariable Long id,
            @Valid @RequestBody BenhNhanRequest request) {
        BenhNhanResponse result = benhNhanService.capNhatBenhNhan(id, request);
        return ResponseEntity.ok(result);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa bệnh nhân", description = "Xóa thông tin bệnh nhân")
    public ResponseEntity<Void> xoaBenhNhan(
            @Parameter(description = "ID bệnh nhân") @PathVariable Long id) {
        benhNhanService.xoaBenhNhan(id);
        return ResponseEntity.noContent().build();
    }
}
