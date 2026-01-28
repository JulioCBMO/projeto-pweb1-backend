package com.quiz.api.repository;

import com.quiz.api.model.Pergunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository para operações de banco de dados com Pergunta
 */
@Repository
public interface PerguntaRepository extends JpaRepository<Pergunta, Long> {

    /**
     * Busca perguntas que contenham o texto especificado (case-insensitive)
     */
    List<Pergunta> findByTextoContainingIgnoreCase(String texto);

    /**
     * Busca perguntas aleatórias (útil para gerar quizzes variados)
     */
    @Query(value = "SELECT * FROM perguntas ORDER BY RANDOM() LIMIT ?1", nativeQuery = true)
    List<Pergunta> findRandomPerguntas(int limit);

    /**
     * Conta o total de perguntas no banco
     */
    @Query("SELECT COUNT(p) FROM Pergunta p")
    long countTotalPerguntas();
}