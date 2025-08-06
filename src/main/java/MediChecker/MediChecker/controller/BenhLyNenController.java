package MediChecker.MediChecker.controller;

import MediChecker.MediChecker.dto.request.BenhLyNenDTO;
import MediChecker.MediChecker.service.BenhLyNenService;
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
@RequestMapping("/benh-ly-nen")
@RequiredArgsConstructor
@Tag(name = "Quản lý bệnh nền", description = "API quản lý thông tin bệnh nền của bệnh nhân")
public class BenhLyNenController {

    private final BenhLyNenService benhLyNenService;

    @PostMapping
    @Operation(summary = "thêm bệnh lý nền cho bệnh nhân", description = "thêm bệnh lý nền cho bệnh nhân")
    public ResponseEntity<BenhLyNenDTO> create(@Valid @RequestBody BenhLyNenDTO dto) {
        BenhLyNenDTO created = benhLyNenService.create(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "chi tiết bệnh lý nền", description = "chi tiết bệnh lý nền")
    public ResponseEntity<BenhLyNenDTO> findById(@PathVariable Long id) {
        BenhLyNenDTO dto = benhLyNenService.findById(id);
        return ResponseEntity.ok(dto);
    }


    @GetMapping("/benh-nhan/{benhNhanId}")
    @Operation(summary = "Danh sách bệnh nền của bệnh nhân", description = "Danh sách bệnh nền của bệnh nhân")
    public ResponseEntity<List<BenhLyNenDTO>> findByBenhNhanId(@PathVariable Long benhNhanId) {
        List<BenhLyNenDTO> list = benhLyNenService.findByBenhNhanId(benhNhanId);
        return ResponseEntity.ok(list);
    }


    @PutMapping("/{id}")
    @Operation(summary = "chỉnh sửa bệnh nền của bệnh nhân", description = "chỉnh sửa bệnh nền của bệnh nhân")
    public ResponseEntity<BenhLyNenDTO> update(@PathVariable Long id, @Valid @RequestBody BenhLyNenDTO dto) {
        BenhLyNenDTO updated = benhLyNenService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "xóa bệnh nền của bệnh nhân", description = "xóa bệnh nền của bệnh nhân")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        benhLyNenService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
