package com.deliverytech.delivery_api.service.impl;

import com.deliverytech.delivery_api.dto.req.RestauranteReqDTO;
import com.deliverytech.delivery_api.dto.res.RestauranteResDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.util.List;

public interface RestauranteService {

    RestauranteResDTO cadastrarRestaurante(RestauranteReqDTO dto);

    RestauranteResDTO buscarPorId(Long id);

    RestauranteResDTO atualizarRestaurante(Long id, RestauranteReqDTO dto);

    RestauranteResDTO alterarStatusRestaurante(Long id);

    Page<RestauranteResDTO> listarRestaurantes(String categoria, Boolean ativo, Pageable pageable);

    List<RestauranteResDTO> buscarRestaurantesPorCategoria(String categoria);

    BigDecimal calcularTaxaEntrega(Long id, String cep);

    List<RestauranteResDTO> buscarRestaurantesProximos(String cep, Integer raio);
}