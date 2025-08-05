package MediChecker.MediChecker.repository;

import MediChecker.MediChecker.entity.DieuTri;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DieuTriRepository extends JpaRepository<DieuTri, Long> {
    
    List<DieuTri> findByBenhNhanId(Long benhNhanId);
}
