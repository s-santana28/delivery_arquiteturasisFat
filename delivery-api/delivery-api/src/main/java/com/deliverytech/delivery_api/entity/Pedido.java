package com.deliverytech.delivery_api.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import com.deliverytech.delivery_api.enums.StatusPedido;


@Entity
@Data
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime dataPedido;
    private Boolean entrega;
    private BigDecimal subTotal;
    private BigDecimal taxaEntrega;
    private BigDecimal valorTotal;
    private String numeroPedido;


    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "restaurante_id")
    private Restaurante restaurante;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens;
}
