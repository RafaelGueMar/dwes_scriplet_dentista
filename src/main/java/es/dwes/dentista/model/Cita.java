package es.dwes.dentista.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Cita {
    private int id;
    private LocalDate fecha;
    private LocalTime hora;
    private int idPaciente;
    private int idOdontologo;
    private String nombrePaciente;
    private String apellidosPaciente;
    private String motivo;
    private String nombreOdontologo;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }
    public int getIdPaciente() { return idPaciente; }
    public void setIdPaciente(int idPaciente) { this.idPaciente = idPaciente; }
    public int getIdOdontologo() { return idOdontologo; }
    public void setIdOdontologo(int idOdontologo) { this.idOdontologo = idOdontologo; }
    public String getNombrePaciente() { return nombrePaciente; }
    public void setNombrePaciente(String nombrePaciente) { this.nombrePaciente = nombrePaciente; }
    public String getApellidosPaciente() { return apellidosPaciente; }
    public void setApellidosPaciente(String apellidosPaciente) { this.apellidosPaciente = apellidosPaciente; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public String getNombreOdontologo() { return nombreOdontologo; }
    public void setNombreOdontologo(String nombreOdontologo) { this.nombreOdontologo = nombreOdontologo; }
}