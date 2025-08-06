package MediChecker.MediChecker.repository;

import MediChecker.MediChecker.entity.BenhLyNen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BenhLyNenRepository extends JpaRepository<BenhLyNen, Long> {
    
    List<BenhLyNen> findByBenhNhanId(Long benhNhanId);

    Page<BenhLyNen> findByBenhNhanId(Long benhNhanId, Pageable pageable);

    @Query("SELECT b FROM BenhLyNen b WHERE b.benhNhan.id = :benhNhanId AND " +
            "(LOWER(b.tenBenh) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(b.maBenh) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<BenhLyNen> findByBenhNhanIdAndKeyword(@Param("benhNhanId") Long benhNhanId,
                                               @Param("keyword") String keyword,
                                               Pageable pageable);

    boolean existsByMaBenh(String maBenh);

    boolean existsByMaBenhAndIdNot(String maBenh, Long id);
}
