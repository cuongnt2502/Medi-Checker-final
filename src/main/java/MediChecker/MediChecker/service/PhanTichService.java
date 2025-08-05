package MediChecker.MediChecker.service;

import MediChecker.MediChecker.dto.response.PhanTichResponse;
import MediChecker.MediChecker.dto.response.ThuocResponse;
import MediChecker.MediChecker.entity.*;
import MediChecker.MediChecker.repository.BenhLyNenRepository;
import MediChecker.MediChecker.repository.BenhNhanRepository;
import MediChecker.MediChecker.repository.DiUngThuocRepository;
import MediChecker.MediChecker.repository.DonThuocRepository;
import MediChecker.MediChecker.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PhanTichService {
    
    private final BenhNhanRepository benhNhanRepository;
    private final DonThuocRepository donThuocRepository;
    private final BenhLyNenRepository benhLyNenRepository;
    private final DiUngThuocRepository diUngThuocRepository;
    
    public PhanTichResponse phanTichChongChiDinh(Long patientId, Long prescriptionId) {
        BenhNhan benhNhan = benhNhanRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bệnh nhân"));
        
        DonThuoc donThuoc = donThuocRepository.findById(prescriptionId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn thuốc"));
        
        List<BenhLyNen> benhLyNenList = benhLyNenRepository.findByBenhNhanId(patientId);
        List<DiUngThuoc> diUngList = diUngThuocRepository.findByBenhNhanId(patientId);
        
        PhanTichResponse response = new PhanTichResponse();
        response.setChongChiDinh(new ArrayList<>());
        
        // Phân tích chống chỉ định theo bệnh lý nền
        for (ChiTietDonThuoc chiTiet : donThuoc.getDanhSachThuoc()) {
            Thuoc thuoc = chiTiet.getThuoc();
            
            // Kiểm tra chống chỉ định với bệnh lý nền
            for (BenhLyNen benhLy : benhLyNenList) {
                if (kiemTraChongChiDinh(thuoc, benhLy)) {
                    PhanTichResponse.ChongChiDinhWarning warning = new PhanTichResponse.ChongChiDinhWarning();
                    warning.setThuoc(convertThuocToResponse(thuoc));
                    warning.setBenhLyNen(benhLy.getTenBenh());
                    warning.setMucDoNguyHiem(benhLy.getMucDoNghiemTrong().toString());
                    warning.setLyDo("Thuốc chống chỉ định với bệnh lý nền: " + benhLy.getTenBenh());
                    response.getChongChiDinh().add(warning);
                }
            }
            
            // Kiểm tra dị ứng thuốc
            for (DiUngThuoc diUng : diUngList) {
                if (diUng.getThuoc().getId().equals(thuoc.getId())) {
                    PhanTichResponse.ChongChiDinhWarning warning = new PhanTichResponse.ChongChiDinhWarning();
                    warning.setThuoc(convertThuocToResponse(thuoc));
                    warning.setBenhLyNen("Dị ứng thuốc");
                    warning.setMucDoNguyHiem(diUng.getMucDoNghiemTrong().toString());
                    warning.setLyDo("Bệnh nhân có tiền sử dị ứng với thuốc này: " + diUng.getTrieuChung());
                    response.getChongChiDinh().add(warning);
                }
            }
        }
        
        return response;
    }
    
    public PhanTichResponse phanTichTuongTac(Long prescriptionId) {
        DonThuoc donThuoc = donThuocRepository.findById(prescriptionId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn thuốc"));
        
        PhanTichResponse response = new PhanTichResponse();
        response.setTuongTac(new ArrayList<>());
        
        List<ChiTietDonThuoc> danhSachThuoc = donThuoc.getDanhSachThuoc();
        
        // Kiểm tra tương tác giữa các cặp thuốc
        for (int i = 0; i < danhSachThuoc.size(); i++) {
            for (int j = i + 1; j < danhSachThuoc.size(); j++) {
                Thuoc thuoc1 = danhSachThuoc.get(i).getThuoc();
                Thuoc thuoc2 = danhSachThuoc.get(j).getThuoc();
                
                String tuongTac = kiemTraTuongTacThuoc(thuoc1, thuoc2);
                if (tuongTac != null) {
                    PhanTichResponse.TuongTacWarning warning = new PhanTichResponse.TuongTacWarning();
                    warning.setThuoc1(convertThuocToResponse(thuoc1));
                    warning.setThuoc2(convertThuocToResponse(thuoc2));
                    warning.setMucDoTuongTac("TRUNG BÌNH");
                    warning.setMoTa(tuongTac);
                    warning.setKhuyenNghi("Theo dõi chặt chẽ tác dụng phụ và điều chỉnh liều nếu cần");
                    response.getTuongTac().add(warning);
                }
            }
        }
        
        return response;
    }
    
    public PhanTichResponse phanTichTrungChiDinh(Long prescriptionId) {
        DonThuoc donThuoc = donThuocRepository.findById(prescriptionId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn thuốc"));
        
        PhanTichResponse response = new PhanTichResponse();
        response.setTrungChiDinh(new ArrayList<>());
        
        // Nhóm thuốc theo tác dụng
        Map<Thuoc.NhomThuoc, List<Thuoc>> nhomThuocMap = donThuoc.getDanhSachThuoc().stream()
                .map(ChiTietDonThuoc::getThuoc)
                .collect(Collectors.groupingBy(Thuoc::getNhomThuoc));
        
        // Kiểm tra trùng lặp trong cùng nhóm
        for (Map.Entry<Thuoc.NhomThuoc, List<Thuoc>> entry : nhomThuocMap.entrySet()) {
            if (entry.getValue().size() > 1) {
                PhanTichResponse.TrungChiDinhWarning warning = new PhanTichResponse.TrungChiDinhWarning();
                warning.setDanhSachThuoc(entry.getValue().stream()
                        .map(this::convertThuocToResponse)
                        .collect(Collectors.toList()));
                warning.setTacDungTrung("Cùng nhóm tác dụng: " + entry.getKey().toString());
                warning.setKhuyenNghi("Xem xét giảm số lượng thuốc cùng tác dụng để tránh dư thừa");
                response.getTrungChiDinh().add(warning);
            }
        }
        
        return response;
    }
    
    public PhanTichResponse phanTichKhongHopLy(String diagnosisCode, Long prescriptionId) {
        DonThuoc donThuoc = donThuocRepository.findById(prescriptionId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn thuốc"));
        
        PhanTichResponse response = new PhanTichResponse();
        response.setKhongHopLy(new ArrayList<>());
        
        // Kiểm tra từng thuốc với chẩn đoán
        for (ChiTietDonThuoc chiTiet : donThuoc.getDanhSachThuoc()) {
            Thuoc thuoc = chiTiet.getThuoc();
            
            if (!kiemTraHopLyVoiChanDoan(thuoc, diagnosisCode)) {
                PhanTichResponse.KhongHopLyWarning warning = new PhanTichResponse.KhongHopLyWarning();
                warning.setThuoc(convertThuocToResponse(thuoc));
                warning.setChanDoan(diagnosisCode);
                warning.setLyDo("Thuốc không phù hợp với chẩn đoán hiện tại");
                warning.setKhuyenNghi("Xem xét thay thế bằng thuốc phù hợp hơn với chẩn đoán");
                response.getKhongHopLy().add(warning);
            }
        }
        
        return response;
    }
    
    private boolean kiemTraChongChiDinh(Thuoc thuoc, BenhLyNen benhLy) {
        // Logic kiểm tra chống chỉ định dựa trên bệnh lý nền
        String chongChiDinh = thuoc.getChongChiDinh();
        if (chongChiDinh != null && !chongChiDinh.isEmpty()) {
            return chongChiDinh.toLowerCase().contains(benhLy.getTenBenh().toLowerCase()) ||
                   chongChiDinh.toLowerCase().contains(benhLy.getMaBenh().toLowerCase());
        }
        return false;
    }
    
    private String kiemTraTuongTacThuoc(Thuoc thuoc1, Thuoc thuoc2) {
        // Logic kiểm tra tương tác thuốc (đơn giản hóa)
        // Trong thực tế, cần database tương tác thuốc chuyên biệt
        
        // Ví dụ: Warfarin + Aspirin
        if ((thuoc1.getTenHoatChat() != null && thuoc1.getTenHoatChat().toLowerCase().contains("warfarin") &&
             thuoc2.getTenHoatChat() != null && thuoc2.getTenHoatChat().toLowerCase().contains("aspirin")) ||
            (thuoc2.getTenHoatChat() != null && thuoc2.getTenHoatChat().toLowerCase().contains("warfarin") &&
             thuoc1.getTenHoatChat() != null && thuoc1.getTenHoatChat().toLowerCase().contains("aspirin"))) {
            return "Tăng nguy cơ chảy máu khi dùng đồng thời Warfarin và Aspirin";
        }
        
        // Kiểm tra cùng nhóm thuốc có thể gây tương tác
        if (thuoc1.getNhomThuoc() == thuoc2.getNhomThuoc() && 
            (thuoc1.getNhomThuoc() == Thuoc.NhomThuoc.KHANG_SINH || 
             thuoc1.getNhomThuoc() == Thuoc.NhomThuoc.TIM_MACH)) {
            return "Có thể có tương tác khi dùng đồng thời các thuốc cùng nhóm";
        }
        
        return null;
    }
    
    private boolean kiemTraHopLyVoiChanDoan(Thuoc thuoc, String diagnosisCode) {
        // Logic kiểm tra tính hợp lý của thuốc với chẩn đoán
        String chiDinh = thuoc.getChiDinh();
        if (chiDinh != null && !chiDinh.isEmpty()) {
            // Đơn giản hóa: kiểm tra mã chẩn đoán có trong chỉ định không
            return chiDinh.toLowerCase().contains(diagnosisCode.toLowerCase());
        }
        return true; // Mặc định là hợp lý nếu không có thông tin chỉ định
    }
    
    private ThuocResponse convertThuocToResponse(Thuoc thuoc) {
        ThuocResponse response = new ThuocResponse();
        response.setId(thuoc.getId());
        response.setMaThuoc(thuoc.getMaThuoc());
        response.setTenThuoc(thuoc.getTenThuoc());
        response.setTenHoatChat(thuoc.getTenHoatChat());
        response.setNongDo(thuoc.getNongDo());
        response.setDangBaoChe(thuoc.getDangBaoChe());
        response.setNhomThuoc(thuoc.getNhomThuoc());
        return response;
    }
}
