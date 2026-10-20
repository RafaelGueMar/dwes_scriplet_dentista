package es.dwes.dentista.dao;

import es.dwes.dentista.model.Paciente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {
    public List<Paciente> listar() throws SQLException {
        String sql = "SELECT p.id_paciente, p.nombre, p.apellidos, p.dni, p.fecha_nacimiento, "
                + "p.telefono, p.email, p.direccion, p.alergias, "
                + "(SELECT COUNT(*) FROM cita c WHERE c.id_paciente = p.id_paciente) AS numero_citas "
                + "FROM paciente p ORDER BY p.apellidos, p.nombre";
        List<Paciente> pacientes = new ArrayList<>();
        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql);
             ResultSet resultados = sentencia.executeQuery()) {
            while (resultados.next()) {
                Paciente paciente = new Paciente();
                paciente.setId(resultados.getInt("id_paciente"));
                paciente.setNombre(resultados.getString("nombre"));
                paciente.setApellidos(resultados.getString("apellidos"));
                paciente.setDni(resultados.getString("dni"));
                paciente.setFechaNacimiento(resultados.getObject("fecha_nacimiento", java.time.LocalDate.class));
                paciente.setTelefono(resultados.getString("telefono"));
                paciente.setEmail(resultados.getString("email"));
                paciente.setDireccion(resultados.getString("direccion"));
                paciente.setAlergias(resultados.getString("alergias"));
                paciente.setNumeroCitas(resultados.getInt("numero_citas"));
                pacientes.add(paciente);
            }
        }
        return pacientes;
    }

    public void insertar(Paciente paciente) throws SQLException {
        String sql = "INSERT INTO paciente (nombre, apellidos, dni, fecha_nacimiento, telefono, email, direccion, alergias) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setString(1, paciente.getNombre());
            sentencia.setString(2, paciente.getApellidos());
            sentencia.setString(3, paciente.getDni());
            if (paciente.getFechaNacimiento() == null) {
                sentencia.setNull(4, Types.DATE);
            } else {
                sentencia.setObject(4, paciente.getFechaNacimiento());
            }
            sentencia.setString(5, paciente.getTelefono());
            sentencia.setString(6, paciente.getEmail());
            sentencia.setString(7, paciente.getDireccion());
            sentencia.setString(8, paciente.getAlergias());
            sentencia.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM paciente WHERE id_paciente = ?")) {
            sentencia.setInt(1, id);
            sentencia.executeUpdate();
        }
    }
}