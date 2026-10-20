http://localhost:8080/<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
  <head>
    <meta charset="UTF-8">
    <title>Nueva cita</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/estilos.css">
  </head>
  <body>
    <h1>Nueva cita</h1>
    <c:if test="${not empty error}"><p class="error"><c:out value="${error}"/></p></c:if>
    <form method="post" action="${pageContext.request.contextPath}/citas/guardar">
      <label>Fecha <input type="date" name="fecha" required></label><br>
      <label>Hora <input type="time" name="hora" required></label><br>
      <label>Paciente
        <select name="id_paciente" required>
          <option value="">-- Seleccione un paciente --</option>
          <c:forEach var="paciente" items="${pacientes}">
            <option value="${paciente.id}" <c:if test="${paciente.id == pacienteSeleccionado}">selected</c:if>><c:out value="${paciente.apellidos}"/>, <c:out value="${paciente.nombre}"/></option>
          </c:forEach>
        </select>
      </label><br>
      <label>Odontólogo
        <select name="id_odontologo" required>
          <option value="">-- Seleccione un odontólogo --</option>
          <c:forEach var="odontologo" items="${odontologos}">
            <option value="${odontologo.id}"><c:out value="${odontologo.nombre}"/> (<c:out value="${odontologo.especialidad}"/>)</option>
          </c:forEach>
        </select>
      </label><br>
      <label>Motivo <input type="text" name="motivo"></label><br>
      <button type="submit">Guardar cita</button>
    </form>
    <p><a href="${pageContext.request.contextPath}/citas">Volver al listado</a></p>
  </body>
</html>