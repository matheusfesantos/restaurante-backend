package com.projeto.projeto_restaurante.controllers;

import com.projeto.projeto_restaurante.models.dto.ProdutoDTO;
import com.projeto.projeto_restaurante.models.entity.Produtos;
import com.projeto.projeto_restaurante.services.ProdutoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @GetMapping(value = "/todos")
    public List<Produtos> findAll() {
        return service.findAll();
    }

    @PostMapping("/cadastrar-produto")
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> salvarProduto(@Valid @NotNull ProdutoDTO dto){
        return ResponseEntity.ok(service.save(dto));
    }

    @PatchMapping("/atualizar-produto")
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> atualizarProduto(@Valid @NotNull ProdutoDTO dto){
        return ResponseEntity.ok(service.atualizar(dto));
    }

    @DeleteMapping("/{id}")
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> deletarProduto(@PathVariable Long id){
        return ResponseEntity.ok(service.delete(id));
    }
}
