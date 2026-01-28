package com.quiz.api.service;

import com.quiz.api.model.Pergunta;
import com.quiz.api.repository.PerguntaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ConsultaService {

    private static final Logger log = LoggerFactory.getLogger(ConsultaService.class);

    private final PerguntaRepository perguntaRepository;

    public ConsultaService(PerguntaRepository perguntaRepository) {
        this.perguntaRepository = perguntaRepository;
    }

    public List<Pergunta> findAll() {
        log.info("Buscando todas as perguntas");
        return perguntaRepository.findAll();
    }

    public Optional<Pergunta> findById(Long id) {
        log.info("Buscando pergunta por ID: {}", id);
        if (id == null) {
            return Optional.empty();
        }
        return perguntaRepository.findById(id);
    }

    public List<Pergunta> findByCategoria(String categoria) {
        log.info("Buscando perguntas por categoria: {}", categoria);
        return findAll().stream()
                .collect(Collectors.toList());
    }

    public List<Pergunta> findByDificuldade(String dificuldade) {
        log.info("Buscando perguntas por dificuldade: {}", dificuldade);
        return findAll().stream()
                .collect(Collectors.toList());
    }

    public Optional<Pergunta> getRandomQuestion() {
        log.info("Obtendo pergunta aleatória");
        List<Pergunta> todas = findAll();
        if (todas.isEmpty()) {
            return Optional.empty();
        }
        int index = new Random().nextInt(todas.size());
        return Optional.of(todas.get(index));
    }

    public List<Pergunta> getMultipleRandomQuestions(Integer quantidade) {
        log.info("Obtendo {} perguntas aleatórias", quantidade);
        List<Pergunta> todas = findAll();
        if (todas.size() <= quantidade) {
            return todas;
        }

        Collections.shuffle(todas);
        return todas.subList(0, quantidade);
    }

    public ResponseEntity<Map<String, Object>> validarResposta(Long perguntaId, Integer respostaUsuario) {
        log.info("Validando resposta para pergunta {}: {}", perguntaId, respostaUsuario);
        Optional<Pergunta> pergunta = findById(perguntaId);

        if (pergunta.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Pergunta p = pergunta.get();
        boolean correta = Objects.equals(p.getCorreta(), respostaUsuario);

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("perguntaId", perguntaId);
        resultado.put("alternativaSelecionada", respostaUsuario);
        resultado.put("alternativaCorreta", p.getCorreta());
        resultado.put("correta", correta);
        resultado.put("mensagem", correta ? "Resposta correta!" : "Resposta incorreta!");

        return ResponseEntity.ok(resultado);
    }

    public Map<String, Object> getEstatisticas() {
        log.info("Obtendo estatísticas gerais");
        List<Pergunta> todas = findAll();

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalPerguntas", todas.size());

        return stats;
    }

    public Map<String, Integer> getEstatisticasPorCategoria() {
        log.info("Obtendo estatísticas por categoria");
        return new HashMap<>();
    }

    public Map<String, Integer> getEstatisticasPorDificuldade() {
        log.info("Obtendo estatísticas por dificuldade");
        return new HashMap<>();
    }

    public List<Pergunta> pesquisarPorTexto(String termo) {
        log.info("Pesquisando perguntas com termo: {}", termo);
        String termoLower = termo.toLowerCase();
        return findAll().stream()
                .filter(p -> p.getTexto() != null && p.getTexto().toLowerCase().contains(termoLower))
                .collect(Collectors.toList());
    }

    public long countAll() {
        log.info("Contando total de perguntas");
        return perguntaRepository.count();
    }

    public long countByCategoria(String categoria) {
        log.info("Contando perguntas da categoria: {}", categoria);
        return findByCategoria(categoria).size();
    }
}
