package com.quiz.api.config;

import com.quiz.api.model.Pergunta;
import com.quiz.api.repository.PerguntaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);
    private final PerguntaRepository repository;

    public DataLoader(PerguntaRepository repository) {
        this.repository = repository;
    }

    @SuppressWarnings("null")
    @Override
    public void run(String... args) {
        if (repository.count() > 0) {
            log.info(" Banco já contém {} perguntas. Pulando inicialização.", repository.count());
            return;
        }

        log.info(" Carregando perguntas iniciais...");

        List<Pergunta> perguntas = Arrays.asList(
            criarPergunta(
                "Qual jogador marcou mais gols na história das Copas do Mundo?",
                Arrays.asList("Pelé", "Miroslav Klose", "Ronaldo Fenômeno", "Gerd Müller"),
                1
            ),
            criarPergunta(
                "Qual país sediou a primeira Copa do Mundo em 1930?",
                Arrays.asList("Brasil", "Itália", "Uruguai", "França"),
                2
            ),
            criarPergunta(
                "Qual a capital do Canadá?",
                Arrays.asList("Toronto", "Vancouver", "Montreal", "Ottawa"),
                3
            ),
            criarPergunta(
                "Qual seleção venceu a Copa do Mundo de 2014?",
                Arrays.asList("Argentina", "Alemanha", "Brasil", "Espanha"),
                1
            ),
            criarPergunta(
                "Qual é o recordista de prêmios Bola de Ouro (Ballon d'Or)?",
                Arrays.asList("Cristiano Ronaldo", "Lionel Messi", "Pelé", "Johan Cruyff"),
                1
            ),
            criarPergunta(
                "Qual é o ponto mais alto da Terra acima do nível do mar?",
                Arrays.asList("Monte Kilimanjaro", "Monte Everest", "K2", "Monte McKinley"),
                1
            ),
            criarPergunta(
                "Qual é o menor país do mundo em área territorial?",
                Arrays.asList("Mônaco", "Nauru", "Vaticano", "San Marino"),
                2
            )
        );

        repository.saveAll(perguntas);
        log.info(" {} perguntas carregadas com sucesso!", perguntas.size());
    }

    private Pergunta criarPergunta(String texto, List<String> alternativas, Integer correta) {
        Pergunta p = new Pergunta();
        p.setTexto(texto);
        p.setAlternativas(alternativas);
        p.setCorreta(correta);
        p.setImagem(null);
        return p;
    }
}