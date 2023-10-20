package com.paulomarchon.testepratico.unidade;

import com.paulomarchon.testepratico.AbstractTestcontainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UnidadeRepositoryTest extends AbstractTestcontainers {

    @Autowired
    private UnidadeRepository emTeste;
    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    void setUp(){
        emTeste.deleteAll();
        System.out.println(applicationContext.getBeanDefinitionCount());
    }

    @Test
    void unidadeComNomeJaCadastrado() {
        //Given
        String nomeUnidade = "UNIDADE DE GERENCIAMENTO";
        String siglaUnidade = "UG";
        Unidade unidade = new Unidade(nomeUnidade, siglaUnidade);

        emTeste.save(unidade);

        //When
        var atual = emTeste.existsUnidadeByNome(nomeUnidade);

        //Then
        assertThat(atual).isTrue();
    }

    @Test
    void unidadeComNomeNaoCadastrado() {
        //Given
        String nomeUnidade = "UNIDADE DE MONITORAMENTO";
        //When
        var atual = emTeste.existsUnidadeByNome(nomeUnidade);
        //Then
        assertThat(atual).isFalse();
    }
}
