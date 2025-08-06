package MediChecker.MediChecker.service;

import MediChecker.MediChecker.dto.request.ChiTietDonThuocRequest;
import MediChecker.MediChecker.dto.request.DonThuocRequest;
import MediChecker.MediChecker.dto.response.BenhNhanResponse;
import MediChecker.MediChecker.dto.response.ChiTietDonThuocResponse;
import MediChecker.MediChecker.dto.response.DonThuocResponse;
import MediChecker.MediChecker.dto.response.ThuocResponse;
import MediChecker.MediChecker.entity.*;
import MediChecker.MediChecker.enumer.TrangThaiDonThuoc;
import MediChecker.MediChecker.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DonThuocService {
    
    private final DonThuocRepository donThuocRepository;
    private final BenhNhanRepository benhNhanRepository;
    private final ThuocRepository thuocRepository;
    private final ChiTietDonThuocRepository chiTietDonThuocRepository;
    private final DieuTriRepository dieuTriRepository;
    
    public Page<DonThuocResponse> getDonThuocDieuTri(Pageable pageable, Long benhNhanId) {
        Page<DonThuoc> donThuocPage;
        
        if (benhNhanId != null) {
            donThuocPage = donThuocRepository.findByBenhNhanId(benhNhanId, pageable);
        } else {
            donThuocPage = donThuocRepository.findAll(pageable);
        }
        
        return donThuocPage.map(this::convertToResponse);
    }
    
    public DonThuocResponse getChiTietDonThuoc(Long id) {
        DonThuoc donThuoc = donThuocRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn thuốc với ID: " + id));
        return convertToResponse(donThuoc);
    }
    
    public DonThuocResponse taoDonThuoc(DonThuocRequest request) {
        BenhNhan benhNhan = benhNhanRepository.findById(request.getBenhNhanId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bệnh nhân với ID: " + request.getBenhNhanId()));
        
        DonThuoc donThuoc = new DonThuoc();
        donThuoc.setMaDonThuoc(generateMaDonThuoc());
        donThuoc.setBenhNhan(benhNhan);
        donThuoc.setBacSiKeDon(request.getBacSiKeDon());
        donThuoc.setGhiChu(request.getGhiChu());
        donThuoc.setTrangThai(TrangThaiDonThuoc.MOI_TAO);
        
        if (request.getDieuTriId() != null) {
            DieuTri dieuTri = dieuTriRepository.findById(request.getDieuTriId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy điều trị với ID: " + request.getDieuTriId()));
            donThuoc.setDieuTri(dieuTri);
        }
        
        DonThuoc savedDonThuoc = donThuocRepository.save(donThuoc);
        return convertToResponse(savedDonThuoc);
    }
    
    public DonThuocResponse capNhatDonThuoc(Long id, DonThuocRequest request) {
        DonThuoc donThuoc = donThuocRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn thuốc với ID: " + id));
        
        donThuoc.setBacSiKeDon(request.getBacSiKeDon());
        donThuoc.setGhiChu(request.getGhiChu());
        
        DonThuoc savedDonThuoc = donThuocRepository.save(donThuoc);
        return convertToResponse(savedDonThuoc);
    }
    
    public void xoaDonThuoc(Long id) {
        DonThuoc donThuoc = donThuocRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn thuốc với ID: " + id));
        
        donThuoc.setTrangThai(TrangThaiDonThuoc.HUY_BO);
        donThuocRepository.save(donThuoc);
    }
    
    public DonThuocResponse themThuocVaoDon(Long donThuocId, ChiTietDonThuocRequest request) {
        DonThuoc donThuoc = donThuocRepository.findById(donThuocId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn thuốc với ID: " + donThuocId));
        
        Thuoc thuoc = thuocRepository.findById(request.getThuocId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thuốc với ID: " + request.getThuocId()));
        
        // Kiểm tra thuốc đã có trong đơn chưa
        boolean exists = chiTietDonThuocRepository.existsByDonThuocIdAndThuocId(donThuocId, request.getThuocId());
        if (exists) {
            throw new RuntimeException("Thuốc đã có trong đơn thuốc này");
        }
        
        ChiTietDonThuoc chiTiet = new ChiTietDonThuoc();
        chiTiet.setDonThuoc(donThuoc);
        chiTiet.setThuoc(thuoc);
        updateChiTietFromRequest(chiTiet, request);
        chiTiet.setGiaDonVi(thuoc.getGiaBan());
        chiTiet.setThanhTien(thuoc.getGiaBan().multiply(BigDecimal.valueOf(request.getSoLuong())));
        
        chiTietDonThuocRepository.save(chiTiet);
        return convertToResponse(donThuoc);
    }
    
    public DonThuocResponse xoaThuocKhoiDon(Long donThuocId, Long thuocId) {
        DonThuoc donThuoc = donThuocRepository.findById(donThuocId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn thuốc với ID: " + donThuocId));
        
        ChiTietDonThuoc chiTiet = chiTietDonThuocRepository.findByDonThuocIdAndThuocId(donThuocId, thuocId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thuốc trong đơn"));
        
        chiTietDonThuocRepository.delete(chiTiet);
        return convertToResponse(donThuoc);
    }
    
    public DonThuocResponse capNhatThuocTrongDon(Long donThuocId, Long thuocId, ChiTietDonThuocRequest request) {
        DonThuoc donThuoc = donThuocRepository.findById(donThuocId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn thuốc với ID: " + donThuocId));
        
        ChiTietDonThuoc chiTiet = chiTietDonThuocRepository.findByDonThuocIdAndThuocId(donThuocId, thuocId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thuốc trong đơn"));
        
        updateChiTietFromRequest(chiTiet, request);
        chiTiet.setThanhTien(chiTiet.getGiaDonVi().multiply(BigDecimal.valueOf(request.getSoLuong())));
        
        chiTietDonThuocRepository.save(chiTiet);
        return convertToResponse(donThuoc);
    }
    
    private void updateChiTietFromRequest(ChiTietDonThuoc chiTiet, ChiTietDonThuocRequest request) {
        chiTiet.setSoLuong(request.getSoLuong());
        chiTiet.setLieuDung(request.getLieuDung());
        chiTiet.setDuongDung(request.getDuongDung());
        chiTiet.setTanSuat(request.getTanSuat());
        chiTiet.setThoiGianDung(request.getThoiGianDung());
        chiTiet.setHuongDanSuDung(request.getHuongDanSuDung());
    }
    
    private DonThuocResponse convertToResponse(DonThuoc donThuoc) {
        DonThuocResponse response = new DonThuocResponse();
        response.setId(donThuoc.getId());
        response.setMaDonThuoc(donThuoc.getMaDonThuoc());
        response.setBacSiKeDon(donThuoc.getBacSiKeDon());
        response.setGhiChu(donThuoc.getGhiChu());
        response.setTrangThai(donThuoc.getTrangThai());
        response.setNgayKeDon(donThuoc.getNgayKeDon());
        
        // Convert BenhNhan
        BenhNhanResponse benhNhanResponse = new BenhNhanResponse();
        benhNhanResponse.setId(donThuoc.getBenhNhan().getId());
        benhNhanResponse.setMaBenhNhan(donThuoc.getBenhNhan().getMaBenhNhan());
        benhNhanResponse.setHoTen(donThuoc.getBenhNhan().getHoTen());
        response.setBenhNhan(benhNhanResponse);
        
        // Convert ChiTietDonThuoc
        if (donThuoc.getDanhSachThuoc() != null) {
            List<ChiTietDonThuocResponse> chiTietList = donThuoc.getDanhSachThuoc().stream()
                    .map(this::convertChiTietToResponse)
                    .collect(Collectors.toList());
            response.setDanhSachThuoc(chiTietList);
        }
        
        return response;
    }
    
    private ChiTietDonThuocResponse convertChiTietToResponse(ChiTietDonThuoc chiTiet) {
        ChiTietDonThuocResponse response = new ChiTietDonThuocResponse();
        response.setId(chiTiet.getId());
        response.setSoLuong(chiTiet.getSoLuong());
        response.setLieuDung(chiTiet.getLieuDung());
        response.setDuongDung(chiTiet.getDuongDung());
        response.setTanSuat(chiTiet.getTanSuat());
        response.setThoiGianDung(chiTiet.getThoiGianDung());
        response.setHuongDanSuDung(chiTiet.getHuongDanSuDung());
        response.setGiaDonVi(chiTiet.getGiaDonVi());
        response.setThanhTien(chiTiet.getThanhTien());
        
        // Convert Thuoc
        ThuocResponse thuocResponse = new ThuocResponse();
        thuocResponse.setId(chiTiet.getThuoc().getId());
        thuocResponse.setMaThuoc(chiTiet.getThuoc().getMaThuoc());
        thuocResponse.setTenThuoc(chiTiet.getThuoc().getTenThuoc());
        thuocResponse.setTenHoatChat(chiTiet.getThuoc().getTenHoatChat());
        thuocResponse.setNongDo(chiTiet.getThuoc().getNongDo());
        thuocResponse.setDangBaoChe(chiTiet.getThuoc().getDangBaoChe());
        thuocResponse.setDonViTinh(chiTiet.getThuoc().getDonViTinh());
        response.setThuoc(thuocResponse);
        
        return response;
    }
    
    private String generateMaDonThuoc() {
        String prefix = "DT";
        String timestamp = String.valueOf(System.currentTimeMillis()).substring(8);
        String random = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        return prefix + timestamp + random;
    }
}
