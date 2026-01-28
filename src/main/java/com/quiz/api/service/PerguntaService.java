package com.quiz.api.service;

import com.quiz.api.exception.ResourceNotFoundException;
import com.quiz.api.model.Pergunta;
import com.quiz.api.repository.PerguntaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PerguntaService {

    private static final Logger log = LoggerFactory.getLogger(PerguntaService.class);

    private final PerguntaRepository perguntaRepository;

    public PerguntaService(PerguntaRepository perguntaRepository) {
        this.perguntaRepository = perguntaRepository;
    }

    public Pergunta criar(Pergunta pergunta) {
        log.info("Criando nova pergunta: {}", pergunta.getTexto());
        return perguntaRepository.save(pergunta);
    }

    public List<Pergunta> listar() {
        log.info("Listando todas as perguntas");
        return perguntaRepository.findAll();
    }

    public Optional<Pergunta> obterPorId(Long id) {
        log.info("Obtendo pergunta por ID: {}", id);
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        return perguntaRepository.findById(id);
    }

    public Pergunta atualizar(Long id, Pergunta perguntaAtualizada) {
        log.info("Atualizando pergunta com ID: {}", id);
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        Pergunta pergunta = perguntaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pergunta não encontrada com ID: " + id));

        if (perguntaAtualizada.getTexto() != null) {
            pergunta.setTexto(perguntaAtualizada.getTexto());
        }
        if (perguntaAtualizada.getAlternativas() != null) {
            pergunta.setAlternativas(perguntaAtualizada.getAlternativas());
        }
        if (perguntaAtualizada.getCorreta() != null) {
            pergunta.setCorreta(perguntaAtualizada.getCorreta());
        }
        if (perguntaAtualizada.getImagem() != null) {
            pergunta.setImagem(perguntaAtualizada.getImagem());
        }

        if (pergunta != null) {
            return perguntaRepository.save(pergunta);
        }
        throw new ResourceNotFoundException("Pergunta não pode ser nula");
    }

    public void deletar(Long id) {
        log.info("Deletando pergunta com ID: {}", id);
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        if (!perguntaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pergunta não encontrada com ID: " + id);
        }
        perguntaRepository.deleteById(id);
    }

    public List<Pergunta> obterPorCategoria(String categoria) {
        log.info("Obtendo perguntas da categoria: {}", categoria);
        return listar();
    }

    public List<Pergunta> obterPorDificuldade(String dificuldade) {
        log.info("Obtendo perguntas da dificuldade: {}", dificuldade);
        return listar();
    }

    public boolean validarResposta(Long perguntaId, Integer alternativa) {
        log.info("Validando resposta da pergunta {}: alternativa {}", perguntaId, alternativa);
        if (perguntaId == null) {
            throw new IllegalArgumentException("ID da pergunta não pode ser nulo");
        }
        Pergunta pergunta = perguntaRepository.findById(perguntaId)
                .orElseThrow(() -> new ResourceNotFoundException("Pergunta não encontrada com ID: " + perguntaId));

        return pergunta.getCorreta().equals(alternativa);
    }

    public long contar() {
        log.info("Contando total de perguntas");
        return perguntaRepository.count();
    }
}
