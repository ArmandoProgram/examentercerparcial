<%-- 
    Document   : verBean
    Created on : 13/11/2024, 07:15:28 PM
    Author     : arman
--%>
<%@ page import="servlets.MiBean" %>
<%@ page import="servlets.ListaMiBean" %>
<jsp:useBean id="listaMiBean" class="servlets.ListaMiBean" scope="session" />

<html>
<head>
    <title>Mostrar Registros</title>
</head>
<body>
    <h2>Datos Almacenados</h2>

    <ul>
        <%
            // Obtener la lista de datos almacenados en el session bean
            if (listaMiBean != null) {
                for (MiBean bean : listaMiBean.getListaDatos()) {
        %>
            <li>
                Nombre: <%= bean.getNombre() %><br>
                Apellido: <%= bean.getApellido() %><br>
                Email: <%= bean.getEmail() %><br>
                Teléfono: <%= bean.getTelefono() %><br>
                Dirección: <%= bean.getDireccion() %><br>
            </li>
        <%
                }
            } else {
        %>
            <p>No hay datos almacenados.</p>
        <%
            }
        %>
    </ul>
</body>
</html>

