package es.dwes.dentista.dao;

import es.dwes.dentista.model.Odontologo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OdontologoDAO {
    public List<Odontologo> listar() throws SQLException {
        List<Odontologo> odontologos = new ArrayList<>();
        String sql = "SELECT id_odontologo, nombre, especialidad FROM odontologos ORDER BY nombre";
        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql);
             ResultSet resultados = sentencia.executeQuery()) {
            while (resultados.next()) {
                Odontologo odontologo = new Odontologo();
                odontologo.setId(resultados.getInt("id_odontologo"));
                odontologo.setNombre(resultados.getString("nombre"));
                odontologo.setEspecialidad(resultados.getString("especialidad"));
                odontologos.add(odontologo);
            }
        }
        return odontologos;
    }
}