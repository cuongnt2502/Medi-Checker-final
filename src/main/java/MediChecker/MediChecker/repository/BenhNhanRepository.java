package MediChecker.MediChecker.repository;

import MediChecker.MediChecker.entity.BenhNhan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BenhNhanRepository extends JpaRepository<BenhNhan, Long> {
    
    Page<BenhNhan> findByHoTenContainingIgnoreCaseOrMaBenhNhanContainingIgnoreCase(
            String hoTen, String maBenhNhan, Pageable pageable);
    
    boolean existsByMaBenhNhan(String maBenhNhan);
}
