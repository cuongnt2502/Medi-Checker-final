CREATE TABLE lich_su_dieu_tri (
                                  id SERIAL PRIMARY KEY,
                                  ngay_dieu_tri DATE NOT NULL,
                                  chuan_doan TEXT NOT NULL,
                                  bac_si TEXT NOT NULL,
                                  ghi_chu TEXT NOT NULL,
                                  trang_thai TEXT NOT NULL
);

CREATE TABLE don_thuoc_item (
                                id SERIAL PRIMARY KEY,
                                ten_thuoc VARCHAR(200) NOT NULL,
                                lieu_luong VARCHAR(20) NOT NULL,
                                lich_su_dieu_tri_id INTEGER REFERENCES lich_su_dieu_tri(id) ON DELETE CASCADE
);
INSERT INTO lich_su_dieu_tri (ngay_dieu_tri, chuan_doan, bac_si, ghi_chu, trang_thai)
VALUES
    ('2025-08-01', 'Viêm họng cấp', 'BS. Nguyễn Văn A', 'Cần nghỉ ngơi, uống nhiều nước', 'Đã điều trị'),
    ('2025-08-03', 'Cảm cúm', 'BS. Trần Thị B', 'Không sốt cao, theo dõi tại nhà', 'Đang theo dõi'),
    ('2025-08-05', 'Đau dạ dày', 'BS. Lê Văn C', 'Ăn uống đúng giờ, tránh stress', 'Đã điều trị');

INSERT INTO don_thuoc_item (ten_thuoc, lieu_luong, lich_su_dieu_tri_id)
VALUES
    ('Paracetamol', '500mg x 3 lần/ngày', 1),
    ('Nước muối sinh lý', '2 lần/ngày', 1);

-- Đơn thuốc cho bệnh nhân cảm cúm (id = 2)
INSERT INTO don_thuoc_item (ten_thuoc, lieu_luong, lich_su_dieu_tri_id)
VALUES
    ('Vitamin C', '1000mg/ngày', 2),
    ('Panadol Cold & Flu', '1 viên x 2 lần/ngày', 2);

-- Đơn thuốc cho bệnh nhân đau dạ dày (id = 3)
INSERT INTO don_thuoc_item (ten_thuoc, lieu_luong, lich_su_dieu_tri_id)
VALUES
    ('Omeprazole', '20mg x sáng trước ăn', 3),
    ('Antacid', '2 viên sau ăn', 3);