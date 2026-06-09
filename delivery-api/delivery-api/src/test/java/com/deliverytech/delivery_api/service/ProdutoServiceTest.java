package com.deliverytech.delivery_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import com.deliverytech.delivery_api.exception.EntityNotFoundException;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;


import com.deliverytech.delivery_api.dto.req.ProdutoReqDTO;
import com.deliverytech.delivery_api.dto.res.ProdutoResDTO;
import com.deliverytech.delivery_api.entity.Produto;
import com.deliverytech.delivery_api.entity.Restaurante;
import com.deliverytech.delivery_api.repository.ProdutoRepository;
import com.deliverytech.delivery_api.repository.RestauranteRepository;
import com.deliverytech.delivery_api.service.impl.ProdutoServiceImpl;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private RestauranteRepository restauranteRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProdutoServiceImpl service;


    @Test
    void deveCadastrarProduto() {

        ProdutoReqDTO dto = new ProdutoReqDTO();
        dto.setRestauranteId(1L);

        Restaurante restaurante = new Restaurante();
        restaurante.setId(1L);

        Produto produto = new Produto();

        ProdutoResDTO resposta = new ProdutoResDTO();

        when(restauranteRepository.findById(1L))
            .thenReturn(Optional.of(restaurante));

        when(modelMapper.map(dto, Produto.class))
            .thenReturn(produto);

        when(produtoRepository.save(any(Produto.class)))
            .thenReturn(produto);

        when(modelMapper.map(produto, ProdutoResDTO.class))
            .thenReturn(resposta);

        ProdutoResDTO resultado =
            service.cadastrar(dto);

        assertNotNull(resultado);

        verify(produtoRepository)
            .save(any(Produto.class));
    }

    @Test
    void deveLancarExcecaoQuandoRestauranteNaoExiste() {

        ProdutoReqDTO dto = new ProdutoReqDTO();
        dto.setRestauranteId(1L);

        when(restauranteRepository.findById(1L))
            .thenReturn(Optional.empty());

        assertThrows(
            EntityNotFoundException.class,
            () -> {
                service.cadastrar(dto);
            }
        );
    }

    @Test
    void deveBuscarProdutoPorId() {

        Produto produto = new Produto();
        produto.setId(1);

        ProdutoResDTO dto = new ProdutoResDTO();

        when(produtoRepository.findById(1L))
            .thenReturn(Optional.of(produto));

        when(modelMapper.map(
            produto,
            ProdutoResDTO.class))
            .thenReturn(dto);

        ProdutoResDTO resultado =
            service.buscarProdutoPorId(1L);

        assertNotNull(resultado);
    }

    @Test
    void deveLancarExcecaoAoBuscarProdutoInexistente() {

        when(produtoRepository.findById(1L))
            .thenReturn(Optional.empty());

        assertThrows(
            EntityNotFoundException.class,
                () -> service.buscarProdutoPorId(1L)
        );
    }

    @Test
    void deveAlterarDisponibilidade() {

        Produto produto = new Produto();
        produto.setDisponivel(true);

        ProdutoResDTO dto = new ProdutoResDTO();

        when(produtoRepository.findById(1L))
            .thenReturn(Optional.of(produto));

        when(produtoRepository.save(any()))
            .thenReturn(produto);

        when(modelMapper.map(
            any(Produto.class),
            eq(ProdutoResDTO.class)))
            .thenReturn(dto);

        ProdutoResDTO resultado =
            service.alterarDisponibilidade(1L);

        assertNotNull(resultado);

        verify(produtoRepository)
            .save(any(Produto.class));
    }

    @Test
    void deveRemoverProduto() {

        Produto produto = new Produto();

        when(produtoRepository.findById(1L))
            .thenReturn(Optional.of(produto));

        service.removerProduto(1L);

        verify(produtoRepository)
            .delete(produto);
    }

    @Test
    void deveAtualizarProduto() {  

        Produto produto = new Produto();
        produto.setId(1);

        ProdutoReqDTO req = new ProdutoReqDTO();
        req.setNome("Pizza Calabresa");
        req.setDescricao("Pizza grande de calabresa");
        req.setCategoria("Pizza");
        req.setPreco(new BigDecimal("45.00"));

        ProdutoResDTO res = new ProdutoResDTO();
        res.setNome("Pizza Calabresa");

        when(produtoRepository.findById(1L))
            .thenReturn(Optional.of(produto));

        when(produtoRepository.save(any(Produto.class)))
            .thenReturn(produto);

        when(modelMapper.map(
            any(Produto.class),
            eq(ProdutoResDTO.class)))
            .thenReturn(res);

        ProdutoResDTO resultado =
            service.atualizarProduto(1L, req);

        assertEquals(
            "Pizza Calabresa",
            resultado.getNome()
        );
    }

    @Test
    void deveLancarExcecaoAoAtualizarProdutoInexistente() {

        ProdutoReqDTO req = new ProdutoReqDTO();

        when(produtoRepository.findById(1L))
            .thenReturn(Optional.empty());

        assertThrows(
            EntityNotFoundException.class,
            () -> service.atualizarProduto(1L, req)
        );
    }

    @Test
    void deveBuscarProdutosPorCategoria() {

        Produto produto = new Produto();

        ProdutoResDTO dto =
            new ProdutoResDTO();

        when(produtoRepository
            .findByCategoriaAndDisponivelTrue("Pizza"))
            .thenReturn(List.of(produto));

        when(modelMapper.map(
            produto,
            ProdutoResDTO.class))
            .thenReturn(dto);

        List<ProdutoResDTO> resultado =
            service.buscarProdutosPorCategoria("Pizza");

        assertEquals(1, resultado.size());
    }

    @Test
    void deveBuscarProdutosPorNome() {

        Produto produto = new Produto();

        ProdutoResDTO dto =
            new ProdutoResDTO();

        when(produtoRepository
            .findByNomeContainingIgnoreCaseAndDisponivelTrue(
                "Pizza"))
            .thenReturn(List.of(produto));

        when(modelMapper.map(
            produto,
            ProdutoResDTO.class))
            .thenReturn(dto);

        List<ProdutoResDTO> resultado =
            service.buscarProdutosPorNome("Pizza");

        assertEquals(1, resultado.size());
    }

    @Test
    void deveBuscarProdutosPorRestaurante() {

        Produto produto = new Produto();

        ProdutoResDTO dto =
            new ProdutoResDTO();

        when(produtoRepository
            .findByRestauranteIdAndDisponivelTrue(1L))
            .thenReturn(List.of(produto));

        when(modelMapper.map(
            produto,
            ProdutoResDTO.class))
            .thenReturn(dto);

        List<ProdutoResDTO> resultado =
            service.buscarProdutosPorRestaurante(
                1L,
                true
            );

        assertEquals(1, resultado.size());
    }

    @Test
    void deveBuscarProdutosPorRestauranteQuandoDisponivelFalse() {

        Produto produto = new Produto();

        ProdutoResDTO dto =
            new ProdutoResDTO();

        when(produtoRepository
            .findByRestauranteIdAndDisponivelTrue(1L))
            .thenReturn(List.of(produto));

        when(modelMapper.map(
            produto,
            ProdutoResDTO.class))
            .thenReturn(dto);

        List<ProdutoResDTO> resultado =
            service.buscarProdutosPorRestaurante(
                1L,
                false
            );

        assertEquals(1, resultado.size());
    }
    
}


