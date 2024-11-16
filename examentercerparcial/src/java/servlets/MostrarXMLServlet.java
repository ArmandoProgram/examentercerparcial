/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.sql.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import configuration.ConnectionBD;  // Asegúrate de que esta clase esté bien importada

public class MostrarXMLServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Preparar la respuesta
        response.setContentType("application/xml");

        // Usar try-with-resources para manejar la conexión
        try (ConnectionBD connectionBD = new ConnectionBD();
             Connection connection = connectionBD.getConnectionBD();
             Statement stmt = connection.createStatement()) {

            // Realizar la consulta SQL (ajusta la consulta según tu modelo de datos)
            String sql = "SELECT id, nombre, apellido, email, telefono, direccion FROM mi_tabla"; // Cambia el nombre de la tabla
            ResultSet rs = stmt.executeQuery(sql);

            // Crear el documento XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            // Crear el elemento raíz
            Element rootElement = doc.createElement("data");
            doc.appendChild(rootElement);

            // Recorrer los resultados de la base de datos y agregarlos al XML
            while (rs.next()) {
                Element beanElement = doc.createElement("bean");

                // Crear los elementos de cada propiedad y agregarlos al bean
                Element idElement = doc.createElement("id");
                idElement.appendChild(doc.createTextNode(String.valueOf(rs.getInt("id"))));
                beanElement.appendChild(idElement);

                Element nombreElement = doc.createElement("nombre");
                nombreElement.appendChild(doc.createTextNode(rs.getString("nombre")));
                beanElement.appendChild(nombreElement);

                Element apellidoElement = doc.createElement("apellido");
                apellidoElement.appendChild(doc.createTextNode(rs.getString("apellido")));
                beanElement.appendChild(apellidoElement);

                Element emailElement = doc.createElement("email");
                emailElement.appendChild(doc.createTextNode(rs.getString("email")));
                beanElement.appendChild(emailElement);

                Element telefonoElement = doc.createElement("telefono");
                telefonoElement.appendChild(doc.createTextNode(rs.getString("telefono")));
                beanElement.appendChild(telefonoElement);

                Element direccionElement = doc.createElement("direccion");
                direccionElement.appendChild(doc.createTextNode(rs.getString("direccion")));
                beanElement.appendChild(direccionElement);

                // Añadir el elemento 'bean' al elemento raíz
                rootElement.appendChild(beanElement);
            }

            // Convertir el documento XML a un String
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));

            // Configurar la respuesta para que sea descargada como XML
            response.setHeader("Content-Disposition", "attachment; filename=\"datos.xml\"");

            // Escribir el contenido del XML en la respuesta
            response.getWriter().write(writer.toString());

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("<error>Error al generar el XML</error>");
        }
    }
}
