package MediChecker.MediChecker.controller;

import MediChecker.MediChecker.dto.request.ThuocRequest;
import MediChecker.MediChecker.dto.response.ThuocResponse;
import MediChecker.MediChecker.service.ThuocService;
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
@RequestMapping("/drugs")
@RequiredArgsConstructor
@Tag(name = "Quản lý thuốc", description = "API quản lý danh mục thuốc")
public class ThuocController {
    
    private final ThuocService thuocService;
    
    @GetMapping
    @Operation(summary = "Danh sách thuốc", description = "Lấy danh sách tất cả thuốc với phân trang")
    public ResponseEntity<Page<ThuocResponse>> getDanhSachThuoc(
            @Parameter(description = "Thông tin phân trang") Pageable pageable,
            @Parameter(description = "Từ khóa tìm kiếm") @RequestParam(required = false) String keyword) {
        Page<ThuocResponse> result = thuocService.getDanhSachThuoc(pageable, keyword);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Chi tiết thuốc", description = "Lấy thông tin chi tiết của một loại thuốc")
    public ResponseEntity<ThuocResponse> getChiTietThuoc(
            @Parameter(description = "ID thuốc") @PathVariable Long id) {
        ThuocResponse result = thuocService.getChiTietThuoc(id);
        return ResponseEntity.ok(result);
    }
    
    @PostMapping
    @Operation(summary = "Thêm thuốc mới", description = "Thêm loại thuốc mới vào danh mục")
    public ResponseEntity<ThuocResponse> themThuocMoi(
            @Valid @RequestBody ThuocRequest request) {
        ThuocResponse result = thuocService.themThuocMoi(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật thuốc", description = "Cập nhật thông tin thuốc")
    public ResponseEntity<ThuocResponse> capNhatThuoc(
            @Parameter(description = "ID thuốc") @PathVariable Long id,
            @Valid @RequestBody ThuocRequest request) {
        ThuocResponse result = thuocService.capNhatThuoc(id, request);
        return ResponseEntity.ok(result);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa thuốc", description = "Xóa thuốc khỏi danh mục")
    public ResponseEntity<Void> xoaThuoc(
            @Parameter(description = "ID thuốc") @PathVariable Long id) {
        thuocService.xoaThuoc(id);
        return ResponseEntity.noContent().build();
    }
}
