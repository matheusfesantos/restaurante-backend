package com.projeto.projeto_restaurante.controllers;

import com.projeto.projeto_restaurante.models.dto.ProdutoDTO;
import com.projeto.projeto_restaurante.models.entity.Produtos;
import com.projeto.projeto_restaurante.services.ProdutoService;
import com.projeto.projeto_restaurante.services.UsuarioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService service;
    private final UsuarioService usuarioService;

    public ProdutoController(ProdutoService service, UsuarioService usuarioService) {
        this.service = service;
        this.usuarioService = usuarioService;
    }

    @GetMapping(value = "/todos")
    public List<Produtos> findAll() {
        return service.findAll();
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> salvarProduto(@Valid @NotNull @RequestBody ProdutoDTO dto,
                                           @RequestHeader("Authorization") String token){
        try {

            UUID uuid = usuarioService.recuperarUUIDporToken(token);

            boolean save = service.save(dto, uuid);

            if (save){
                log.info("Produto cadastrado com sucesso");
                return ResponseEntity.status(HttpStatus.CREATED).
                        body(Map.of("message", "Produto cadastrado com sucesso"));
            }
            else{
                log.error("Erro ao salvar produto");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("message", "Erro interno ao cadastrar produto"));
            }
        }
        catch (Exception e) {
            log.error("Erro no controller ao salvar produto: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Erro interno ao cadastrar produto"));
        }
    }

    @PatchMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarProduto(@Valid @RequestBody ProdutoDTO dto, @PathVariable Long id){
        try {
            boolean update = service.atualizar(dto, id);

            if (update){
                log.info("Produto atualizado com sucesso");
                return ResponseEntity.status(HttpStatus.OK).
                        body(Map.of("message", "Produto atualizado com sucesso"));
            }
            else{
                log.error("Erro ao atualizar produto");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("message", "Erro interno ao atualizar produto"));
            }
        }
        catch (Exception e){
            log.error("Erro ao atualizar produto: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Erro interno ao atualizar produto"));
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarProduto(@PathVariable Long id){
        try{
            boolean delete = service.delete(id);

            if (delete){
                return ResponseEntity.status(HttpStatus.OK).
                        body(Map.of("message", "Produto deletado com sucesso"));
            }
            else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("message", "Erro interno ao deletar produto"));
            }
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Erro interno ao deletar produto"));
        }
    }
}
