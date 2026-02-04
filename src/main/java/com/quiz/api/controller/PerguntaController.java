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
@CrossOrigin(origins = "https://automatic-potato-v66v755v4g6w3p94v-4200.app.github.dev",
    allowedMethods = {"GET","POST","PUT","DELETE","OPTIONS"},
    allowedHeaders = "*",
    allowCredentials = "true"
)
public class PerguntaController {

    private final PerguntaService perguntaService;

    public PerguntaController(PerguntaService perguntaService) {
        this.perguntaService = perguntaService;
    }

    @GetMapping
    public ResponseEntity<List<Pergunta>> listar() {
        List<Pergunta> perguntas = perguntaService.listar();
        return ResponseEntity.ok(perguntas);
}


    @PostMapping
    public ResponseEntity<Pergunta> criar(@Valid @RequestBody Pergunta pergunta) {
        Pergunta criada = perguntaService.criar(pergunta);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
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
}