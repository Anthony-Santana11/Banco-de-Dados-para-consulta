package org.novagm.projetoconsulta.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.novagm.projetoconsulta.model.SyncStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SyncStatusRepository extends JpaRepository<SyncStatus, Long> {
    
    Optional<SyncStatus> findTopByOrderByStartTimeDesc();
    
    List<SyncStatus> findByStatusOrderByStartTimeDesc(String status);
    
    @Query("SELECT s FROM SyncStatus s WHERE s.startTime >= :startDate AND s.startTime <= :endDate")
    List<SyncStatus> findSyncStatusInPeriod(LocalDateTime startDate, LocalDateTime endDate);
    
    List<SyncStatus> findByStartTimeBetweenOrderByStartTimeDesc(LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("SELECT COUNT(s) > 0 FROM SyncStatus s WHERE s.status = 'IN_PROGRESS'")
    boolean hasActiveSynchronization();
}