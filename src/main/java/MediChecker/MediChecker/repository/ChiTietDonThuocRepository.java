package MediChecker.MediChecker.repository;

import MediChecker.MediChecker.entity.ChiTietDonThuoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChiTietDonThuocRepository extends JpaRepository<ChiTietDonThuoc, Long> {
    
    boolean existsByDonThuocIdAndThuocId(Long donThuocId, Long thuocId);
    
    Optional<ChiTietDonThuoc> findByDonThuocIdAndThuocId(Long donThuocId, Long thuocId);
}
