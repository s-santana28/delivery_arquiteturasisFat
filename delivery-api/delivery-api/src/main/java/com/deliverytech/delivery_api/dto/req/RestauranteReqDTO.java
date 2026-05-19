package com.deliverytech.delivery_api.dto.req;
 
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
 
public class RestauranteReqDTO {
 
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String nome;
 
    @NotBlank(message = "Categoria é obrigatória")
    private String categoria;
 
    @NotBlank(message = "Endereço é obrigatório")
    @Size(max = 200, message = "Endereço deve ter no máximo 200 caracteres")
    private String endereco;
 
    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve ter 10 ou 11 dígitos")
    private String telefone;
 
    @NotNull(message = "Taxa de entrega é obrigatória")
    @DecimalMin(value = "0.0", message = "Taxa de entrega deve ser positiva")
    private BigDecimal taxaEntrega;
 
    @NotNull(message = "Tempo de entrega é obrigatório")
    @Min(value = 10, message = "Tempo mínimo é 10 minutos")
    @Max(value = 120, message = "Tempo máximo é 120 minutos")
    private Integer tempoEntrega;
 
    @NotBlank(message = "Horário de funcionamento é obrigatório")
    private String horarioFuncionamento;
 
    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
 
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
 
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
 
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
 
    public BigDecimal getTaxaEntrega() { return taxaEntrega; }
    public void setTaxaEntrega(BigDecimal taxaEntrega) { this.taxaEntrega = taxaEntrega; }
 
    public Integer getTempoEntrega() { return tempoEntrega; }
    public void setTempoEntrega(Integer tempoEntrega) { this.tempoEntrega = tempoEntrega; }
 
    public String getHorarioFuncionamento() { return horarioFuncionamento; }
    public void setHorarioFuncionamento(String horarioFuncionamento) { this.horarioFuncionamento = horarioFuncionamento; }
}