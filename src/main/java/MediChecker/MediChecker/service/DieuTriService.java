package MediChecker.MediChecker.service;

import MediChecker.MediChecker.dto.request.DieuTriDTO;
import MediChecker.MediChecker.entity.BenhNhan;
import MediChecker.MediChecker.entity.DieuTri;
import MediChecker.MediChecker.enumer.TrangThaiDieuTri;
import MediChecker.MediChecker.repository.BenhNhanRepository;
import MediChecker.MediChecker.repository.DieuTriRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DieuTriService {
    
    private final DieuTriRepository dieuTriRepository;
    private final BenhNhanRepository benhNhanRepository;
    
    public DieuTriDTO create(DieuTriDTO dto) {
        // Kiểm tra bệnh nhân tồn tại
        BenhNhan benhNhan = benhNhanRepository.findById(dto.getBenhNhanId())
            .orElseThrow(() -> new RuntimeException("Không tìm thấy bệnh nhân với ID: " + dto.getBenhNhanId())  );
        
        DieuTri dieuTri = new DieuTri();
        dieuTri.setBenhNhan(benhNhan);
        dieuTri.setMaChanDoan(dto.getMaChanDoan());
        dieuTri.setChanDoanChinh(dto.getChanDoanChinh());
        dieuTri.setChanDoanPhu(dto.getChanDoanPhu());
        dieuTri.setTrieuChung(dto.getTrieuChung());
        dieuTri.setBacSiDieuTri(dto.getBacSiDieuTri());
        dieuTri.setTrangThai(dto.getTrangThai() != null ? dto.getTrangThai() : TrangThaiDieuTri.DANG_DIEU_TRI);
        dieuTri.setNgayKetThuc(dto.getNgayKetThuc());
        
        DieuTri saved = dieuTriRepository.save(dieuTri);
        return convertToDTO(saved);
    }
    
    @Transactional(readOnly = true)
    public DieuTriDTO findById(Long id) {
        DieuTri dieuTri = dieuTriRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy điều trị với ID: " + id));
        return convertToDTO(dieuTri);
    }
    
    @Transactional(readOnly = true)
    public Page<DieuTriDTO> findAll(Pageable pageable) {
        return dieuTriRepository.findAll(pageable).map(this::convertToDTO);
    }
    
    @Transactional(readOnly = true)
    public List<DieuTriDTO> findByBenhNhanId(Long benhNhanId) {
        return dieuTriRepository.findByBenhNhanId(benhNhanId)
            .stream()
            .map(this::convertToDTO)
            .toList();
    }
    

    @Transactional(readOnly = true)
    public Page<DieuTriDTO> searchByKeyword(Long benhNhanId, String keyword, Pageable pageable) {
        return dieuTriRepository.findByBenhNhanIdAndKeyword(benhNhanId, keyword, pageable)
            .map(this::convertToDTO);
    }
    
    public DieuTriDTO update(Long id, DieuTriDTO dto) {
        DieuTri existing = dieuTriRepository.findById(id)
            .orElseThrow(() ->new RuntimeException("Không tìm thấy điều trị với ID: " + id) );
        
        // Cập nhật bệnh nhân nếu thay đổi
        if (!existing.getBenhNhan().getId().equals(dto.getBenhNhanId())) {
            BenhNhan benhNhan = benhNhanRepository.findById(dto.getBenhNhanId())
                .orElseThrow(() ->new RuntimeException("Không tìm thấy bệnh nhân với ID: " + dto.getBenhNhanId()) );
            existing.setBenhNhan(benhNhan);
        }
        
        existing.setMaChanDoan(dto.getMaChanDoan());
        existing.setChanDoanChinh(dto.getChanDoanChinh());
        existing.setChanDoanPhu(dto.getChanDoanPhu());
        existing.setTrieuChung(dto.getTrieuChung());
        existing.setBacSiDieuTri(dto.getBacSiDieuTri());
        existing.setTrangThai(dto.getTrangThai());
        existing.setNgayKetThuc(dto.getNgayKetThuc());
        
        DieuTri updated = dieuTriRepository.save(existing);
        return convertToDTO(updated);
    }
    
    public DieuTriDTO updateTrangThai(Long id, TrangThaiDieuTri trangThai) {
        DieuTri existing = dieuTriRepository.findById(id)
            .orElseThrow(() ->new RuntimeException("Không tìm thấy điều trị với ID: " + id) );
        
        // Kiểm tra logic chuyển trạng thái
        validateTrangThaiTransition(existing.getTrangThai(), trangThai);
        
        existing.setTrangThai(trangThai);
        
        // Tự động cập nhật ngày kết thúc khi hoàn thành
        if (trangThai == TrangThaiDieuTri.HOAN_THANH && existing.getNgayKetThuc() == null) {
            existing.setNgayKetThuc(LocalDateTime.now());
        }
        
        DieuTri updated = dieuTriRepository.save(existing);
        return convertToDTO(updated);
    }
    
    public void delete(Long id) {
        DieuTri existing = dieuTriRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy điều trị với ID: " + id));
        
        // Kiểm tra có thể xóa không (ví dụ: không xóa khi đang điều trị)
        if (existing.getTrangThai() == TrangThaiDieuTri.DANG_DIEU_TRI) {
            throw new RuntimeException("Không thể xóa điều trị đang thực hiện");
        }
        
        dieuTriRepository.deleteById(id);
    }
    
    private void validateTrangThaiTransition(TrangThaiDieuTri currentState, TrangThaiDieuTri newState) {
        // Logic kiểm tra chuyển trạng thái hợp lệ
        if (currentState == TrangThaiDieuTri.HOAN_THANH && newState != TrangThaiDieuTri.HOAN_THANH) {
            throw new RuntimeException("Không thể thay đổi trạng thái từ HOÀN THÀNH");
        }
        
        if (currentState == TrangThaiDieuTri.HUY_BO && newState != TrangThaiDieuTri.HUY_BO) {
            throw new RuntimeException("Không thể thay đổi trạng thái từ HỦY BỎ");
        }
    }
    
    private DieuTriDTO convertToDTO(DieuTri entity) {
        DieuTriDTO dto = new DieuTriDTO();
        dto.setId(entity.getId());
        dto.setBenhNhanId(entity.getBenhNhan().getId());
        dto.setMaChanDoan(entity.getMaChanDoan());
        dto.setChanDoanChinh(entity.getChanDoanChinh());
        dto.setChanDoanPhu(entity.getChanDoanPhu());
        dto.setTrieuChung(entity.getTrieuChung());
        dto.setBacSiDieuTri(entity.getBacSiDieuTri());
        dto.setTrangThai(entity.getTrangThai());
        dto.setNgayBatDau(entity.getNgayBatDau());
        dto.setNgayKetThuc(entity.getNgayKetThuc());
        dto.setNgayCapNhat(entity.getNgayCapNhat());
        return dto;
    }
}
