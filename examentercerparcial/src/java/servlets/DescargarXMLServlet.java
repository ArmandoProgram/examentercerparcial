package servlets;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
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
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author arman
 */
@WebServlet(urlPatterns = {"/descargarxmlservlet"})
public class DescargarXMLServlet extends HttpServlet {

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
            out.println("<title>Servlet DescargarXMLServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DescargarXMLServlet at " + request.getContextPath() + "</h1>");
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
  // Obtener la lista de MiBean de la sesión
        ListaMiBean listaMiBean = (ListaMiBean) request.getSession().getAttribute("listaMiBean");

        if (listaMiBean == null) {
            response.getWriter().write("<error>No hay datos disponibles para exportar.</error>");
            return;
        }

        // Crear el documento XML
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            // Crear el elemento raíz
            Element rootElement = doc.createElement("data");
            doc.appendChild(rootElement);

            // Recorrer la lista de MiBean y agregar los datos al XML
            for (MiBean bean : listaMiBean.getListaDatos()) {
                Element beanElement = doc.createElement("bean");

                // Crear elementos para cada propiedad de MiBean
                Element idElement = doc.createElement("id");
                idElement.appendChild(doc.createTextNode(String.valueOf(bean.getId())));
                beanElement.appendChild(idElement);

                Element nombreElement = doc.createElement("nombre");
                nombreElement.appendChild(doc.createTextNode(bean.getNombre()));
                beanElement.appendChild(nombreElement);

                Element apellidoElement = doc.createElement("apellido");
                apellidoElement.appendChild(doc.createTextNode(bean.getApellido()));
                beanElement.appendChild(apellidoElement);

                Element emailElement = doc.createElement("email");
                emailElement.appendChild(doc.createTextNode(bean.getEmail()));
                beanElement.appendChild(emailElement);

                Element telefonoElement = doc.createElement("telefono");
                telefonoElement.appendChild(doc.createTextNode(bean.getTelefono()));
                beanElement.appendChild(telefonoElement);

                Element direccionElement = doc.createElement("direccion");
                direccionElement.appendChild(doc.createTextNode(bean.getDireccion()));
                beanElement.appendChild(direccionElement);

                // Añadir el elemento bean a la raíz
                rootElement.appendChild(beanElement);
            }

            // Configurar la respuesta para que sea una descarga de archivo
            response.setContentType("application/xml");
            response.setHeader("Content-Disposition", "attachment; filename=\"datos.xml\"");

            // Convertir el documento XML a un String
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));

            // Escribir el contenido XML al flujo de salida del response
            response.getWriter().write(writer.toString());
            
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("<error>Error al generar el XML.</error>");
        }   }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
