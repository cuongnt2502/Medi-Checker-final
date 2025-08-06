package MediChecker.MediChecker.service;

import MediChecker.MediChecker.dto.request.BenhLyNenDTO;
import MediChecker.MediChecker.entity.BenhLyNen;
import MediChecker.MediChecker.entity.BenhNhan;
import MediChecker.MediChecker.repository.BenhLyNenRepository;
import MediChecker.MediChecker.repository.BenhNhanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BenhLyNenService {
    
    private final BenhLyNenRepository benhLyNenRepository;
    private final BenhNhanRepository benhNhanRepository;
    
    public BenhLyNenDTO create(BenhLyNenDTO dto) {
        // Kiểm tra bệnh nhân tồn tại
        BenhNhan benhNhan = benhNhanRepository.findById(dto.getBenhNhanId())
            .orElseThrow(() ->   new RuntimeException("Không tìm thấy bệnh nhân với ID: " + dto.getId()));
        
        // Kiểm tra mã bệnh đã tồn tại
        if (benhLyNenRepository.existsByMaBenh(dto.getMaBenh())) {
            throw new RuntimeException("Mã bệnh đã tồn tại: " + dto.getMaBenh());
        }
        
        BenhLyNen benhLyNen = new BenhLyNen();
        benhLyNen.setBenhNhan(benhNhan);
        benhLyNen.setMaBenh(dto.getMaBenh());
        benhLyNen.setTenBenh(dto.getTenBenh());
        benhLyNen.setMoTa(dto.getMoTa());
        benhLyNen.setMucDoNghiemTrong(dto.getMucDoNghiemTrong());
        
        BenhLyNen saved = benhLyNenRepository.save(benhLyNen);
        return convertToDTO(saved);
    }
    
    @Transactional(readOnly = true)
    public BenhLyNenDTO findById(Long id) {
        BenhLyNen benhLyNen = benhLyNenRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy bệnh lý nền với ID: " + id) );
        return convertToDTO(benhLyNen);
    }
    
    @Transactional(readOnly = true)
    public Page<BenhLyNenDTO> findAll(Pageable pageable) {
        return benhLyNenRepository.findAll(pageable).map(this::convertToDTO);
    }
    
    @Transactional(readOnly = true)
    public List<BenhLyNenDTO> findByBenhNhanId(Long benhNhanId) {
        return benhLyNenRepository.findByBenhNhanId(benhNhanId)
            .stream()
            .map(this::convertToDTO)
            .toList();
    }
    
    @Transactional(readOnly = true)
    public Page<BenhLyNenDTO> searchByKeyword(Long benhNhanId, String keyword, Pageable pageable) {
        return benhLyNenRepository.findByBenhNhanIdAndKeyword(benhNhanId, keyword, pageable)
            .map(this::convertToDTO);
    }
    
    public BenhLyNenDTO update(Long id, BenhLyNenDTO dto) {
        BenhLyNen existing = benhLyNenRepository.findById(id)
            .orElseThrow(() ->new RuntimeException("Không tìm thấy bệnh lý nền với ID: " + id) );
        
        // Kiểm tra mã bệnh đã tồn tại (trừ bản ghi hiện tại)
        if (benhLyNenRepository.existsByMaBenhAndIdNot(dto.getMaBenh(), id)) {
            throw  new RuntimeException("Mã bệnh đã tồn tại: " + dto.getMaBenh());
        }
        
        // Cập nhật bệnh nhân nếu thay đổi
        if (!existing.getBenhNhan().getId().equals(dto.getBenhNhanId())) {
            BenhNhan benhNhan = benhNhanRepository.findById(dto.getBenhNhanId())
                .orElseThrow(() ->new RuntimeException("Không tìm thấy bệnh nhân với ID: " + dto.getBenhNhanId()) );
            existing.setBenhNhan(benhNhan);
        }
        
        existing.setMaBenh(dto.getMaBenh());
        existing.setTenBenh(dto.getTenBenh());
        existing.setMoTa(dto.getMoTa());
        existing.setMucDoNghiemTrong(dto.getMucDoNghiemTrong());
        
        BenhLyNen updated = benhLyNenRepository.save(existing);
        return convertToDTO(updated);
    }
    
    public void delete(Long id) {
        if (!benhLyNenRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy bệnh lý nền với ID: " + id) ;
        }
        benhLyNenRepository.deleteById(id);
    }
    
    private BenhLyNenDTO convertToDTO(BenhLyNen entity) {
        BenhLyNenDTO dto = new BenhLyNenDTO();
        dto.setId(entity.getId());
        dto.setBenhNhanId(entity.getBenhNhan().getId());
        dto.setMaBenh(entity.getMaBenh());
        dto.setTenBenh(entity.getTenBenh());
        dto.setMoTa(entity.getMoTa());
        dto.setMucDoNghiemTrong(entity.getMucDoNghiemTrong());
        dto.setNgayChanDoan(entity.getNgayChanDoan());
        return dto;
    }
}
