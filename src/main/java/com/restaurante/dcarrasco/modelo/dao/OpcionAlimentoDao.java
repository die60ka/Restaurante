package com.restaurante.dcarrasco.modelo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.restaurante.dcarrasco.exception.ObjetoNoExistenteEsception;
import com.restaurante.dcarrasco.modelo.Carne;
import com.restaurante.dcarrasco.modelo.Ensalada;
import com.restaurante.dcarrasco.modelo.Jugo;
import com.restaurante.dcarrasco.modelo.Principio;
import com.restaurante.dcarrasco.modelo.Sopa;
import com.restaurante.dcarrasco.util.JDBCUtilities;

public class OpcionAlimentoDao {

    public List<Sopa> listarSopas() throws SQLException {
        var respuesta = new ArrayList<Sopa>();

        var statement = JDBCUtilities.getConnection().createStatement();
        var rset = statement.executeQuery("SELECT * FROM Sopa");
        while (rset.next()) {
            var opcion = new Sopa(rset.getString("nombre"));
            opcion.setId(rset.getInt("id"));

            respuesta.add(opcion);
        }
        rset.close();
        statement.close();

        return respuesta;
    }

    public List<Principio> listarPrincipio() throws SQLException {
        var respuesta = new ArrayList<Principio>();

        var statement = JDBCUtilities.getConnection().createStatement();
        var rset = statement.executeQuery("SELECT * FROM Principio");
        while (rset.next()) {
            var opcion = new Principio(rset.getString("nombre"));
            opcion.setId(rset.getInt("id"));

            respuesta.add(opcion);
        }
        rset.close();
        statement.close();

        return respuesta;
    }

    public List<Carne> listarCarnes() throws SQLException {
        var respuesta = new ArrayList<Carne>();

        var statement = JDBCUtilities.getConnection().createStatement();
        var rset = statement.executeQuery("SELECT * FROM Carne");
        while (rset.next()) {
            var opcion = new Carne(rset.getString("nombre"));
            opcion.setId(rset.getInt("id"));

            respuesta.add(opcion);
        }
        rset.close();
        statement.close();

        return respuesta;
    }

    public List<Ensalada> listarEnsaladas() throws SQLException {
        var respuesta = new ArrayList<Ensalada>();

        var statement = JDBCUtilities.getConnection().createStatement();
        var rset = statement.executeQuery("SELECT * FROM Ensalada");
        while (rset.next()) {
            var opcion = new Ensalada(rset.getString("nombre"));
            opcion.setId(rset.getInt("id"));

            respuesta.add(opcion);
        }
        rset.close();
        statement.close();

        return respuesta;
    }

    public List<Jugo> listarJugos() throws SQLException {
        var respuesta = new ArrayList<Jugo>();

        var statement = JDBCUtilities.getConnection().createStatement();
        var rset = statement.executeQuery("SELECT * FROM Jugo");
        while (rset.next()) {
            var opcion = new Jugo(rset.getString("nombre"));
            opcion.setId(rset.getInt("id"));

            respuesta.add(opcion);
        }
        rset.close();
        statement.close();

        return respuesta;
    }

    public Sopa consultarSopa(Integer id) throws ObjetoNoExistenteEsception, SQLException {
        Sopa respuesta = null;

        PreparedStatement statement = null;
        ResultSet rset = null;
        try {
            statement = JDBCUtilities.getConnection()
                    .prepareStatement("SELECT * FROM Sopa WHERE id = ?;");
            statement.setInt(1, id);
            rset = statement.executeQuery();
            if (rset.next()) {
                respuesta = new Sopa(rset.getString("nombre"));
                respuesta.setId(rset.getInt("id"));
            } else {
                throw new ObjetoNoExistenteEsception("No existe la sopa con Id proporcionado");
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

    public Principio consultarPrincipio(Integer id) throws ObjetoNoExistenteEsception, SQLException {
        Principio respuesta = null;

        PreparedStatement statement = null;
        ResultSet rset = null;
        try {
            statement = JDBCUtilities.getConnection()
                    .prepareStatement("SELECT * FROM Principio WHERE id = ?;");
            statement.setInt(1, id);
            rset = statement.executeQuery();
            if (rset.next()) {
                respuesta = new Principio(rset.getString("nombre"));
                respuesta.setId(rset.getInt("id"));
            } else {
                throw new ObjetoNoExistenteEsception("No existe el principio con Id proporcionado");
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

    public Carne consultarCarne(Integer id) throws ObjetoNoExistenteEsception, SQLException {
        Carne respuesta = null;

        PreparedStatement statement = null;
        ResultSet rset = null;
        try {
            statement = JDBCUtilities.getConnection()
                    .prepareStatement("SELECT * FROM Carne WHERE id = ?;");
            statement.setInt(1, id);
            rset = statement.executeQuery();
            if (rset.next()) {
                respuesta = new Carne(rset.getString("nombre"));
                respuesta.setId(rset.getInt("id"));
            } else {
                throw new ObjetoNoExistenteEsception("No existe la carne con Id proporcionado");
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

    public Ensalada consultarEnsalada(Integer id) throws ObjetoNoExistenteEsception, SQLException {
        Ensalada respuesta = null;

        PreparedStatement statement = null;
        ResultSet rset = null;
        try {
            statement = JDBCUtilities.getConnection()
                    .prepareStatement("SELECT * FROM Ensalada WHERE id = ?;");
            statement.setInt(1, id);
            rset = statement.executeQuery();
            if (rset.next()) {
                respuesta = new Ensalada(rset.getString("nombre"));
                respuesta.setId(rset.getInt("id"));
            } else {
                throw new ObjetoNoExistenteEsception("No existe la ensalada con Id proporcionado");
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

    public Jugo consultarJugo(Integer id) throws ObjetoNoExistenteEsception, SQLException {
        Jugo respuesta = null;

        PreparedStatement statement = null;
        ResultSet rset = null;
        try {
            statement = JDBCUtilities.getConnection()
                    .prepareStatement("SELECT * FROM Jugo WHERE id = ?;");
            statement.setInt(1, id);
            rset = statement.executeQuery();
            if (rset.next()) {
                respuesta = new Jugo(rset.getString("nombre"));
                respuesta.setId(rset.getInt("id"));
            } else {
                throw new ObjetoNoExistenteEsception("No existe la jugo con Id proporcionado");
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

}
