<%-- 
    Document   : index
    Created on : 13/11/2024, 06:54:34 PM
    Author     : arman
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <form action="../miservlet" method="POST">
    <label>Nombre: <input type="text" name="nombre"></label><br>
    <label>Apellido: <input type="text" name="apellido"></label><br>
    <label>Email: <input type="text" name="email"></label><br>
    <label>Teléfono: <input type="text" name="telefono"></label><br>
    <label>Dirección: <input type="text" name="direccion"></label><br>
    <button type="submit">Enviar</button>
</form>

</html>
