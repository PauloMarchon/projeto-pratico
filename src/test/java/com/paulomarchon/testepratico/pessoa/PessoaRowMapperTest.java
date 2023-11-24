package com.paulomarchon.testepratico.pessoa;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
public class PessoaRowMapperTest {

    @Test
    void mapRow() throws SQLException {

        //Given
        PessoaRowMapper pessoaRowMapper = new PessoaRowMapper();

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("pes_id")).thenReturn(1);
        when(resultSet.getString("pes_nome")).thenReturn("RONALDO");
        when(resultSet.getObject("pes_data_nascimento", LocalDate.class)).thenReturn(LocalDate.of(1980, 6, 21));
        when(resultSet.getString("pes_sexo")).thenReturn("MASCULINO");
        when(resultSet.getString("pes_mae")).thenReturn("AMELIA");
        when(resultSet.getString("pes_pai")).thenReturn("LUIZ");

        //When
        Pessoa atual = pessoaRowMapper.mapRow(resultSet, 1);

        //Then
        Pessoa esperado = new Pessoa(
                1,
                "RONALDO",
                LocalDate.of(1980, 6, 21),
                "MASCULINO",
                "AMELIA",
                "LUIZ"
        );
        assertThat(atual).isEqualTo(esperado);
    }
}
