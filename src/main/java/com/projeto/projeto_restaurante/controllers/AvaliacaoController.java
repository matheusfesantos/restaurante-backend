package com.projeto.projeto_restaurante.controllers;

import com.projeto.projeto_restaurante.models.dto.AvaliacaoDTO;
import com.projeto.projeto_restaurante.models.dto.AvaliacaoResponseDTO;
import com.projeto.projeto_restaurante.models.entity.Avaliacao;
import com.projeto.projeto_restaurante.models.entity.Usuarios;
import com.projeto.projeto_restaurante.services.AvaliacaoService;
import com.projeto.projeto_restaurante.services.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    public AvaliacaoController (AvaliacaoService avaliacaoService){
        this.avaliacaoService = avaliacaoService;
    }
    @GetMapping
    public List<AvaliacaoResponseDTO> findAll (){return avaliacaoService.findAll();}

    @GetMapping("/produtos/{id}/media")
    public ResponseEntity<?> mediaAvaliacaoPorProduto(@PathVariable Long id){
        try {
            Double media = avaliacaoService.mediaDeNotasPorProduto(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("media", media));
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "erro", "Produto não encontrado",
                            "mensagem", e.getMessage()
                    ));
        }
    }
    @PostMapping("/cadastrar")
    public ResponseEntity<?> salvar(@NotNull @RequestBody @Valid AvaliacaoDTO dto,
                                    @Valid @RequestHeader("Authorization") String token) {
        try{
            String msg = avaliacaoService.save(dto, token);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", msg));
        } catch (EntityNotFoundException e){
            log.error("Erro ao encontrar a entidade durante o processo de cadastrar uma avaliacao: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Não foi possivel cadastrar a avaliação"));
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Erro interno ao cadastrar avaliação"));
        }
    }

    @PatchMapping("/editar/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody AvaliacaoDTO dto,
                                    @PathVariable Long id,
                                    @Valid @RequestHeader("Authorization") String token) {
        try{
            String msg = avaliacaoService.editar(dto, id, token);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("message", msg));
        } catch (EntityNotFoundException e){
            log.error("Erro ao encontrar a entidade durante o processo de editar uma avaliacao: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Não foi possivel editar a avaliação"));
        } catch (AccessDeniedException e){
            log.error("O usuario tentou editar a avaliacao de outro usuario: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Não foi possivel editar a avaliação"));
        } catch (Exception e){
            log.error("Erro interno ao editar uma avaliação: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Não foi possivel editar a avaliação"));
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id,
                                     @Valid @RequestHeader("Authorization") String token){
        try{
            String msg = avaliacaoService.deletar(id, token);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("message", msg));
        } catch (EntityNotFoundException e){
            log.error("Erro ao encontrar a entidade durante o processo de deletar uma avaliacao: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Não foi possivel deletar a avaliação"));
        } catch (AccessDeniedException e){
            log.error("O usuario tentou deletar a avaliacao de outro usuario: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Não foi possivel editar a avaliação"));
        } catch (Exception e){
            log.error("Erro interno ao deletar uma avaliação: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Não foi possivel deletar a avaliação"));
        }
    }

}
