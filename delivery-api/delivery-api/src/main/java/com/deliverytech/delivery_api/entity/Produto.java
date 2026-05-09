package com.deliverytech.delivery_api.entity;
import jakarta.persistence.*;
import lombok.Data;
 
@Entity
@Data
public class Produto {
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String descricao;
    private double preco;
    private String categoria;
    private boolean disponivel;
 
    @ManyToOne
    @JoinColumn(name = "restaurante_id")
    private Restaurante restaurante;
}
