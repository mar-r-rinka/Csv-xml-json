package ru.netology;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlAdapter {
    public static List<Employee> parseXML(String fileXML) {
        List<Employee> employee = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(fileXML));
            employee = new ArrayList<>();
            Node root = doc.getDocumentElement();
            NodeList employeeList = root.getChildNodes();
            for (int i = 0; i < employeeList.getLength(); i++) {

                long id = Long.parseLong(doc.getElementsByTagName("id").item(i).getTextContent());
                String firstName = doc.getElementsByTagName("firstName").item(i).getTextContent();
                String lastName = doc.getElementsByTagName("lastName").item(i).getTextContent();
                String country = doc.getElementsByTagName("country").item(i).getTextContent();
                int age = Integer.parseInt(doc.getElementsByTagName("age").item(i).getTextContent());
                Employee emp = new Employee(id, firstName, lastName, country, age);
                employee.add(emp);
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            ex.printStackTrace(System.out);
        }
        return employee;
    }
}
