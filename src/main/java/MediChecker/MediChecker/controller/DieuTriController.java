package MediChecker.MediChecker.controller;

import MediChecker.MediChecker.dto.request.DieuTriDTO;
import MediChecker.MediChecker.service.DieuTriService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/dieu-tri")
@RequiredArgsConstructor
@Tag(name = "Quản lý lịch sử điều trị", description = "API quản lý thông tin điều trị của bệnh nhân")
public class DieuTriController {

    private final DieuTriService dieuTriService;

    @PostMapping
    @Operation(summary = "thêm điều trị cho bệnh nhân", description = "thêm điều trị cho bệnh nhân")
    public ResponseEntity<DieuTriDTO> create(@Valid @RequestBody DieuTriDTO dto) {
        DieuTriDTO created = dieuTriService.create(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "chi tiêt điều trị", description = "chi tiết điều trị")
    public ResponseEntity<DieuTriDTO> findById(@PathVariable Long id) {
        DieuTriDTO dto = dieuTriService.findById(id);
        return ResponseEntity.ok(dto);
    }


    @GetMapping("/benh-nhan/{benhNhanId}")
    @Operation(summary = "Danh sách lịch sử điều trị của bệnh nhân", description = "Danh sách lịch sử điều trị của bệnh nhân")
    public ResponseEntity<List<DieuTriDTO>> findByBenhNhanId(@PathVariable Long benhNhanId) {
        List<DieuTriDTO> list = dieuTriService.findByBenhNhanId(benhNhanId);
        return ResponseEntity.ok(list);
    }


    @PutMapping("/{id}")
    @Operation(summary = "chỉnh sửa lịch sử điều trị của bệnh nhân", description = "chỉnh sửa lịch sử điều trị của bệnh nhân")
    public ResponseEntity<DieuTriDTO> update(@PathVariable Long id, @Valid @RequestBody DieuTriDTO dto) {
        DieuTriDTO updated = dieuTriService.update(id, dto);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "xóa lịch sử điều trị của bệnh nhân", description = "xóa lịch sử điều trị của bệnh nhân")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        dieuTriService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
