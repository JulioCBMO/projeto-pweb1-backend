package com.quiz.api.controller;

import com.quiz.api.model.Pergunta;
import com.quiz.api.service.ConsultaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://automatic-potato-v66v755v4g6w3p94v-4200.app.github.dev")
@RequestMapping("/api/consultas")
@CrossOrigin(origins = "https://automatic-potato-v66v755v4g6w3p94v-4200.app.github.dev",
    allowedMethods = {"GET","POST","PUT","DELETE","OPTIONS"},
    allowedHeaders = "*",
    allowCredentials = "true"
)
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @GetMapping("/perguntas") 
    public ResponseEntity<List<Pergunta>> listarTodas() {
        List<Pergunta> perguntas = consultaService.findAll();
        return ResponseEntity.ok(perguntas);
    }

    @GetMapping("/perguntas/{id}")
    public ResponseEntity<Pergunta> consultarPorId(@PathVariable Long id) {
        return consultaService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}