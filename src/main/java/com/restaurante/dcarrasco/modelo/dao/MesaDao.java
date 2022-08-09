package com.restaurante.dcarrasco.modelo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.restaurante.dcarrasco.exception.ObjetoNoExistenteEsception;
import com.restaurante.dcarrasco.modelo.Mesa;
import com.restaurante.dcarrasco.util.JDBCUtilities;

public class MesaDao {

    public List<Mesa> listar() throws SQLException {
        var respuesta = new ArrayList<Mesa>();

        var statement = JDBCUtilities.getConnection().createStatement();
        var rset = statement.executeQuery("SELECT * FROM Mesa");
        while (rset.next()) {
            var mesa = new Mesa(rset.getString("numero"));
            mesa.setId(rset.getInt("id"));

            respuesta.add(mesa);
        }
        rset.close();
        statement.close();

        return respuesta;
    }

    public Mesa consultar(String numero) throws SQLException, ObjetoNoExistenteEsception {
        Mesa respuesta = null;
        PreparedStatement statement = null;
        ResultSet rset = null;
        try {
            statement = JDBCUtilities.getConnection().prepareStatement("SELECT * FROM MESA WHERE numero = ?;");
            statement.setString(1, numero);
            rset = statement.executeQuery();
            if (rset.next()) {
                respuesta = new Mesa(rset.getString("numero"));
                respuesta.setId(rset.getInt("id"));
            } else {
                throw new ObjetoNoExistenteEsception("No hay mesa con número " + numero);
            }
        } finally {
            if (rset != null) {
                rset.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
        return respuesta;
    }
    

    public void crear(Mesa mesa) throws SQLException {
        var statement = JDBCUtilities.getConnection().prepareStatement("INSERT INTO Mesa (id, numero) VALUES (?, ?);");
        statement.setInt(1, generarConsecutivo());
        statement.setString(2, mesa.getNumero());
        statement.executeUpdate();
        statement.close();
    }

    private Integer generarConsecutivo() throws SQLException {
        var respuesta = 0;

        var statement = JDBCUtilities.getConnection().createStatement();
        var rset = statement.executeQuery("SELECT MAX(id) AS id FROM Mesa");
        if (rset.next()) {
            respuesta = rset.getInt("id");
        }
        respuesta++;
        rset.close();
        statement.close();

        return respuesta;
    }
}