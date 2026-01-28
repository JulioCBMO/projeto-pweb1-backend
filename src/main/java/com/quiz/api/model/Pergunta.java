package com.quiz.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Entidade que representa uma pergunta do quiz
 */
@Entity
@Table(name = "perguntas")
public class Pergunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O texto da pergunta é obrigatório")
    @Size(min = 10, max = 500, message = "O texto deve ter entre 10 e 500 caracteres")
    @Column(nullable = false, length = 500)
    private String texto;

    @NotNull(message = "As alternativas são obrigatórias")
    @Size(min = 2, max = 6, message = "Deve haver entre 2 e 6 alternativas")
    @ElementCollection
    @CollectionTable(name = "alternativas", joinColumns = @JoinColumn(name = "pergunta_id"))     
    @Column(name = "alternativa", nullable = false)
    private List<String> alternativas;

    @NotNull(message = "O índice da resposta correta é obrigatório")
    @Min(value = 0, message = "O índice deve ser maior ou igual a 0")
    @Column(nullable = false)
    private Integer correta;

    @Column(length = 1000)
    private String imagem;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public Pergunta() {}

    public Pergunta(String texto, List<String> alternativas, Integer correta, String imagem) {
        this.texto = texto;
        this.alternativas = alternativas;
        this.correta = correta;
        this.imagem = imagem;
    }

    public Pergunta(Long id, String texto, List<String> alternativas, Integer correta, String imagem) {
        this.id = id;
        this.texto = texto;
        this.alternativas = alternativas;
        this.correta = correta;
        this.imagem = imagem;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getTexto() {
        return texto;
    }

    public List<String> getAlternativas() {
        return alternativas;
    }

    public Integer getCorreta() {
        return correta;
    }

    public String getImagem() {
        return imagem;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setAlternativas(List<String> alternativas) {
        this.alternativas = alternativas;
    }

    public void setCorreta(Integer correta) {
        this.correta = correta;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Define a data de criação automaticamente
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    /**
     * Atualiza a data de modificação automaticamente
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /**
     * Validação customizada: verifica se o índice da resposta correta é válido
     */
    @PostLoad
    @PostPersist
    @PostUpdate
    private void validarIndiceCorreto() {
        if (alternativas != null && correta != null) {
            if (correta < 0 || correta >= alternativas.size()) {
                throw new IllegalStateException(
                    "Índice da resposta correta inválido: " + correta +
                    ". Deve estar entre 0 e " + (alternativas.size() - 1)
                );
            }
        }
    }
}
