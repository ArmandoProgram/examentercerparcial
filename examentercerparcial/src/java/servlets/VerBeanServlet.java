package servlets;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import configuration.ConnectionBD;
import java.io.PrintWriter;


/**
 *
 * @author arman
 */
@WebServlet(urlPatterns = {"/verbeanservlet"})
public class VerBeanServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet VerBeanServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VerBeanServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
  int id = Integer.parseInt(request.getParameter("id"));
        MiBean bean = new MiBean();

        ConnectionBD connectionBD = new ConnectionBD();
        try (Connection conn = connectionBD.getConnectionBD()) {
            String sql = "SELECT * FROM mi_tabla WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        bean.setId(rs.getInt("id"));
                        bean.setNombre(rs.getString("nombre"));
                        bean.setApellido(rs.getString("apellido"));
                        bean.setEmail(rs.getString("email"));
                        bean.setTelefono(rs.getString("telefono"));
                        bean.setDireccion(rs.getString("direccion"));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en VerBeanServlet: " + e);
        }

        request.setAttribute("bean", bean);
        request.getRequestDispatcher("verBean.jsp").forward(request, response);
    }        }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */


