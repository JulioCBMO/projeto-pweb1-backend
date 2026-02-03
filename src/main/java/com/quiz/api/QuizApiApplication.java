package com.quiz.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuizApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuizApiApplication.class, args);
        
        System.out.println("\n===========================================");
        System.out.println(" Quiz API iniciada com sucesso!");
        System.out.println("===========================================");
        System.out.println(" Servidor rodando em: http://localhost:8080");
        System.out.println(" Console H2: http://localhost:8080/h2-console");
        System.out.println("===========================================\n");
        System.out.println(" Endpoints disponíveis:");
        System.out.println("\n--- MÓDULO 1: Cadastramento/Remoção ---");
        System.out.println("POST   /api/perguntas");
        System.out.println("PUT    /api/perguntas/{id}");
        System.out.println("DELETE /api/perguntas/{id}");
        System.out.println("\n--- MÓDULO 2: Consultas ---");
        System.out.println("GET    /api/consultas/perguntas");
        System.out.println("GET    /api/consultas/perguntas/{id}");
        System.out.println("===========================================\n");
    }
}