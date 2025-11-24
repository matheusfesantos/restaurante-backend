package com.projeto.projeto_restaurante.services;

import com.projeto.projeto_restaurante.models.dto.ComentarioAtualizarDTO;
import com.projeto.projeto_restaurante.models.dto.ComentarioDTO;
import com.projeto.projeto_restaurante.models.dto.ComentarioResponseDTO;
import com.projeto.projeto_restaurante.models.entity.Comentarios;
import com.projeto.projeto_restaurante.models.entity.Produtos;
import com.projeto.projeto_restaurante.models.entity.Usuarios;
import com.projeto.projeto_restaurante.repositories.ComentariosRepository;
import com.projeto.projeto_restaurante.repositories.ProdutosRepository;
import com.projeto.projeto_restaurante.repositories.UsuariosRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ComentarioService {

    @Autowired
    private ComentariosRepository comentariosRepository;

    @Autowired
    private ProdutosRepository produtosRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    public List<ComentarioResponseDTO> findAll() {
            log.info("Gerando lista de comentarios");
            List<Comentarios> list = comentariosRepository.findAll();
            return list.stream()
                    .map(c -> new ComentarioResponseDTO(
                            c.getId(),
                            c.getProduto().getNome(),
                            c.getProduto().getId(),
                            c.getUsuario().getNome(),
                            c.getUsuario().getId(),
                            c.getComentario(),
                            c.getDataComentario()
                    ))
                    .collect(Collectors.toList());
    }

    public ComentarioResponseDTO save(ComentarioDTO dto, UUID usuario_id) {
            Comentarios comentario = new Comentarios();

            Produtos produto = produtosRepository.findById(dto.produtoId())
                    .orElseThrow(() -> new EntityNotFoundException("O usuário não existe"));

            Usuarios usuario = usuariosRepository.findById(usuario_id)
                    .orElseThrow(() -> new EntityNotFoundException("O usuário não existe"));


            log.info("Criando comentario: {}", dto.comentario());

            comentario.setProduto(produto);
            comentario.setUsuario(usuario);
            comentario.setComentario(dto.comentario());

            Comentarios save = comentariosRepository.save(comentario);

            log.info("Comentario criado");

            return new ComentarioResponseDTO(
                    save.getId(),
                    save.getProduto().getNome(),
                    save.getProduto().getId(),
                    save.getUsuario().getNome(),
                    save.getUsuario().getId(),
                    save.getComentario(),
                    save.getDataComentario()
            );
    }

    public ComentarioResponseDTO edit(Long comentario_id, ComentarioAtualizarDTO comentario_novo, UUID usuarios_id){
            Comentarios comentario = comentariosRepository.findById(comentario_id)
                    .orElseThrow(() -> new EntityNotFoundException("O comentário não existe"));

            if (!comentario.getUsuario().getId().equals(usuarios_id)) {
                log.error("Não é possivel editar o comentario de outro usuário");
                throw new AccessDeniedException("O usuário não pode editar esse comentário");
            }

            log.info("Editando comentario de {} para {}", comentario.getComentario(), comentario_novo.comentario());

            comentario.setComentario(comentario_novo.comentario());
            Comentarios edit = comentariosRepository.save(comentario);

            log.info("Comentario editado");
            return new ComentarioResponseDTO(
                    edit.getId(),
                    edit.getProduto().getNome(),
                    edit.getProduto().getId(),
                    edit.getUsuario().getNome(),
                    edit.getUsuario().getId(),
                    edit.getComentario(),
                    edit.getDataComentario()
            );
    }

    public void deletar(Long comentario_id, UUID usuarios_id){
            Comentarios comentario = comentariosRepository.findById(comentario_id)
                    .orElseThrow(() -> new EntityNotFoundException("O comentário não existe"));

            if (!comentario.getUsuario().getId().equals(usuarios_id)) {
                log.error("Não é possivel deletar o comentario de outro usuário");
                throw new AccessDeniedException("O usuário não pode deletar esse comentario");
            }
            comentariosRepository.delete(comentario);
            log.info("O comentariode Id: {} foi deletado", comentario.getId());
    }

    public List<ComentarioResponseDTO> comentariosByProduto (Long produto_id){
        log.info("Buscando todos os comentarios do produto com Id: {}", produto_id);
        List<Comentarios> comentarios = comentariosRepository.findByProdutoId(produto_id);
        return comentarios.stream()
                .map(c -> new ComentarioResponseDTO(
                        c.getId(),
                        c.getProduto().getNome(),
                        c.getProduto().getId(),
                        c.getUsuario().getNome(),
                        c.getUsuario().getId(),
                        c.getComentario(),
                        c.getDataComentario()
                ))
                .collect(Collectors.toList());
    }
}