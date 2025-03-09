package com.pruebaconcepto;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class ProcessResourceServiceImpl implements ProcessResourceService {

    @Override
    public Response ProcessData(StratusDTO request) {
        String convenio = request.convenio;
        String template = obtenerTemplate(convenio);

        if (template == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    // .entity(Map.of("error", "Convenio no válido"))
                    .entity("<error>Convenio no válido</error>")
                    .build();
        }

        try {
            String xmlProcesado = generarXML(request, template);
            return Response.ok(xmlProcesado).build();
            // return Response.ok(Map.of("xml", xmlProcesado)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("<error>Error procesando XML</error>")
                    // .entity(Map.of("error", "Error procesando XML"))
                    .build();
        }
    }

    private String obtenerTemplate(String convenio) {
        return switch (convenio) {
            case "template1" ->
                "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><root><canal1>canalA</canal1><canal2>canalB</canal2><canal3>canalC</canal3><canal4>canalD</canal4><canal5>canalE</canal5></root>";
            case "template2" ->
                "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><root><canal1>canalE</canal1><canal2>canalD</canal2><canal3>canalC</canal3><canal4>canalB</canal4><canal5>canalA</canal5></root>";
            case "template3" ->
                "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><root><canal1>canalA</canal1><canal2>canalC</canal2><canal3>canalE</canal3><canal4>canalD</canal4><canal5>canalB</canal5></root>";
            default -> null;
        };
    }

    private String generarXML(StratusDTO data, String template) throws Exception {
        // Parsea el XML en un objeto
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(template)));

        // Obtiene el nodo raiz
        Element root = document.getDocumentElement();
        // Almacena los elementos hijos (canal1, canal2)
        NodeList nodes = root.getChildNodes();

        // Lee las etiquetas del XML de plantilla y asigna valores dinamicamente
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            // verifica que se procesen nodos XML
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                // Obtiene el valor original de la plantilla (canalA, canalB)
                String nodeName = node.getTextContent(); 
                // Mapea el valor real desde DTO y se obtiene el valor del nodo, 
                String value = obtenerValorDesdeDTO(nodeName, data);
                // Asigna el valor correcto
                node.setTextContent(value != null ? value : null); 
            }
        }

        // Se Convierte de document a XML
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        // "yes" se aplica la sangria
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        // Se convierte a string
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(document), new StreamResult(writer));

        return writer.toString();
    }

    private String obtenerValorDesdeDTO(String nodeName, StratusDTO data) {
        return switch (nodeName) {
            case "canalA" -> data.getCanalA();
            case "canalB" -> data.getCanalB();
            case "canalC" -> data.getCanalC();
            case "canalD" -> data.getCanalD();
            case "canalE" -> data.getCanalE();
            default -> null;
        };
    }

}