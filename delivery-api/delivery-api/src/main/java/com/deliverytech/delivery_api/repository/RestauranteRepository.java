package com.deliverytech.delivery_api.repository;

import com.deliverytech.delivery_api.entity.Restaurante;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

    // Buscar por nome
    Optional<Restaurante> findByNome(String nome);

    // Buscar restaurantes ativos
    Page<Restaurante> findByAtivo(Boolean ativo, Pageable pageable);

    // Buscar por categoria
    Page<Restaurante> findByCategoriaAndAtivo(
        String categoria,
        Boolean ativo,
        Pageable pageable);

    Page<Restaurante> findByCategoria(
        String categoria,
        Pageable pageable);
    // Buscar por nome contendo (case insensitive)
    List<Restaurante> findByNomeContainingIgnoreCaseAndAtivoTrue(String nome);

    // Buscar por avaliação mínima
    List<Restaurante> findByAvaliacaoGreaterThanEqualAndAtivoTrue(Integer avaliacao);

    // Ordenar por avaliação (descendente)
    List<Restaurante> findByAtivoTrueOrderByAvaliacaoDesc();

    // Query customizada - restaurantes com produtos
    @Query("SELECT DISTINCT r FROM Restaurante r JOIN r.produtos p WHERE r.ativo = true")
    List<Restaurante> findRestaurantesComProdutos();

    // Buscar por faixa de taxa de entrega
    @Query("SELECT r FROM Restaurante r WHERE r.taxaEntrega BETWEEN :min AND :max AND r.ativo = true")
    List<Restaurante> findByTaxaEntregaBetween(@Param("min") BigDecimal min, @Param("max") BigDecimal max);

    // Categorias disponíveis
    @Query("SELECT DISTINCT r.categoria FROM Restaurante r WHERE r.ativo = true ORDER BY r.categoria")
    List<String> findCategoriasDisponiveis();
}