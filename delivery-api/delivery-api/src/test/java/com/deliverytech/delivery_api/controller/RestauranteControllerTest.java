package com.deliverytech.delivery_api.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import com.deliverytech.delivery_api.dto.res.RestauranteResDTO;
import com.deliverytech.delivery_api.security.JwtUtil;
import com.deliverytech.delivery_api.service.AuthService;
import com.deliverytech.delivery_api.service.MetricsService;
import com.deliverytech.delivery_api.service.ProdutoService;
import com.deliverytech.delivery_api.service.RestauranteService;

@WebMvcTest(RestauranteController.class)
@AutoConfigureMockMvc(addFilters = false)
class RestauranteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestauranteService restauranteService;

    @MockBean
    private ProdutoService produtoService;

    @MockBean
    private MetricsService metricsService;
    @MockBean
    private AuthService authService;
    @MockBean
    private JwtUtil jwtUtil;

    @Test
    void deveBuscarRestaurantePorId() throws Exception {

        RestauranteResDTO dto = new RestauranteResDTO();
        dto.setId(1L);
        dto.setNome("Pizza House");

        when(restauranteService.buscarPorId(1L))
                .thenReturn(dto);

        mockMvc.perform(get("/api/restaurantes/1"))
                .andExpect(status().isOk());

        verify(restauranteService).buscarPorId(1L);
    }

    @Test
    void deveAlterarStatusRestaurante() throws Exception {

        RestauranteResDTO dto = new RestauranteResDTO();
        dto.setAtivo(false);

        when(restauranteService.alterarStatusRestaurante(1L))
                .thenReturn(dto);

        mockMvc.perform(
                patch("/api/restaurantes/1/status"))
                .andExpect(status().isOk());

        verify(restauranteService)
                .alterarStatusRestaurante(1L);
    }

    @Test
    void deveBuscarPorCategoria() throws Exception {

        when(restauranteService
                .buscarRestaurantesPorCategoria("Pizza"))
                .thenReturn(List.of(new RestauranteResDTO()));

        mockMvc.perform(
                get("/api/restaurantes/categoria/Pizza"))
                .andExpect(status().isOk());

        verify(restauranteService)
                .buscarRestaurantesPorCategoria("Pizza");
    }

    @Test
    void deveCalcularTaxaEntrega() throws Exception {

        when(restauranteService
                .calcularTaxaEntrega(1L, "12345678"))
                .thenReturn(BigDecimal.valueOf(5));

        mockMvc.perform(
                get("/api/restaurantes/1/taxa-entrega/12345678"))
                .andExpect(status().isOk());

        verify(restauranteService)
                .calcularTaxaEntrega(1L, "12345678");
    }
}



