package com.projeto.projeto_restaurante.services;

import com.projeto.projeto_restaurante.models.dto.AvaliacaoDTO;
import com.projeto.projeto_restaurante.models.dto.AvaliacaoResponseDTO;
import com.projeto.projeto_restaurante.models.entity.Avaliacao;
import com.projeto.projeto_restaurante.models.entity.Produtos;
import com.projeto.projeto_restaurante.models.entity.Usuarios;
import com.projeto.projeto_restaurante.repositories.AvaliacaoRepository;
import com.projeto.projeto_restaurante.repositories.ProdutosRepository;
import com.projeto.projeto_restaurante.repositories.UsuariosRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AvaliacaoService {

    private final AvaliacaoRepository repository;
    private final UsuarioService usuarioService;
    private final UsuariosRepository usuariosRepository;
    private final ProdutosRepository produtosRepository;
    private final AvaliacaoRepository avaliacaoRepository;

    public AvaliacaoService(AvaliacaoRepository repository, UsuarioService usuarioService, UsuariosRepository usuariosRepository, ProdutosRepository produtosRepository, AvaliacaoRepository avaliacaoRepository) {
        this.repository = repository;
        this.usuarioService = usuarioService;
        this.usuariosRepository = usuariosRepository;
        this.produtosRepository = produtosRepository;
        this.avaliacaoRepository = avaliacaoRepository;
    }

    public List<AvaliacaoResponseDTO> findAll() {
        log.info("Gerando lista de avaliacoes");
        List<Avaliacao> list = avaliacaoRepository.findAll();
        return list.stream()
                .map(a -> new AvaliacaoResponseDTO(
                        a.getId(),
                        a.getProduto().getNome(),
                        a.getProduto().getId(),
                        a.getUsuario().getNome(),
                        a.getUsuario().getId(),
                        a.getNota(),
                        a.getData()
                ))
                .collect(Collectors.toList());
    }

    public String save(AvaliacaoDTO dto, String token) {
        log.info("Iniciando o processo de salvar a avaliacao");
        UUID usuarioId = usuarioService.recuperarUUIDporToken(token);
        Avaliacao novaAvaliacao = new Avaliacao();

        if (avaliacaoRepository.existsByUsuario_IdAndProduto_Id(usuarioId, dto.produtoId())){
            log.error("O usuário já avaliou esse produto");
            throw new RuntimeException("O usuario ja avaliou esse produto");
        }

        Usuarios usuario = usuariosRepository.findById(usuarioId).orElseThrow(
                () -> new EntityNotFoundException("O usuario nao existe"));

        Produtos produto = produtosRepository.findById(dto.produtoId()).orElseThrow(
                () -> new EntityNotFoundException("O produto nao existe"));

        novaAvaliacao.setUsuario(usuario);
        novaAvaliacao.setNota(dto.nota());
        novaAvaliacao.setProduto(produto);

        repository.save(novaAvaliacao);
        log.info("Avaliação salva com sucesso");
        return "Avaliação foi salva com sucesso";
    }

    public String editar(AvaliacaoDTO dto, Long avaliacaoId, String token) {
        log.info("Iniciando o processo de editar a avaliacao");

        UUID usuarioId = usuarioService.recuperarUUIDporToken(token);
        Avaliacao avaliacao = avaliacaoRepository.findById(avaliacaoId).orElseThrow(
                () -> new EntityNotFoundException("A avaliacao nao existe")
        );
        if (!avaliacao.getUsuario().getId().equals(usuarioId)) {
            log.error("O usuario nao pode modificar uma avaliacao que nao o pertence");
            throw new AccessDeniedException("O usuario so pode editar suas proprias avaliacoes");
        }

        log.info("Atualizando nota de {} para {}", avaliacao.getNota(), dto.nota());
        avaliacao.setNota(dto.nota());

        avaliacaoRepository.save(avaliacao);

        log.info("Avaliacao atualizada com sucesso");
        return "Avaliacao foi atualizada com sucesso";
    }

    public String deletar(Long avaliacaoId, String token){
        log.info("Iniciando o processo de deletar a avaliacao");

        UUID usuarioId = usuarioService.recuperarUUIDporToken(token);
        Avaliacao avaliacao = avaliacaoRepository.findById(avaliacaoId).orElseThrow(
                () -> new EntityNotFoundException("A avaliacao nao existe")
        );

        if (!avaliacao.getUsuario().getId().equals(usuarioId)) {
            log.error("O usuario nao pode deletar uma avaliacao que nao o pertence");
            throw new AccessDeniedException("O usuario so pode deletar suas proprias avaliacoes");
        }

        avaliacaoRepository.delete(avaliacao);
        log.info("Avaliacao deletada com sucesso");
        return "Avaliacao foi deletada com sucesso";
    }

    public Double mediaDeNotasPorProduto(Long produtoId){
        log.info("Iniciando o processo de buscar a media de avaliacao por produto");
        if (!produtosRepository.existsById(produtoId)){
            log.error("O id: {} não corresponde a nenhum produto", produtoId);
            throw new EntityNotFoundException("O id não corresponde a nenhum produto");
        }
        Double media = avaliacaoRepository.calcularMediaPorProduto(produtoId);
        log.info("Avaliacao calculada com sucesso");
        return media;
    }
}
