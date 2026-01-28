package com.quiz.api.controller;

import com.quiz.api.model.Pergunta;
import com.quiz.api.service.PerguntaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/perguntas")
public class PerguntaController {

    private final PerguntaService perguntaService;

    public PerguntaController(PerguntaService perguntaService) {
        this.perguntaService = perguntaService;
    }

    @PostMapping
    public ResponseEntity<Pergunta> criar(@Valid @RequestBody Pergunta pergunta) {
        Pergunta criada = perguntaService.criar(pergunta);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }

    @GetMapping
    public ResponseEntity<List<Pergunta>> listar() {
        List<Pergunta> perguntas = perguntaService.listar();
        return ResponseEntity.ok(perguntas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pergunta> obterPorId(@PathVariable Long id) {
        return perguntaService.obterPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pergunta> atualizar(@PathVariable Long id, @Valid @RequestBody Pergunta perguntaAtualizada) {
        try {
            Pergunta atualizada = perguntaService.atualizar(id, perguntaAtualizada);
            return ResponseEntity.ok(atualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            perguntaService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Pergunta>> obterPorCategoria(@PathVariable String categoria) {
        List<Pergunta> perguntas = perguntaService.obterPorCategoria(categoria);
        return ResponseEntity.ok(perguntas);
    }

    @GetMapping("/dificuldade/{dificuldade}")
    public ResponseEntity<List<Pergunta>> obterPorDificuldade(@PathVariable String dificuldade) {
        List<Pergunta> perguntas = perguntaService.obterPorDificuldade(dificuldade);
        return ResponseEntity.ok(perguntas);
    }

    @PostMapping("/{id}/validar")
    public ResponseEntity<Map<String, Object>> validarResposta(
            @PathVariable Long id,
            @RequestParam Integer alternativa) {
        try {
            boolean correta = perguntaService.validarResposta(id, alternativa);
            return ResponseEntity.ok(Map.of(
                    "perguntaId", id,
                    "alternativaSelecionada", alternativa,
                    "correta", correta
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> contar() {
        long total = perguntaService.contar();
        return ResponseEntity.ok(Map.of("total", total));
    }
}
