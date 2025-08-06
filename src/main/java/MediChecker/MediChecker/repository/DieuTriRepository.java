package MediChecker.MediChecker.repository;

import MediChecker.MediChecker.entity.DieuTri;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DieuTriRepository extends JpaRepository<DieuTri, Long> {
    
    List<DieuTri> findByBenhNhanId(Long benhNhanId);

    Page<DieuTri> findByBenhNhanId(Long benhNhanId, Pageable pageable);

    @Query("SELECT d FROM DieuTri d WHERE d.benhNhan.id = :benhNhanId AND " +
            "(LOWER(d.chanDoanChinh) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(d.bacSiDieuTri) LIKE LOWER(CONCAT('%', :keyword, '%')))")

    Page<DieuTri> findByBenhNhanIdAndKeyword(@Param("benhNhanId") Long benhNhanId,
                                             @Param("keyword") String keyword,
                                             Pageable pageable);

    @Query("SELECT d FROM DieuTri d WHERE d.ngayBatDau BETWEEN :startDate AND :endDate")
    List<DieuTri> findByNgayBatDauBetween(@Param("startDate") LocalDateTime startDate,
                                          @Param("endDate") LocalDateTime endDate);
}
