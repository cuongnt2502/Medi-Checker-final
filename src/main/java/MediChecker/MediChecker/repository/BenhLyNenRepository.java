package MediChecker.MediChecker.repository;

import MediChecker.MediChecker.entity.BenhLyNen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BenhLyNenRepository extends JpaRepository<BenhLyNen, Long> {
    
    List<BenhLyNen> findByBenhNhanId(Long benhNhanId);
}
