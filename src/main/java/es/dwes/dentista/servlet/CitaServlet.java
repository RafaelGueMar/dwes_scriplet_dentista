package es.dwes.dentista.servlet;

import es.dwes.dentista.dao.CitaDAO;
import es.dwes.dentista.dao.OdontologoDAO;
import es.dwes.dentista.dao.PacienteDAO;
import es.dwes.dentista.model.Cita;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/citas/*")
public class CitaServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(CitaServlet.class.getName());
    private final CitaDAO citaDAO = new CitaDAO();
    private final PacienteDAO pacienteDAO = new PacienteDAO();
    private final OdontologoDAO odontologoDAO = new OdontologoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            if ("/nueva".equals(request.getPathInfo())) {
                request.setAttribute("pacientes", pacienteDAO.listar());
                request.setAttribute("odontologos", odontologoDAO.listar());
                request.setAttribute("pacienteSeleccionado", request.getParameter("paciente"));
                request.getRequestDispatcher("/WEB-INF/vistas/formularioCita.jsp").forward(request, response);
                return;
            }
            boolean todas = "1".equals(request.getParameter("todas"));
            request.setAttribute("todas", todas);
            request.setAttribute("citas", citaDAO.listar(todas));
            String estado = request.getParameter("estado");
            if ("creada".equals(estado)) request.setAttribute("mensaje", "Cita registrada.");
            if ("eliminada".equals(estado)) request.setAttribute("mensaje", "Cita anulada.");
            if ("error".equals(estado)) request.setAttribute("error", "No se pudo completar la operación.");
            request.getRequestDispatcher("/WEB-INF/vistas/listadoCitas.jsp").forward(request, response);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "No se pudieron cargar los datos de citas", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "No se pudieron cargar los datos de citas.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String accion = request.getPathInfo();
        try {
            if ("/guardar".equals(accion)) {
                Cita cita = new Cita();
                cita.setFecha(LocalDate.parse(request.getParameter("fecha")));
                cita.setHora(LocalTime.parse(request.getParameter("hora")));
                cita.setIdPaciente(Integer.parseInt(request.getParameter("id_paciente")));
                cita.setIdOdontologo(Integer.parseInt(request.getParameter("id_odontologo")));
                cita.setMotivo(request.getParameter("motivo"));
                citaDAO.insertar(cita);
                response.sendRedirect(request.getContextPath() + "/citas?estado=creada");
            } else if ("/eliminar".equals(accion)) {
                citaDAO.eliminar(Integer.parseInt(request.getParameter("id_cita")));
                response.sendRedirect(request.getContextPath() + "/citas?estado=eliminada");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Revisa los datos introducidos.");
            try {
                request.setAttribute("pacientes", pacienteDAO.listar());
                request.setAttribute("odontologos", odontologoDAO.listar());
                request.getRequestDispatcher("/WEB-INF/vistas/formularioCita.jsp").forward(request, response);
            } catch (SQLException sqlException) {
                LOGGER.log(Level.SEVERE, "No se pudo volver a cargar el formulario de cita", sqlException);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "No se pudo guardar o eliminar la cita", e);
            response.sendRedirect(request.getContextPath() + "/citas?estado=error");
        }
    }
}