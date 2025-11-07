package com.projeto.projeto_restaurante.services;

import com.projeto.projeto_restaurante.models.dto.ProdutoDTO;
import com.projeto.projeto_restaurante.models.entity.Produtos;
import com.projeto.projeto_restaurante.repositories.ProdutosRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProdutoService {

    @Autowired
    private ProdutosRepository repository;

    /**
     * Retrieves a list of all products from the repository.
     *
     * @return a list containing all instances of {@code Produtos} stored in the repository
     */
    public List<Produtos> findAll(){
        log.info("Buscando todos os produtos");
        return repository.findAll();
    }

    /**
     * Persists a product in the repository based on the information provided in the {@code AdicionarProdutoDTO}.
     *
     * @param dto the data transfer object containing the product's details, including its name, photo, and price
     * @return {@code true} if the product was successfully saved, {@code false} otherwise
     */
    public boolean save(ProdutoDTO dto){
        try{
            Produtos novoProduto = new Produtos();

            log.info("Salvando produto: {}", dto.nome());
            novoProduto.setNome(dto.nome());
            novoProduto.setFoto(dto.foto());
            novoProduto.setPreco(dto.preco());

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
    public boolean atualizar(ProdutoDTO dto){
        try{
            Produtos produtoAtualizado = new Produtos();

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
