package es.dwes.dentista.dao;

import es.dwes.dentista.model.Cita;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CitaDAO {
    public List<Cita> listar(boolean todas) throws SQLException {
        String sql = "SELECT c.id_cita, c.fecha, c.hora, c.id_paciente, c.id_odontologo, c.motivo, "
                + "p.nombre AS nombre_paciente, p.apellidos AS apellidos_paciente, o.nombre AS nombre_odontologo "
                + "FROM cita c JOIN paciente p ON p.id_paciente = c.id_paciente "
                + "JOIN odontologos o ON o.id_odontologo = c.id_odontologo "
                + (todas ? "" : "WHERE c.fecha >= CURDATE() ") + "ORDER BY c.fecha, c.hora";
        List<Cita> citas = new ArrayList<>();
        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql);
             ResultSet resultados = sentencia.executeQuery()) {
            while (resultados.next()) {
                Cita cita = new Cita();
                cita.setId(resultados.getInt("id_cita"));
                cita.setFecha(resultados.getObject("fecha", java.time.LocalDate.class));
                cita.setHora(resultados.getObject("hora", java.time.LocalTime.class));
                cita.setIdPaciente(resultados.getInt("id_paciente"));
                cita.setIdOdontologo(resultados.getInt("id_odontologo"));
                cita.setMotivo(resultados.getString("motivo"));
                cita.setNombrePaciente(resultados.getString("nombre_paciente"));
                cita.setApellidosPaciente(resultados.getString("apellidos_paciente"));
                cita.setNombreOdontologo(resultados.getString("nombre_odontologo"));
                citas.add(cita);
            }
        }
        return citas;
    }

    public void insertar(Cita cita) throws SQLException {
        String sql = "INSERT INTO cita (fecha, hora, id_paciente, id_odontologo, motivo) VALUES (?, ?, ?, ?, ?)";
        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setObject(1, cita.getFecha());
            sentencia.setObject(2, cita.getHora());
            sentencia.setInt(3, cita.getIdPaciente());
            sentencia.setInt(4, cita.getIdOdontologo());
            sentencia.setString(5, cita.getMotivo());
            sentencia.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM cita WHERE id_cita = ?")) {
            sentencia.setInt(1, id);
            sentencia.executeUpdate();
        }
    }
}