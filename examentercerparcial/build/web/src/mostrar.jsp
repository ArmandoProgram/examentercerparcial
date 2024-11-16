<%-- 
    Document   : mostrar
    Created on : 13/11/2024, 07:04:49 PM
    Author     : arman
--%>
<%@ page import="java.sql.*" %>
<%@ page import="configuration.ConnectionBD" %>
<html>
<head>
    <title>Mostrar Registros</title>
</head>
<body>
    <h2>Lista de Registros</h2>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Nombre</th>
        </tr>
        <%
            ConnectionBD connectionBD = new ConnectionBD();
            Connection conn = connectionBD.getConnectionBD();
            String sql = "SELECT * FROM mi_tabla";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
        %>
        <tr>
            <td><%= rs.getString("id") %></td>
            <td><%= rs.getString("nombre") %></td>
        </tr>
        <%
            }
            conn.close();
        %>
    </table>
    <form action="verBean.jsp" method="GET">
        <input type="submit" value="Ver Datos en Bean" />
    </form>

    <!-- Botón para redirigir a verXML.jsp -->
    <form action="verXML.jsp" method="GET">
        <input type="submit" value="Ver Datos en XML" />
    </form>
    
        <!-- Botón para descargar el archivo XML -->
    <a href="descargarxmlservlet" download>
        <button type="button">Descargar XML</button>
    </a>
</body>
</html>



