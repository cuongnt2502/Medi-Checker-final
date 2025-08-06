package MediChecker.MediChecker.service;

import MediChecker.MediChecker.dto.request.BenhNhanRequest;
import MediChecker.MediChecker.dto.response.*;
import MediChecker.MediChecker.entity.*;
import MediChecker.MediChecker.repository.BenhNhanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    
    public ChiTietBenhNhanResponse getChiTietBenhNhan(Long id) {
        BenhNhan benhNhan = benhNhanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bệnh nhân với ID: " + id));
        return convertToResponseDetail(benhNhan);
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
    private ChiTietBenhNhanResponse convertToResponseDetail(BenhNhan benhNhan) {
        ChiTietBenhNhanResponse response = new ChiTietBenhNhanResponse();
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
        response.setDanhSachBenhLyNen(convertToResponseBenhlyNen(benhNhan.getDanhSachBenhLyNen()));
        response.setDanhSachDiUng(convertToResponseDiUngThuocR(benhNhan.getDanhSachDiUng()));
        response.setDanhSachDieuTri(convertToResponseDieuTri(benhNhan.getDanhSachDieuTri()));
        return response;
    }
    private List<DieuTriResponse>  convertToResponseDieuTri(List<DieuTri> dieuTris) {
        List<DieuTriResponse> responses = new ArrayList<>();
        for (DieuTri dieuTri : dieuTris) {
            DieuTriResponse response = new DieuTriResponse();
            response.setId(dieuTri.getId());
            response.setDonThuocDieuTri(convertToResponseDonThuoc(dieuTri.getDonThuocDieuTri()));
            response.setTrieuChung(dieuTri.getTrieuChung());
            response.setBacSiDieuTri(dieuTri.getBacSiDieuTri());
            response.setChanDoanChinh(dieuTri.getChanDoanChinh());
            response.setChanDoanPhu(dieuTri.getChanDoanPhu());
            response.setTrangThai(dieuTri.getTrangThai());
            responses.add(response);
        }
        return responses;
    }
    private DonThuocResponse convertToResponseDonThuoc(DonThuoc donThuoc) {
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
        return response;
    }
    private List<DiUngThuocRespone>  convertToResponseDiUngThuocR(List<DiUngThuoc> diUngThuocs) {
        List<DiUngThuocRespone> responses = new ArrayList<>();
        for (DiUngThuoc diUngThuoc : diUngThuocs) {
            DiUngThuocRespone response = new DiUngThuocRespone();
            response.setId(diUngThuoc.getId());
            response.setThuoc(convertToResponseThuoc(diUngThuoc.getThuoc()));
            response.setTrieuChung(diUngThuoc.getTrieuChung());
            response.setMucDoNghiemTrong(diUngThuoc.getMucDoNghiemTrong());
            response.setNgayPhatHien(diUngThuoc.getNgayPhatHien());
            responses.add(response);
        }
        return responses;
    }
    private ThuocResponse  convertToResponseThuoc(Thuoc thuoc) {
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
    private List<BenhLyNenRespone> convertToResponseBenhlyNen(List<BenhLyNen> benhLyNens) {
        List<BenhLyNenRespone> responses = new ArrayList<>();
        for (BenhLyNen benhLyNen : benhLyNens) {
            BenhLyNenRespone response = new BenhLyNenRespone();
            response.setId(benhLyNen.getId());
            response.setTenBenh(benhLyNen.getTenBenh());
            response.setMaBenh(benhLyNen.getMaBenh());
            response.setMoTa(benhLyNen.getMoTa());
            response.setNgayChanDoan(benhLyNen.getNgayChanDoan());
            response.setMucDoNghiemTrong(benhLyNen.getMucDoNghiemTrong());
            responses.add(response);
        }

        return responses;
    }

    private String generateMaBenhNhan() {
        String prefix = "BN";
        String timestamp = String.valueOf(System.currentTimeMillis()).substring(8);
        String random = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        return prefix + timestamp + random;
    }
}
