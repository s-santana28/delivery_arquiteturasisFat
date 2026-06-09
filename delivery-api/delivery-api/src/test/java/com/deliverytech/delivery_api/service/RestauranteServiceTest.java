package com.deliverytech.delivery_api.service;

import com.deliverytech.delivery_api.dto.req.RestauranteReqDTO;
import com.deliverytech.delivery_api.dto.res.RestauranteResDTO;
import com.deliverytech.delivery_api.entity.Restaurante;
import com.deliverytech.delivery_api.exception.BusinessException;
import com.deliverytech.delivery_api.exception.EntityNotFoundException;
import com.deliverytech.delivery_api.repository.RestauranteRepository;
import com.deliverytech.delivery_api.service.impl.RestauranteServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class RestauranteServiceTest{

    @Mock
    private RestauranteRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RestauranteServiceImpl service;

    @Test
    void deveCadastrarRestaurante() {

        RestauranteReqDTO dto = new RestauranteReqDTO();
        dto.setNome("Pizza House");

        Restaurante restaurante = new Restaurante();
        restaurante.setNome("Pizza House");

        RestauranteResDTO resposta = new RestauranteResDTO();
        resposta.setNome("Pizza House");

        when(repository.findByNome("Pizza House"))
            .thenReturn(Optional.empty());

        when(modelMapper.map(dto, Restaurante.class))
            .thenReturn(restaurante);

        when(repository.save(any(Restaurante.class)))
            .thenReturn(restaurante);

        when(modelMapper.map(restaurante, RestauranteResDTO.class))
            .thenReturn(resposta);

        RestauranteResDTO resultado =
            service.cadastrarRestaurante(dto);

        assertNotNull(resultado);
        assertEquals("Pizza House", resultado.getNome());

        verify(repository).save(any(Restaurante.class));
    }
    
        
    @Test
    void deveLancarExcecaoQuandoNomeJaExiste() {

        RestauranteReqDTO dto = new RestauranteReqDTO();
        dto.setNome("Pizza House");

        when(repository.findByNome("Pizza House"))
            .thenReturn(Optional.of(new Restaurante()));

        assertThrows(
            BusinessException.class,
            () -> service.cadastrarRestaurante(dto)
        );
    }

    @Test
    void deveBuscarPorId() {

        Restaurante restaurante = new Restaurante();
        restaurante.setId(1L);

        RestauranteResDTO dto = new RestauranteResDTO();
        dto.setId(1L);

        when(repository.findById(1L))
            .thenReturn(Optional.of(restaurante));

        when(modelMapper.map(restaurante, RestauranteResDTO.class))
            .thenReturn(dto);

        RestauranteResDTO resultado =
            service.buscarPorId(1L);

        assertEquals(1L, resultado.getId());
    }
    
    @Test
    void deveLancarExcecaoQuandoIdNaoExiste() {

        when(repository.findById(1L))
            .thenReturn(Optional.empty());

        assertThrows(
            EntityNotFoundException.class,
            () -> service.buscarPorId(1L)
        );
    }

    @Test
    void deveAlterarStatusRestaurante() {

        Restaurante restaurante = new Restaurante();
        restaurante.setId(1L);
        restaurante.setAtivo(true);

        RestauranteResDTO dto = new RestauranteResDTO();
        dto.setAtivo(false);

        when(repository.findById(1L))
            .thenReturn(Optional.of(restaurante));

        when(repository.save(any(Restaurante.class)))
            .thenReturn(restaurante);

        when(modelMapper.map(any(Restaurante.class),
            eq(RestauranteResDTO.class)))
            .thenReturn(dto);

        RestauranteResDTO resultado =
            service.alterarStatusRestaurante(1L);

        assertFalse(resultado.isAtivo());
    }

    @Test
    void deveAtualizarRestaurante() {

        Restaurante restaurante = new Restaurante();
        restaurante.setId(1L);
        restaurante.setNome("Pizza House");

        RestauranteReqDTO req = new RestauranteReqDTO();
            req.setNome("Pizza Nova");

        RestauranteResDTO res = new RestauranteResDTO();
            res.setNome("Pizza Nova");

        when(repository.findById(1L))
            .thenReturn(Optional.of(restaurante));

        when(repository.findByNome("Pizza Nova"))
            .thenReturn(Optional.empty());

        when(repository.save(any(Restaurante.class)))
            .thenReturn(restaurante);

        when(modelMapper.map(any(Restaurante.class),
            eq(RestauranteResDTO.class)))
            .thenReturn(res);

        RestauranteResDTO resultado =
            service.atualizarRestaurante(1L, req);

        assertEquals("Pizza Nova", resultado.getNome());
    }
    
    @Test
    void deveLancarExcecaoAoAtualizarComNomeExistente() {

        Restaurante restaurante = new Restaurante();
        restaurante.setId(1L);
        restaurante.setNome("Pizza House");

        RestauranteReqDTO req = new RestauranteReqDTO();
        req.setNome("Pizza Nova");

        when(repository.findById(1L))
            .thenReturn(Optional.of(restaurante));

        when(repository.findByNome("Pizza Nova"))
            .thenReturn(Optional.of(new Restaurante()));

        assertThrows(
            BusinessException.class,
            () -> service.atualizarRestaurante(1L, req)
        );
    }

    @Test
    void deveCalcularTaxaEntrega() {

        Restaurante restaurante = new Restaurante();
        restaurante.setId(1L);
        restaurante.setTaxaEntrega(
            new BigDecimal("8.50"));

        when(repository.findById(1L))
            .thenReturn(Optional.of(restaurante));

        BigDecimal taxa =
            service.calcularTaxaEntrega(1L, "12345-678");

        assertEquals(
            new BigDecimal("8.50"),
            taxa
        );
    }

    @Test
    void deveBuscarPorCategoria() {

        Restaurante restaurante = new Restaurante();
        restaurante.setCategoria("Italiana");

        RestauranteResDTO dto =
            new RestauranteResDTO();

        when(repository.findByCategoriaAndAtivo(
            eq("Italiana"), eq(true), any(org.springframework.data.domain.Pageable.class)))
            .thenReturn(new org.springframework.data.domain.PageImpl<>(List.of(restaurante)));

        when(modelMapper.map(
            restaurante,
            RestauranteResDTO.class))
            .thenReturn(dto);

        List<RestauranteResDTO> resultado =
            service.buscarRestaurantesPorCategoria("Italiana");

        assertEquals(1, resultado.size());
    }

    @Test
    void deveBuscarRestaurantesProximos() {

        Restaurante restaurante = new Restaurante();

        RestauranteResDTO dto =
            new RestauranteResDTO();

        when(repository.findByAtivo(
            eq(true), any(org.springframework.data.domain.Pageable.class)))
            .thenReturn(new org.springframework.data.domain.PageImpl<>(List.of(restaurante)));

        when(modelMapper.map(
            restaurante,
            RestauranteResDTO.class))
            .thenReturn(dto);

        List<RestauranteResDTO> resultado =
            service.buscarRestaurantesProximos(
                "12345-678",
                10
            );

        assertEquals(1, resultado.size());
    }
}