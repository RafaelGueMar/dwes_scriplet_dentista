package es.dwes.dentista.servlet;

import es.dwes.dentista.dao.PacienteDAO;
import es.dwes.dentista.model.Paciente;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/pacientes/*")
public class PacienteServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(PacienteServlet.class.getName());
    private final PacienteDAO pacienteDAO = new PacienteDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if ("/nuevo".equals(request.getPathInfo())) {
            request.getRequestDispatcher("/WEB-INF/vistas/formularioPaciente.jsp").forward(request, response);
            return;
        }
        String estado = request.getParameter("estado");
        if ("creado".equals(estado)) request.setAttribute("mensaje", "Paciente dado de alta.");
        if ("eliminado".equals(estado)) request.setAttribute("mensaje", "Paciente eliminado junto con sus citas.");
        if ("error".equals(estado)) request.setAttribute("error", "No se pudo completar la operación.");
        try {
            request.setAttribute("pacientes", pacienteDAO.listar());
            request.getRequestDispatcher("/WEB-INF/vistas/listadoPacientes.jsp").forward(request, response);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "No se pudo cargar el listado de pacientes", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "No se pudo cargar el listado de pacientes.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String accion = request.getPathInfo();
        try {
            if ("/guardar".equals(accion)) {
                Paciente paciente = new Paciente();
                paciente.setNombre(request.getParameter("nombre"));
                paciente.setApellidos(request.getParameter("apellidos"));
                paciente.setDni(request.getParameter("dni"));
                String fecha = request.getParameter("fecha_nacimiento");
                paciente.setFechaNacimiento(fecha == null || fecha.isBlank() ? null : LocalDate.parse(fecha));
                paciente.setTelefono(request.getParameter("telefono"));
                paciente.setEmail(request.getParameter("email"));
                paciente.setDireccion(request.getParameter("direccion"));
                paciente.setAlergias(request.getParameter("alergias"));
                pacienteDAO.insertar(paciente);
                response.sendRedirect(request.getContextPath() + "/pacientes?estado=creado");
            } else if ("/eliminar".equals(accion)) {
                pacienteDAO.eliminar(Integer.parseInt(request.getParameter("id_paciente")));
                response.sendRedirect(request.getContextPath() + "/pacientes?estado=eliminado");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Revisa los datos introducidos.");
            request.getRequestDispatcher("/WEB-INF/vistas/formularioPaciente.jsp").forward(request, response);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "No se pudo guardar o eliminar el paciente", e);
            response.sendRedirect(request.getContextPath() + "/pacientes?estado=error");
        }
    }
}