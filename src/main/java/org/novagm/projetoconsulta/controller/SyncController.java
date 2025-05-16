package org.novagm.projetoconsulta.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.novagm.projetoconsulta.repository.SyncStatusRepository;
import org.novagm.projetoconsulta.repository.DocumentoRepository;
import org.novagm.projetoconsulta.model.SyncStatus;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/sync")
public class SyncController {

    private final SyncStatusRepository syncStatusRepository;
    private final DocumentoRepository documentoRepository;

    @Autowired
    public SyncController(SyncStatusRepository syncStatusRepository, 
                         DocumentoRepository documentoRepository) {
        this.syncStatusRepository = syncStatusRepository;
        this.documentoRepository = documentoRepository;
    }

    @GetMapping("/status")
    public SyncStatus getLastSyncStatus() {
        return syncStatusRepository.findTopByOrderByStartTimeDesc()
                .orElse(null);
    }

    @GetMapping("/stats")
    public Map<String, Object> getSyncStats() {
        return Map.of(
            "totalDocumentos", documentoRepository.count(),
            "lastSync", syncStatusRepository.findTopByOrderByStartTimeDesc()
                .map(SyncStatus::getEndTime)
                .orElse(null),
            "nextSync", getNextSyncTime()
        );
    }

    private LocalDateTime getNextSyncTime() {
        return syncStatusRepository.findTopByOrderByStartTimeDesc()
                .map(lastSync -> lastSync.getEndTime().plusHours(24))
                .orElse(LocalDateTime.now().plusHours(24));
    }
}