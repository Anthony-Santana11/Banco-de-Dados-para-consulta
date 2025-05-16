package org.novagm.projetoconsulta.service;

import java.time.LocalDateTime;

import org.novagm.projetoconsulta.exception.DocumentoValidationException;
import org.novagm.projetoconsulta.exception.ResourceNotFoundException;
import org.novagm.projetoconsulta.model.Documento;
import org.novagm.projetoconsulta.repository.DocumentoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Validated
@Transactional
public class DocumentoService {
    private final DocumentoRepository documentoRepository;

    public DocumentoService(DocumentoRepository documentoRepository) {
        this.documentoRepository = documentoRepository;
    }

    @Transactional
    public Documento criar(@Valid @NotNull Documento documento) {
        log.info("Criando novo documento: {}", documento);
        validarDocumento(documento);
        documento.setDataCriacao(LocalDateTime.now());
        return documentoRepository.save(documento);
    }

    @Transactional
    public Documento atualizar(@NotNull Long id, @Valid @NotNull Documento documento) {
        log.info("Atualizando documento ID {}: {}", id, documento);
        validarDocumento(documento);
        
        return documentoRepository.findById(id)
            .map(documentoExistente -> {
                documentoExistente.setNome(documento.getNome());
                documentoExistente.setTipoProjeto(documento.getTipoProjeto());
                documentoExistente.setCartorio(documento.getCartorio());
                documentoExistente.setDataAtualizacao(LocalDateTime.now());
                return documentoRepository.save(documentoExistente);
            })
            .orElseThrow(() -> new ResourceNotFoundException("Documento não encontrado com ID: " + id));
    }

    @Transactional
    public void deletar(@NotNull Long id) {
        log.info("Deletando documento ID: {}", id);
        if (!documentoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Documento não encontrado com ID: " + id);
        }
        documentoRepository.deleteById(id);
    }

    public Page<Documento> buscarPorProjetoECartorio(String tipoProjeto, String cartorio, Pageable pageable) {
        log.info("Buscando documentos por projeto {} e cartório {}", tipoProjeto, cartorio);
        return documentoRepository.findByTipoProjetoAndCartorio(tipoProjeto, cartorio, pageable);
    }

    public Documento buscarPorId(@NotNull Long id) {
        log.info("Buscando documento por ID: {}", id);
        return documentoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Documento não encontrado com ID: " + id));
    }

    public Page<Documento> buscarPorCartorio(String cartorio, Pageable pageable) {
        log.info("Buscando documentos por cartório: {}", cartorio);
        return documentoRepository.findByCartorio(cartorio, pageable);
    }

    public Page<Documento> buscarPorNome(String nome, Pageable pageable) {
        log.info("Buscando documentos por nome contendo: {}", nome);
        return documentoRepository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    public Page<Documento> listarTodosOrdenadosPorData(Pageable pageable) {
        log.info("Listando todos os documentos ordenados por data");
        return documentoRepository.findAllByOrderByDataCriacaoDesc(pageable);
    }

    public boolean verificarExistencia(String cartorio, String tipoProjeto) {
        log.info("Verificando existência de documento para cartório {} e projeto {}", cartorio, tipoProjeto);
        return documentoRepository.existsByCartorioAndTipoProjeto(cartorio, tipoProjeto);
    }

    private void validarDocumento(Documento documento) {
        if (documento.getNome() == null || documento.getNome().trim().isEmpty()) {
            throw new DocumentoValidationException("Nome do documento é obrigatório");
        }
        if (documento.getTipoProjeto() == null || documento.getTipoProjeto().trim().isEmpty()) {
            throw new DocumentoValidationException("Tipo do projeto é obrigatório");
        }
        if (documento.getCartorio() == null || documento.getCartorio().trim().isEmpty()) {
            throw new DocumentoValidationException("Cartório é obrigatório");
        }
    }
}
