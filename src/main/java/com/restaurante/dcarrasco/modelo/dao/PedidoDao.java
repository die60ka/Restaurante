package com.restaurante.dcarrasco.modelo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.restaurante.dcarrasco.exception.ObjetoNoExistenteEsception;
import com.restaurante.dcarrasco.modelo.Bandeja;
import com.restaurante.dcarrasco.modelo.Completo;
import com.restaurante.dcarrasco.modelo.EstadoPedido;
import com.restaurante.dcarrasco.modelo.Mesa;
import com.restaurante.dcarrasco.modelo.OpcionPedido;
import com.restaurante.dcarrasco.modelo.Pedido;
import com.restaurante.dcarrasco.util.JDBCUtilities;

public class PedidoDao {

    private OpcionAlimentoDao alimentoDao;

    public PedidoDao() {
        alimentoDao = new OpcionAlimentoDao();
    }

    public void crear(Pedido pedido) throws SQLException {
        PreparedStatement pstmt = JDBCUtilities.getConnection()
            .prepareStatement(
                "INSERT INTO Pedido (id, cliente, estado, id_mesa, id_tipo_opcion, "
                + "id_sopa, id_principio, id_carne, id_ensalada, id_jugo)"
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

    pstmt.setInt(1, generarConsecutivo());
    pstmt.setString(2, pedido.getCliente());
    pstmt.setString(3, pedido.getEstado().toString());
    pstmt.setInt(4, pedido.getMesa().getId());
    pstmt.setInt(5, pedido.getOpcion().getId());
    if (pedido.getOpcion() instanceof Completo) {
        pstmt.setInt(6, ((Completo)pedido.getOpcion()).getSopa().getId());
    } else {
        pstmt.setNull(6, Types.INTEGER);
    }
    pstmt.setInt(7, pedido.getOpcion().getPrincipio().getId());
    pstmt.setInt(8, pedido.getOpcion().getCarne().getId());
    if (pedido.getOpcion().getEnsalada().getId() != null) {
        pstmt.setInt(9, pedido.getOpcion().getEnsalada().getId());
    } else {
        pstmt.setNull(9, Types.INTEGER);
    }
    pstmt.setInt(10, pedido.getOpcion().getJugo().getId());
    pstmt.executeUpdate();

    pstmt.close();
    }

    public List<Pedido> listarPedidos (Mesa mesa) throws SQLException, ObjetoNoExistenteEsception {
        List<Pedido> respuesta = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rset = null;
        try {
            pstmt = JDBCUtilities.getConnection().
                prepareStatement("SELECT * FROM Pedido WHERE id_mesa = ?;");
            pstmt.setInt(1, mesa.getId());
            rset = pstmt.executeQuery();
            while (rset.next()) {
                OpcionPedido opcion;
                if (rset.getInt("id_tipo_opcion") == 1) {
                    opcion = new Completo(12_000);

                    // Consultar la opcion de sopa
                    ((Completo)opcion).setSopa(alimentoDao.consultarSopa(rset.getInt("id_sopa")));
                } else {
                    opcion = new Bandeja(10_000);
                    
                }
                opcion.setPrincipio(alimentoDao.consultarPrincipio(rset.getInt("id_principio")));
                opcion.setCarne(alimentoDao.consultarCarne(rset.getInt("id_carne")));
                if (rset.getString("id_ensalada") != null) {
                    opcion.setEnsalada(alimentoDao.consultarEnsalada(rset.getInt("id_ensalada")));
                } 
                opcion.setJugo(alimentoDao.consultarJugo(rset.getInt("id_jugo")));

                var pedido = new Pedido(rset.getString("cliente"), opcion);
                pedido.setId(rset.getInt("id"));
                pedido.setEstado(EstadoPedido.valueOf(rset.getString("estado")));
                respuesta.add(pedido);
            }
        } finally {
            if (rset != null) {
                rset.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return respuesta;
    }

    private Integer generarConsecutivo() throws SQLException {
        var respuesta = 0;

        var statement = JDBCUtilities.getConnection().createStatement();
        var rset = statement.executeQuery("SELECT MAX(id) AS id FROM Pedido");
        if (rset.next()) {
            respuesta = rset.getInt("id");
        }
        respuesta++;
        rset.close();
        statement.close();

        return respuesta;
    }

    public void borrarPedidosMesa(Mesa mesa) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = JDBCUtilities.getConnection().
                prepareStatement("DELETE FROM Pedido WHERE id_mesa = ?;");
            pstmt.setInt(1, mesa.getId());
            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    public void actualizarEstadoPedido(Pedido pedido) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = JDBCUtilities.getConnection().
                prepareStatement("UPDATE Pedido SET estado = ? WHERE id = ?;");
            pstmt.setString(1, pedido.getEstado().toString());
            pstmt.setInt(2, pedido.getId());
            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }
}