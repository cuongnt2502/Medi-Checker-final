package MediChecker.MediChecker.repository;

import MediChecker.MediChecker.entity.Thuoc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThuocRepository extends JpaRepository<Thuoc, Long> {
    
    Page<Thuoc> findByKichHoatTrue(Pageable pageable);
    
    Page<Thuoc> findByTenThuocContainingIgnoreCaseOrTenHoatChatContainingIgnoreCaseAndKichHoatTrue(
            String tenThuoc, String tenHoatChat, Pageable pageable);
    
    boolean existsByMaThuoc(String maThuoc);
}
