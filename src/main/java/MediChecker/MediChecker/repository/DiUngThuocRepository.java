package MediChecker.MediChecker.repository;

import MediChecker.MediChecker.entity.DiUngThuoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiUngThuocRepository extends JpaRepository<DiUngThuoc, Long> {
    
    List<DiUngThuoc> findByBenhNhanId(Long benhNhanId);
}
