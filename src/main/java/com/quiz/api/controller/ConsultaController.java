package com.quiz.api.controller;

import com.quiz.api.model.Pergunta;
import com.quiz.api.service.ConsultaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/consultas")
@CrossOrigin(origins = "http://localhost:4200")
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @GetMapping
    public ResponseEntity<List<Pergunta>> listarTodas() {
        List<Pergunta> perguntas = consultaService.findAll();
        return ResponseEntity.ok(perguntas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pergunta> consultarPorId(@PathVariable Long id) {
        return consultaService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/filtro/categoria")
    public ResponseEntity<List<Pergunta>> filtrarPorCategoria(@RequestParam String categoria) {
        List<Pergunta> perguntas = consultaService.findByCategoria(categoria);
        return ResponseEntity.ok(perguntas);
    }

    @GetMapping("/filtro/dificuldade")
    public ResponseEntity<List<Pergunta>> filtrarPorDificuldade(@RequestParam String dificuldade) {
        List<Pergunta> perguntas = consultaService.findByDificuldade(dificuldade);
        return ResponseEntity.ok(perguntas);
    }

    @GetMapping("/random")
    public ResponseEntity<Pergunta> obterAleatoria() {
        return consultaService.getRandomQuestion()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/random/multiple")
    public ResponseEntity<List<Pergunta>> obterMultiplasAleatorias(@RequestParam(defaultValue = "10") Integer quantidade) {
        List<Pergunta> perguntas = consultaService.getMultipleRandomQuestions(quantidade);
        return ResponseEntity.ok(perguntas);
    }

    @PostMapping("/validar")
    public ResponseEntity<Map<String, Object>> validarResposta(
            @RequestParam Long perguntaId,
            @RequestParam Integer respostaUsuario) {
        return consultaService.validarResposta(perguntaId, respostaUsuario);
    }

    @GetMapping("/estatisticas")
    public ResponseEntity<Map<String, Object>> obterEstatisticas() {
        Map<String, Object> stats = consultaService.getEstatisticas();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/estatisticas/categoria")
    public ResponseEntity<Map<String, Integer>> estatisticasPorCategoria() {
        Map<String, Integer> stats = consultaService.getEstatisticasPorCategoria();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/estatisticas/dificuldade")
    public ResponseEntity<Map<String, Integer>> estatisticasPorDificuldade() {
        Map<String, Integer> stats = consultaService.getEstatisticasPorDificuldade();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Pergunta>> pesquisar(@RequestParam String termo) {
        List<Pergunta> resultado = consultaService.pesquisarPorTexto(termo);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> contagemTotal() {
        long total = consultaService.countAll();
        return ResponseEntity.ok(Map.of("total", total));
    }

    @GetMapping("/count/categoria")
    public ResponseEntity<Map<String, Object>> contagemPorCategoria(@RequestParam String categoria) {
        long count = consultaService.countByCategoria(categoria);
        return ResponseEntity.ok(Map.of("categoria", categoria, "quantidade", count));
    }
}
