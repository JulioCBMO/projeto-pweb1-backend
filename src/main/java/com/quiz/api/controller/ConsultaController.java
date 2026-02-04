package com.quiz.api.controller;

import com.quiz.api.model.Pergunta;
import com.quiz.api.service.ConsultaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/consultas")
@CrossOrigin(origins = "*")
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

}
