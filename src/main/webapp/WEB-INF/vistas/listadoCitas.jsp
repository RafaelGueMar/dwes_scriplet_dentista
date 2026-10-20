<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
  <head>
    <meta charset="UTF-8">
    <title>Listado de citas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/estilos.css">
  </head>
  <body>
    <h1><c:choose><c:when test="${todas}">Todas las citas</c:when><c:otherwise>Próximas citas</c:otherwise></c:choose></h1>
    <c:if test="${not empty mensaje}"><p><c:out value="${mensaje}"/></p></c:if>
    <c:if test="${not empty error}"><p class="error"><c:out value="${error}"/></p></c:if>
    <table>
      <tr><th>Id cita</th><th>Fecha</th><th>Hora</th><th>Paciente</th><th>Motivo</th><th>Odontólogo</th><th>Acciones</th></tr>
      <c:forEach var="cita" items="${citas}">
        <tr>
          <td><c:out value="${cita.id}"/></td><td><c:out value="${cita.fecha}"/></td>
          <td><c:out value="${cita.hora}"/></td>
          <td><c:out value="${cita.apellidosPaciente}"/>, <c:out value="${cita.nombrePaciente}"/></td>
          <td><c:out value="${cita.motivo}"/></td><td><c:out value="${cita.nombreOdontologo}"/></td>
          <td><form method="post" action="${pageContext.request.contextPath}/citas/eliminar">
            <input type="hidden" name="id_cita" value="${cita.id}"><button type="submit">Anular</button>
          </form></td>
        </tr>
      </c:forEach>
    </table>
    <p>
      <c:choose>
        <c:when test="${todas}"><a href="${pageContext.request.contextPath}/citas">Ver próximas citas</a></c:when>
        <c:otherwise><a href="${pageContext.request.contextPath}/citas?todas=1">Ver el histórico completo</a></c:otherwise>
      </c:choose>
      | <a href="${pageContext.request.contextPath}/citas/nueva">Nueva cita</a>
      | <a href="${pageContext.request.contextPath}/">Volver al inicio</a>
    </p>
  </body>
</html>