package org.novagm.projetoconsulta.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.novagm.projetoconsulta.model.Documento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    Page<Documento> findByTipoProjetoAndCartorio(String tipoProjeto, String cartorio, Pageable pageable);
    
    Page<Documento> findByCartorio(String cartorio, Pageable pageable);
    
    List<Documento> findByTipoProjeto(String tipoProjeto);
    
    Page<Documento> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
    
    Page<Documento> findAllByOrderByDataCriacaoDesc(Pageable pageable);
    
    boolean existsByCartorioAndTipoProjeto(String cartorio, String tipoProjeto);
    
    @Query("SELECT d FROM Documento d WHERE d.status = 'PENDING' ORDER BY d.dataCriacao ASC")
    List<Documento> findPendingDocuments();
    
    @Query("SELECT d FROM Documento d WHERE d.lastSyncTime < :lastSyncTime")
    List<Documento> findDocumentsToSync(LocalDateTime lastSyncTime);
}
