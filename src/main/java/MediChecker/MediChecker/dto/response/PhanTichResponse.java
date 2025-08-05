package MediChecker.MediChecker.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Kết quả phân tích đơn thuốc")
public class PhanTichResponse {
    
    @Schema(description = "Danh sách cảnh báo chống chỉ định")
    private List<ChongChiDinhWarning> chongChiDinh;
    
    @Schema(description = "Danh sách cảnh báo tương tác thuốc")
    private List<TuongTacWarning> tuongTac;
    
    @Schema(description = "Danh sách cảnh báo trùng chỉ định")
    private List<TrungChiDinhWarning> trungChiDinh;
    
    @Schema(description = "Danh sách thuốc không hợp lý")
    private List<KhongHopLyWarning> khongHopLy;
    
    @Data
    @Schema(description = "Cảnh báo chống chỉ định")
    public static class ChongChiDinhWarning {
        @Schema(description = "Thông tin thuốc")
        private ThuocResponse thuoc;
        
        @Schema(description = "Bệnh lý nền gây chống chỉ định")
        private String benhLyNen;
        
        @Schema(description = "Mức độ nguy hiểm")
        private String mucDoNguyHiem;
        
        @Schema(description = "Lý do chống chỉ định")
        private String lyDo;
    }
    
    @Data
    @Schema(description = "Cảnh báo tương tác thuốc")
    public static class TuongTacWarning {
        @Schema(description = "Thuốc thứ nhất")
        private ThuocResponse thuoc1;
        
        @Schema(description = "Thuốc thứ hai")
        private ThuocResponse thuoc2;
        
        @Schema(description = "Mức độ tương tác")
        private String mucDoTuongTac;
        
        @Schema(description = "Mô tả tương tác")
        private String moTa;
        
        @Schema(description = "Khuyến nghị")
        private String khuyenNghi;
    }
    
    @Data
    @Schema(description = "Cảnh báo trùng chỉ định")
    public static class TrungChiDinhWarning {
        @Schema(description = "Danh sách thuốc trùng lặp")
        private List<ThuocResponse> danhSachThuoc;
        
        @Schema(description = "Tác dụng trùng lặp")
        private String tacDungTrung;
        
        @Schema(description = "Khuyến nghị")
        private String khuyenNghi;
    }
    
    @Data
    @Schema(description = "Cảnh báo không hợp lý")
    public static class KhongHopLyWarning {
        @Schema(description = "Thông tin thuốc")
        private ThuocResponse thuoc;
        
        @Schema(description = "Chẩn đoán")
        private String chanDoan;
        
        @Schema(description = "Lý do không hợp lý")
        private String lyDo;
        
        @Schema(description = "Khuyến nghị")
        private String khuyenNghi;
    }
}
