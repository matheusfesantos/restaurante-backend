package com.projeto.projeto_restaurante.services;

import com.projeto.projeto_restaurante.models.dto.ProdutoDTO;
import com.projeto.projeto_restaurante.models.entity.Produtos;
import com.projeto.projeto_restaurante.models.entity.Usuarios;
import com.projeto.projeto_restaurante.repositories.ProdutosRepository;
import com.projeto.projeto_restaurante.repositories.UsuariosRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ProdutoService {

    private final ProdutosRepository repository;
    private final UsuariosRepository usuariosRepository;

    public ProdutoService(ProdutosRepository repository, UsuariosRepository usuariosRepository) {
        this.repository = repository;
        this.usuariosRepository = usuariosRepository;
    }

    /**
     * Retrieves a list of all products from the repository.
     *
     * @return a list containing all instances of {@code Produtos} stored in the repository
     */
    public List<Produtos> findAll(){
        log.info("Buscando todos os produtos");
        return repository.findAll();
    }

    public Produtos findById(Long produtoId){

        return repository.findById(produtoId)
                .orElseThrow(() -> new EntityNotFoundException("Produto informado não esta no nosso banco de dados"));
    }

    /**
     * Saves a product based on the details provided in the {@code ProdutoDTO} and associates
     * it with a user identified by the given {@code id}.
     * If the user with the specified {@code id} is not found, an {@code EntityNotFoundException} is thrown.
     * Logs information and errors at various stages of the saving process.
     *
     * @param dto the product data transfer object containing details such as name, photo, and price
     * @param id the unique identifier of the user to associate the product with
     * @return {@code true} if the product was successfully saved, {@code false} otherwise
     */
    public boolean save(ProdutoDTO dto, UUID id){
        try{
            Produtos novoProduto = new Produtos();

            log.info("Salvando produto: {}", dto.nome());
            novoProduto.setNome(dto.nome());
            novoProduto.setFoto(dto.foto());
            novoProduto.setPreco(dto.preco());

            Usuarios usuario = usuariosRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("UUID: " + id+ ", não pertence a nenhum usuario"));

            novoProduto.setUsuario(usuario);
            repository.save(novoProduto);
            log.info("Produto salvo com sucesso!");
            return true;
        }
        catch (Exception e){
            log.error("Erro ao salvar produto: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Updates the properties of a product based on the non-null fields provided in the {@code ProdutoDTO}.
     * Only the specified fields (name, price, photo) will be updated.
     *
     * @param dto the data transfer object containing product details to be updated, such as name, price, and photo
     * @return {@code true} if the product was successfully updated, or an exception is thrown otherwise
     */
    public boolean atualizar(ProdutoDTO dto, Long id){
        try{
            Produtos produtoAtualizado = repository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("O Produto informado não existe"));

            if (dto.nome() != null){
                log.info("Atualizando nome do produto de {} para {}",produtoAtualizado.getNome(), dto.nome());
                produtoAtualizado.setNome(dto.nome());
            }

            if (dto.preco() != null){
                log.info("Atualizando o preço do produto de {} para {}",produtoAtualizado.getPreco(), dto.preco());
                produtoAtualizado.setPreco(dto.preco());
            }

            if (dto.foto() != null){
                log.info("Atualizando a foto do produto de {} para {}",produtoAtualizado.getFoto(), dto.foto());
                produtoAtualizado.setFoto(dto.foto());
            }

            repository.save(produtoAtualizado);
            return true;

        }
        catch (Exception e){
            log.error("Erro ao atualizar produto: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes a product from the repository based on its ID.
     * If the product with the given ID is not found, an {@code EntityNotFoundException} is thrown.
     * Logs errors in case of unsuccessful deletion.
     *
     * @param id the unique identifier of the product to be deleted
     * @return {@code true} if the product was successfully deleted, {@code false} if an error occurred
     */
    public boolean delete(Long id){
        try{
            Produtos produto = repository.findById(id).
                    orElseThrow(() -> new EntityNotFoundException("O Id: "+ id +", não pertence a nenhum produto"));

            repository.delete(produto);
            return true;
        }
        catch (Exception e){
            log.error("Erro ao deletar produto: {}", e.getMessage());
            return false;
        }
    }
}
