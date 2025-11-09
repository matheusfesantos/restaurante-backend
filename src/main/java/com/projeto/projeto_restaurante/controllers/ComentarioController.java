package com.projeto.projeto_restaurante.controllers;

import com.projeto.projeto_restaurante.models.dto.ComentarioAtualizarDTO;
import com.projeto.projeto_restaurante.models.dto.ComentarioDTO;
import com.projeto.projeto_restaurante.models.dto.ComentarioResponseDTO;
import com.projeto.projeto_restaurante.services.ComentarioService;
import com.projeto.projeto_restaurante.services.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private UsuarioService usuarioService;

    // Retorna todos os comentarios no padrão do Response
    @GetMapping("/comentarios")
    public List<ComentarioResponseDTO> findAll() {
        return comentarioService.findAll();
    }

    // Retorna todos os comentários baseados em um determinado produto no padrão do Response
    @GetMapping("/produtos/{id}/comentarios")
    public List<ComentarioResponseDTO> comentariosByProduto(@PathVariable @NotNull Long id){
        return comentarioService.comentariosByProduto(id);
    }

    // Criar um comentario
    @PostMapping("/comentarios")
    public ResponseEntity<?> criarComentario(
            @Valid @RequestBody ComentarioDTO dto,
            @RequestHeader("Authorization") String token)
    {
        try {
            UUID usuario_id = usuarioService.recuperarUUIDporToken(token);
            ComentarioResponseDTO comentario = comentarioService.save(dto, usuario_id);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(comentario);
        }
        catch (EntityNotFoundException e){
            log.error("Entidade não encontrada no banco ao criar comentario: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        }
        catch (Exception e){
            log.error("Erro interno ao criar comentario: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("/comentarios/{id}")
    public ResponseEntity<?> editarComentario(@Valid @NotNull @PathVariable Long id,
                                                 @RequestBody ComentarioAtualizarDTO comentario,
                                                 @RequestHeader("Authorization") String token) {
        try {
            UUID usuario_id = usuarioService.recuperarUUIDporToken(token);
            ComentarioResponseDTO edit = comentarioService.edit(id, comentario, usuario_id);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(edit);
        }
        catch (EntityNotFoundException e){
            log.error("Entidade não encontrada no banco ao editar comentario: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        }
        catch (AccessDeniedException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", e.getMessage()));
        }
        catch (Exception e){
            log.error("Erro interno ao editar comentario: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("comentarios/{id}")
    public ResponseEntity<?> deletarComentario(@Valid @NotNull @PathVariable Long id, @RequestHeader("Authorization") String token){
        try{
            UUID usuario_id = usuarioService.recuperarUUIDporToken(token);
            comentarioService.deletar(id, usuario_id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("message", "Objeto com Id: " + id + " foi deletado com sucesso"));
        }
        catch (EntityNotFoundException e){
            log.error("Entidade não encontrada no banco ao deletar comentario: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        }
        catch (AccessDeniedException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", e.getMessage()));
        }
        catch (Exception e){
            log.error("Erro interno ao deletar comentario: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", e.getMessage()));
        }
    }
}

