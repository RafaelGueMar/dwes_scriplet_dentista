<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
  <head>
    <meta charset="UTF-8">
    <title>Nuevo paciente</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/estilos.css">
  </head>
  <body>
    <h1>Nuevo paciente</h1>
    <c:if test="${not empty error}"><p class="error"><c:out value="${error}"/></p></c:if>
    <form method="post" action="${pageContext.request.contextPath}/pacientes/guardar">
      <label>Nombre <input type="text" name="nombre" required></label><br>
      <label>Apellidos <input type="text" name="apellidos" required></label><br>
      <label>DNI <input type="text" name="dni" maxlength="9" required></label><br>
      <label>Fecha de nacimiento <input type="date" name="fecha_nacimiento"></label><br>
      <label>Teléfono <input type="text" name="telefono"></label><br>
      <label>Correo electrónico <input type="email" name="email"></label><br>
      <label>Dirección <input type="text" name="direccion"></label><br>
      <label>Alergias o notas <input type="text" name="alergias"></label><br>
      <button type="submit">Guardar paciente</button>
    </form>
    <p><a href="${pageContext.request.contextPath}/pacientes">Volver al listado</a></p>
  </body>
</html>