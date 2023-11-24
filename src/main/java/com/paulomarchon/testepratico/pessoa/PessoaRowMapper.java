package com.paulomarchon.testepratico.pessoa;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Component
public class PessoaRowMapper implements RowMapper<Pessoa> {
    @Override
    public Pessoa mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Pessoa(
                rs.getInt("pes_id"),
                rs.getString("pes_nome"),
                rs.getObject("pes_data_nascimento", LocalDate.class),
                rs.getString("pes_sexo"),
                rs.getString("pes_mae"),
                rs.getString("pes_pai")
        );
    }
}
