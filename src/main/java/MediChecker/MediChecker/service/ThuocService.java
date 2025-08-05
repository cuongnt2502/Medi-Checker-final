package MediChecker.MediChecker.service;

import MediChecker.MediChecker.dto.request.ThuocRequest;
import MediChecker.MediChecker.dto.response.ThuocResponse;
import MediChecker.MediChecker.entity.Thuoc;
import MediChecker.MediChecker.repository.ThuocRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ThuocService {
    
    private final ThuocRepository thuocRepository;
    
    public Page<ThuocResponse> getDanhSachThuoc(Pageable pageable, String keyword) {
        Page<Thuoc> thuocPage;
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            thuocPage = thuocRepository.findByTenThuocContainingIgnoreCaseOrTenHoatChatContainingIgnoreCaseAndKichHoatTrue(
                    keyword.trim(), keyword.trim(), pageable);
        } else {
            thuocPage = thuocRepository.findByKichHoatTrue(pageable);
        }
        
        return thuocPage.map(this::convertToResponse);
    }
    
    public ThuocResponse getChiTietThuoc(Long id) {
        Thuoc thuoc = thuocRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thuốc với ID: " + id));
        return convertToResponse(thuoc);
    }
    
    public ThuocResponse themThuocMoi(ThuocRequest request) {
        Thuoc thuoc = new Thuoc();
        thuoc.setMaThuoc(generateMaThuoc());
        updateThuocFromRequest(thuoc, request);
        
        Thuoc savedThuoc = thuocRepository.save(thuoc);
        return convertToResponse(savedThuoc);
    }
    
    public ThuocResponse capNhatThuoc(Long id, ThuocRequest request) {
        Thuoc thuoc = thuocRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thuốc với ID: " + id));
        
        updateThuocFromRequest(thuoc, request);
        Thuoc savedThuoc = thuocRepository.save(thuoc);
        return convertToResponse(savedThuoc);
    }
    
    public void xoaThuoc(Long id) {
        Thuoc thuoc = thuocRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thuốc với ID: " + id));
        
        thuoc.setKichHoat(false);
        thuocRepository.save(thuoc);
    }
    
    private void updateThuocFromRequest(Thuoc thuoc, ThuocRequest request) {
        thuoc.setTenThuoc(request.getTenThuoc());
        thuoc.setTenHoatChat(request.getTenHoatChat());
        thuoc.setNongDo(request.getNongDo());
        thuoc.setDangBaoChe(request.getDangBaoChe());
        thuoc.setHangSanXuat(request.getHangSanXuat());
        thuoc.setNuocSanXuat(request.getNuocSanXuat());
        thuoc.setGiaBan(request.getGiaBan());
        thuoc.setDonViTinh(request.getDonViTinh());
        thuoc.setChiDinh(request.getChiDinh());
        thuoc.setChongChiDinh(request.getChongChiDinh());
        thuoc.setTacDungPhu(request.getTacDungPhu());
        thuoc.setLieuDungNguoiLon(request.getLieuDungNguoiLon());
        thuoc.setLieuDungTreEm(request.getLieuDungTreEm());
        thuoc.setNhomThuoc(request.getNhomThuoc());
    }
    
    private ThuocResponse convertToResponse(Thuoc thuoc) {
        ThuocResponse response = new ThuocResponse();
        response.setId(thuoc.getId());
        response.setMaThuoc(thuoc.getMaThuoc());
        response.setTenThuoc(thuoc.getTenThuoc());
        response.setTenHoatChat(thuoc.getTenHoatChat());
        response.setNongDo(thuoc.getNongDo());
        response.setDangBaoChe(thuoc.getDangBaoChe());
        response.setHangSanXuat(thuoc.getHangSanXuat());
        response.setNuocSanXuat(thuoc.getNuocSanXuat());
        response.setGiaBan(thuoc.getGiaBan());
        response.setDonViTinh(thuoc.getDonViTinh());
        response.setChiDinh(thuoc.getChiDinh());
        response.setChongChiDinh(thuoc.getChongChiDinh());
        response.setNhomThuoc(thuoc.getNhomThuoc());
        response.setKichHoat(thuoc.getKichHoat());
        response.setNgayTao(thuoc.getNgayTao());
        return response;
    }
    
    private String generateMaThuoc() {
        String prefix = "T";
        String timestamp = String.valueOf(System.currentTimeMillis()).substring(8);
        String random = UUID.randomUUID().toString().substring(0, 3).toUpperCase();
        return prefix + timestamp + random;
    }
}
