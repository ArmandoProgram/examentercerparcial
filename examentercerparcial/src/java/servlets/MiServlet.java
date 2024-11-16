package servlets;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import configuration.ConnectionBD;

@WebServlet(urlPatterns = {"/miservlet"})
public class MiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        String direccion = request.getParameter("direccion");

        // Crear un nuevo objeto MiBean y llenarlo con los datos obtenidos
        MiBean bean = new MiBean();
        bean.setNombre(nombre);
        bean.setApellido(apellido);
        bean.setEmail(email);
        bean.setTelefono(telefono);
        bean.setDireccion(direccion);

        // Obtener el ListaMiBean de la sesión y agregar el nuevo bean
        ListaMiBean listaMiBean = (ListaMiBean) request.getSession().getAttribute("listaMiBean");
        if (listaMiBean == null) {
            listaMiBean = new ListaMiBean();
            request.getSession().setAttribute("listaMiBean", listaMiBean);
        }
        listaMiBean.agregarDatos(bean);
        
        ConnectionBD connectionBD = new ConnectionBD();
        try (Connection conn = connectionBD.getConnectionBD()) {
            String sql = "INSERT INTO mi_tabla (nombre, apellido, email, telefono, direccion) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nombre);
                stmt.setString(2, apellido);
                stmt.setString(3, email);
                stmt.setString(4, telefono);
                stmt.setString(5, direccion);
                stmt.executeUpdate();
            }
            response.sendRedirect("src/mostrar.jsp"); 
        } catch (SQLException e) {
            System.err.println("Error en el insert: " + e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error en el insert: " + e.getMessage());
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet para insertar datos en la base de datos";
    }
}
