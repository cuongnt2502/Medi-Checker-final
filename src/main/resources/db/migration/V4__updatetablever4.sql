-- Thêm dữ liệu mẫu cho bệnh nhân
INSERT INTO benh_nhan (ma_benh_nhan, ho_ten, ngay_sinh, gioi_tinh, so_dien_thoai, email, dia_chi, so_bao_hiem) VALUES
                                                                                                                   ('BN001', 'Nguyễn Văn An', '1985-03-15', 'NAM', '0901234567', 'nguyenvanan@email.com', '123 Đường ABC, Quận 1, TP.HCM', 'DN1234567890'),
                                                                                                                   ('BN002', 'Trần Thị Bình', '1990-07-22', 'NU', '0912345678', 'tranthibinh@email.com', '456 Đường DEF, Quận 2, TP.HCM', 'DN2345678901'),
                                                                                                                   ('BN003', 'Lê Văn Cường', '1978-12-10', 'NAM', '0923456789', 'levancuong@email.com', '789 Đường GHI, Quận 3, TP.HCM', 'DN3456789012');

-- Thêm dữ liệu mẫu cho thuốc
INSERT INTO thuoc (ma_thuoc, ten_thuoc, ten_hoat_chat, nong_do, dang_bao_che, hang_san_xuat, nuoc_san_xuat, gia_ban, don_vi_tinh, chi_dinh, chong_chi_dinh, nhom_thuoc) VALUES
                                                                                                                                                                            ('T001', 'Paracetamol 500mg', 'Paracetamol', '500mg', 'Viên nén', 'Công ty Dược ABC', 'Việt Nam', 5000.00, 'Viên', 'Giảm đau, hạ sốt', 'Suy gan nặng', 'GIAM_DAU'),
                                                                                                                                                                            ('T002', 'Amoxicillin 500mg', 'Amoxicillin', '500mg', 'Viên nang', 'Công ty Dược DEF', 'Việt Nam', 8000.00, 'Viên', 'Nhiễm khuẩn đường hô hấp', 'Dị ứng penicillin', 'KHANG_SINH'),
                                                                                                                                                                            ('T003', 'Aspirin 100mg', 'Aspirin', '100mg', 'Viên nén', 'Công ty Dược GHI', 'Việt Nam', 3000.00, 'Viên', 'Phòng ngừa đột quỵ, nhồi máu cơ tim', 'Loét dạ dày', 'TIM_MACH'),
                                                                                                                                                                            ('T004', 'Omeprazole 20mg', 'Omeprazole', '20mg', 'Viên nang', 'Công ty Dược JKL', 'Việt Nam', 12000.00, 'Viên', 'Loét dạ dày, trào ngược dạ dày', 'Dị ứng omeprazole', 'TIEU_HOA');

-- Thêm dữ liệu mẫu cho bệnh lý nền
INSERT INTO benh_ly_nen (benh_nhan_id, ma_benh, ten_benh, mo_ta, muc_do_nghiem_trong) VALUES
                                                                                          (1, 'I10', 'Tăng huyết áp', 'Tăng huyết áp nguyên phát', 'VUA'),
                                                                                          (1, 'E11', 'Đái tháo đường type 2', 'Đái tháo đường không phụ thuộc insulin', 'VUA'),
                                                                                          (2, 'K25', 'Loét dạ dày', 'Loét dạ dày mãn tính', 'NHE'),
                                                                                          (3, 'J44', 'COPD', 'Bệnh phổi tắc nghẽn mãn tính', 'NANG');

-- Thêm dữ liệu mẫu cho dị ứng thuốc
INSERT INTO di_ung_thuoc (benh_nhan_id, thuoc_id, trieu_chung, muc_do_nghiem_trong) VALUES
                                                                                        (2, 2, 'Phát ban, ngứa', 'VUA'),
                                                                                        (3, 3, 'Buồn nôn, chóng mặt', 'NHE');

-- Thêm dữ liệu mẫu cho điều trị
INSERT INTO dieu_tri (benh_nhan_id, ma_chan_doan, chan_doan_chinh, chan_doan_phu, trieu_chung, bac_si_dieu_tri, trang_thai) VALUES
                                                                                                                                (1, 'I10', 'Tăng huyết áp nguyên phát', 'Đái tháo đường type 2', 'Đau đầu, chóng mặt', 'BS. Nguyễn Văn A', 'DANG_DIEU_TRI'),
                                                                                                                                (2, 'K25', 'Loét dạ dày', '', 'Đau thượng vị, ợ nóng', 'BS. Trần Thị B', 'DANG_DIEU_TRI'),
                                                                                                                                (3, 'J44', 'COPD', '', 'Khó thở, ho có đờm', 'BS. Lê Văn C', 'DANG_DIEU_TRI');

-- Thêm dữ liệu mẫu cho đơn thuốc
INSERT INTO don_thuoc (ma_don_thuoc, benh_nhan_id, dieu_tri_id, bac_si_ke_don, ghi_chu, trang_thai) VALUES
                                                                                                        ('DT001', 1, 1, 'BS. Nguyễn Văn A', 'Uống sau ăn', 'DA_DUYET'),
                                                                                                        ('DT002', 2, 2, 'BS. Trần Thị B', 'Uống trước ăn 30 phút', 'DA_DUYET'),
                                                                                                        ('DT003', 3, 3, 'BS. Lê Văn C', 'Theo dõi chặt chẽ', 'MOI_TAO');

-- Thêm dữ liệu mẫu cho chi tiết đơn thuốc
INSERT INTO chi_tiet_don_thuoc (don_thuoc_id, thuoc_id, so_luong, lieu_dung, duong_dung, tan_suat, thoi_gian_dung, huong_dan_su_dung, gia_don_vi, thanh_tien) VALUES
                                                                                                                                                                  (1, 1, 30, '1 viên/lần', 'Uống', '2 lần/ngày', '15 ngày', 'Uống sau ăn 30 phút', 5000.00, 150000.00),
                                                                                                                                                                  (1, 3, 30, '1 viên/lần', 'Uống', '1 lần/ngày', '30 ngày', 'Uống vào buổi sáng', 3000.00, 90000.00),
                                                                                                                                                                  (2, 4, 28, '1 viên/lần', 'Uống', '2 lần/ngày', '14 ngày', 'Uống trước ăn 30 phút', 12000.00, 336000.00),
                                                                                                                                                                  (3, 2, 21, '1 viên/lần', 'Uống', '3 lần/ngày', '7 ngày', 'Uống sau ăn', 8000.00, 168000.00);
