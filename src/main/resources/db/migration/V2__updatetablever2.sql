CREATE TABLE IF NOT EXISTS benh_nhan (
                                         id BIGSERIAL PRIMARY KEY,
                                         ma_benh_nhan VARCHAR(20) UNIQUE NOT NULL,
    ho_ten VARCHAR(100) NOT NULL,
    ngay_sinh DATE,
    gioi_tinh VARCHAR(10) CHECK (gioi_tinh IN ('NAM', 'NU', 'KHAC')),
    so_dien_thoai VARCHAR(15),
    email VARCHAR(100),
    dia_chi VARCHAR(500),
    so_bao_hiem VARCHAR(20),
    ngay_tao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ngay_cap_nhat TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS thuoc (
                                     id BIGSERIAL PRIMARY KEY,
                                     ma_thuoc VARCHAR(20) UNIQUE NOT NULL,
    ten_thuoc VARCHAR(200) NOT NULL,
    ten_hoat_chat VARCHAR(200),
    nong_do VARCHAR(50),
    dang_bao_che VARCHAR(100),
    hang_san_xuat VARCHAR(100),
    nuoc_san_xuat VARCHAR(50),
    gia_ban DECIMAL(10,2),
    don_vi_tinh VARCHAR(20),
    chi_dinh TEXT,
    chong_chi_dinh TEXT,
    tac_dung_phu TEXT,
    lieu_dung_nguoi_lon VARCHAR(500),
    lieu_dung_tre_em VARCHAR(500),
    nhom_thuoc VARCHAR(20) CHECK (nhom_thuoc IN ('KHANG_SINH', 'GIAM_DAU', 'CHONG_VIEM', 'TIM_MACH', 'TIEU_HOA', 'HOI_SUC', 'KHAC')),
    kich_hoat BOOLEAN DEFAULT TRUE,
    ngay_tao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ngay_cap_nhat TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS benh_ly_nen (
                                           id BIGSERIAL PRIMARY KEY,
                                           benh_nhan_id BIGINT NOT NULL REFERENCES benh_nhan(id) ON DELETE CASCADE,
    ma_benh VARCHAR(10) NOT NULL,
    ten_benh VARCHAR(200) NOT NULL,
    mo_ta VARCHAR(500),
    muc_do_nghiem_trong VARCHAR(20) CHECK (muc_do_nghiem_trong IN ('NHE', 'VUA', 'NANG', 'RAT_NANG')),
    ngay_chan_doan TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS di_ung_thuoc (
                                            id BIGSERIAL PRIMARY KEY,
                                            benh_nhan_id BIGINT NOT NULL REFERENCES benh_nhan(id) ON DELETE CASCADE,
    thuoc_id BIGINT NOT NULL REFERENCES thuoc(id) ON DELETE CASCADE,
    trieu_chung VARCHAR(500),
    muc_do_nghiem_trong VARCHAR(20) CHECK (muc_do_nghiem_trong IN ('NHE', 'VUA', 'NANG', 'NGUY_HIEM')),
    ngay_phat_hien TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS dieu_tri (
                                        id BIGSERIAL PRIMARY KEY,
                                        benh_nhan_id BIGINT NOT NULL REFERENCES benh_nhan(id) ON DELETE CASCADE,
    ma_chan_doan VARCHAR(10),
    chan_doan_chinh VARCHAR(500) NOT NULL,
    chan_doan_phu VARCHAR(500),
    trieu_chung TEXT,
    bac_si_dieu_tri VARCHAR(100),
    trang_thai VARCHAR(20) CHECK (trang_thai IN ('DANG_DIEU_TRI', 'HOAN_THANH', 'TAM_DUNG', 'HUY_BO')),
    ngay_bat_dau TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ngay_ket_thuc TIMESTAMP,
    ngay_cap_nhat TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS don_thuoc (
                                         id BIGSERIAL PRIMARY KEY,
                                         ma_don_thuoc VARCHAR(20) UNIQUE NOT NULL,
    benh_nhan_id BIGINT NOT NULL REFERENCES benh_nhan(id) ON DELETE CASCADE,
    dieu_tri_id BIGINT REFERENCES dieu_tri(id) ON DELETE SET NULL,
    bac_si_ke_don VARCHAR(100) NOT NULL,
    ghi_chu TEXT,
    trang_thai VARCHAR(20) CHECK (trang_thai IN ('MOI_TAO', 'DA_DUYET', 'DANG_THUC_HIEN', 'HOAN_THANH', 'HUY_BO')),
    ngay_ke_don TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ngay_cap_nhat TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS chi_tiet_don_thuoc (
                                                  id BIGSERIAL PRIMARY KEY,
                                                  don_thuoc_id BIGINT NOT NULL REFERENCES don_thuoc(id) ON DELETE CASCADE,
    thuoc_id BIGINT NOT NULL REFERENCES thuoc(id) ON DELETE CASCADE,
    so_luong INTEGER NOT NULL,
    lieu_dung VARCHAR(200) NOT NULL,
    duong_dung VARCHAR(100),
    tan_suat VARCHAR(100),
    thoi_gian_dung VARCHAR(100),
    huong_dan_su_dung VARCHAR(500),
    gia_don_vi DECIMAL(10,2),
    thanh_tien DECIMAL(12,2),
    UNIQUE(don_thuoc_id, thuoc_id)
    );