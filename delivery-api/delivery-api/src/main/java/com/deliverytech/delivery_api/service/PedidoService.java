package com.deliverytech.delivery_api.service;

import com.deliverytech.delivery_api.dto.req.PedidoReqDTO;
import com.deliverytech.delivery_api.dto.res.PedidoResDTO;
import com.deliverytech.delivery_api.dto.ItemPedidoDTO;
import com.deliverytech.delivery_api.enums.StatusPedido;
import java.math.BigDecimal;
import java.util.List;

public interface PedidoService {

    PedidoResDTO criarPedido(PedidoReqDTO dto);

    PedidoResDTO buscarPedidoPorId(Long id);

    List<PedidoResDTO> buscarPedidosPorCliente(Long clienteId);

    PedidoResDTO atualizarStatusPedido(Long id, StatusPedido status);

    BigDecimal calcularTotalPedido(List<ItemPedidoDTO> itens);

    void cancelarPedido(Long id);
}