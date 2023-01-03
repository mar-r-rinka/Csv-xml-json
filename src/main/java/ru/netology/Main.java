package ru.netology;

import com.opencsv.CSVWriter;
import org.w3c.dom.*;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static ru.netology.CsvAdapter.parseCSV;
import static ru.netology.JsonAdapter.jsonToList;
import static ru.netology.JsonAdapter.listToJson;
import static ru.netology.JsonAdapter.readString;
import static ru.netology.XmlAdapter.parseXML;

public class Main {
    public static void main(String[] args) {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";
        List<String[]> employee = new ArrayList<>();
        employee.add("1,John,Smith,USA,25".split(","));
        employee.add("2,Inav,Petrov,RU,23".split(","));


//CSV
        for (String[] empl : employee) {
            try (CSVWriter writer = new CSVWriter(new FileWriter("data.csv", true))) {
                writer.writeNext(empl);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        List<Employee> employeesCSV = parseCSV(columnMapping, fileName);
        try (FileWriter file = new FileWriter("new_data.json")) {
            file.write(listToJson(employeesCSV));
            file.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println(employeesCSV);
        System.out.println(listToJson(employeesCSV));


//XML
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("staff");
            doc.appendChild(root);
            for (Employee emp : employeesCSV) {
                Element empl = doc.createElement("employee");
                root.appendChild(empl);
                Element id = doc.createElement("id");
                id.appendChild(doc.createTextNode(Integer.toString((int) emp.id)));
                empl.appendChild(id);
                Element firstName = doc.createElement("firstName");
                firstName.appendChild(doc.createTextNode(emp.firstName));
                empl.appendChild(firstName);
                Element lastName = doc.createElement("lastName");
                lastName.appendChild(doc.createTextNode(emp.lastName));
                empl.appendChild(lastName);
                Element country = doc.createElement("country");
                country.appendChild(doc.createTextNode(emp.country));
                empl.appendChild(country);
                Element age = doc.createElement("age");
                age.appendChild(doc.createTextNode(Integer.toString(emp.age)));
                empl.appendChild(age);

            }
            DOMSource domSource = new DOMSource(doc);
            StreamResult streamResult = new StreamResult(new File("data.xml"));
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.transform(domSource, streamResult);

            List<Employee> list = parseXML("data.xml");
            System.out.println(list);
        } catch (ParserConfigurationException | TransformerException ex) {
            ex.printStackTrace();

        }

// JSON
        String json = readString("new_data.json");
        System.out.println(jsonToList(json));
    }


}