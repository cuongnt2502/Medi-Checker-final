CREATE INDEX idx_benh_nhan_ma ON benh_nhan(ma_benh_nhan);
CREATE INDEX idx_benh_nhan_ho_ten ON benh_nhan(ho_ten);
CREATE INDEX idx_thuoc_ma ON thuoc(ma_thuoc);
CREATE INDEX idx_thuoc_ten ON thuoc(ten_thuoc);
CREATE INDEX idx_don_thuoc_ma ON don_thuoc(ma_don_thuoc);
CREATE INDEX idx_don_thuoc_benh_nhan ON don_thuoc(benh_nhan_id);
CREATE INDEX idx_benh_ly_nen_benh_nhan ON benh_ly_nen(benh_nhan_id);
CREATE INDEX idx_di_ung_thuoc_benh_nhan ON di_ung_thuoc(benh_nhan_id);
