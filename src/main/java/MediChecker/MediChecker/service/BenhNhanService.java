package MediChecker.MediChecker.service;

import MediChecker.MediChecker.dto.request.BenhNhanRequest;
import MediChecker.MediChecker.dto.response.BenhNhanResponse;
import MediChecker.MediChecker.entity.BenhNhan;
import MediChecker.MediChecker.repository.BenhNhanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class BenhNhanService {
    
    private final BenhNhanRepository benhNhanRepository;
    
    public Page<BenhNhanResponse> getDanhSachBenhNhan(Pageable pageable, String keyword) {
        Page<BenhNhan> benhNhanPage;
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            benhNhanPage = benhNhanRepository.findByHoTenContainingIgnoreCaseOrMaBenhNhanContainingIgnoreCase(
                    keyword.trim(), keyword.trim(), pageable);
        } else {
            benhNhanPage = benhNhanRepository.findAll(pageable);
        }
        
        return benhNhanPage.map(this::convertToResponse);
    }
    
    public BenhNhanResponse getChiTietBenhNhan(Long id) {
        BenhNhan benhNhan = benhNhanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bệnh nhân với ID: " + id));
        return convertToResponse(benhNhan);
    }
    
    public BenhNhanResponse taoMoiBenhNhan(BenhNhanRequest request) {
        BenhNhan benhNhan = new BenhNhan();
        benhNhan.setMaBenhNhan(generateMaBenhNhan());
        updateBenhNhanFromRequest(benhNhan, request);
        
        BenhNhan savedBenhNhan = benhNhanRepository.save(benhNhan);
        return convertToResponse(savedBenhNhan);
    }
    
    public BenhNhanResponse capNhatBenhNhan(Long id, BenhNhanRequest request) {
        BenhNhan benhNhan = benhNhanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bệnh nhân với ID: " + id));
        
        updateBenhNhanFromRequest(benhNhan, request);
        BenhNhan savedBenhNhan = benhNhanRepository.save(benhNhan);
        return convertToResponse(savedBenhNhan);
    }
    
    public void xoaBenhNhan(Long id) {
        if (!benhNhanRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy bệnh nhân với ID: " + id);
        }
        benhNhanRepository.deleteById(id);
    }
    
    private void updateBenhNhanFromRequest(BenhNhan benhNhan, BenhNhanRequest request) {
        benhNhan.setHoTen(request.getHoTen());
        benhNhan.setNgaySinh(request.getNgaySinh());
        benhNhan.setGioiTinh(request.getGioiTinh());
        benhNhan.setSoDienThoai(request.getSoDienThoai());
        benhNhan.setEmail(request.getEmail());
        benhNhan.setDiaChi(request.getDiaChi());
        benhNhan.setSoBaoHiem(request.getSoBaoHiem());
    }
    
    private BenhNhanResponse convertToResponse(BenhNhan benhNhan) {
        BenhNhanResponse response = new BenhNhanResponse();
        response.setId(benhNhan.getId());
        response.setMaBenhNhan(benhNhan.getMaBenhNhan());
        response.setHoTen(benhNhan.getHoTen());
        response.setNgaySinh(benhNhan.getNgaySinh());
        response.setGioiTinh(benhNhan.getGioiTinh());
        response.setSoDienThoai(benhNhan.getSoDienThoai());
        response.setEmail(benhNhan.getEmail());
        response.setDiaChi(benhNhan.getDiaChi());
        response.setSoBaoHiem(benhNhan.getSoBaoHiem());
        response.setNgayTao(benhNhan.getNgayTao());
        response.setNgayCapNhat(benhNhan.getNgayCapNhat());
        return response;
    }
    
    private String generateMaBenhNhan() {
        String prefix = "BN";
        String timestamp = String.valueOf(System.currentTimeMillis()).substring(8);
        String random = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        return prefix + timestamp + random;
    }
}
