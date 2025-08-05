package MediChecker.MediChecker.repository;

import MediChecker.MediChecker.entity.DonThuoc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonThuocRepository extends JpaRepository<DonThuoc, Long> {
    
    Page<DonThuoc> findByBenhNhanId(Long benhNhanId, Pageable pageable);
    
    boolean existsByMaDonThuoc(String maDonThuoc);
}
