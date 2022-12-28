package ru.netology;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.w3c.dom.*;
import org.xml.sax.SAXException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";
        List<String[]> employee = new ArrayList<>();
        employee.add("1,John,Smith,USA,25".split(","));
        employee.add("2,Inav,Petrov,RU,23".split(","));
        for (String[] emp : employee) {
            System.out.println(Arrays.toString(emp));
        }
        for (String[] empl : employee) {
            try (CSVWriter writer = new CSVWriter(new FileWriter("data.csv", true))) {
                writer.writeNext(empl);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        List<Employee> employeesCSV = parseCSV(columnMapping, fileName);
        System.out.println(employeesCSV);
        System.out.println(listToJson(employeesCSV));

        try (FileWriter file = new FileWriter("new_data.json")) {
            file.write(listToJson(employeesCSV));
            file.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String json = readString("new_data.json");
        //       List<Employee> listFromJson = jsonToList(json);


        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
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

    }

    public static List<Employee> parseCSV(String[] columnMapping, String fileName) {
        List<Employee> list = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(fileName))) {
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping);
            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(csvReader)
                    .withMappingStrategy(strategy)
                    .build();
            list = csv.parse();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public static String listToJson(List<Employee> list) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();
        String json = gson.toJson(list, listType);
        return json;
    }


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

                long id = Long.parseLong(doc.getElementsByTagName("id").item(0).getTextContent());
                String firstName = doc.getElementsByTagName("firstName").item(0).getTextContent();
                String lastName = doc.getElementsByTagName("lastName").item(0).getTextContent();
                String country = doc.getElementsByTagName("country").item(0).getTextContent();
                int age = Integer.parseInt(doc.getElementsByTagName("age").item(0).getTextContent());
                Employee emp = new Employee(id, firstName, lastName, country, age);
                employee.add(emp);
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            ex.printStackTrace(System.out);
        }
        return employee;
    }

    public static String readString(String fileName) throws IOException {
        String jsonText;
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        jsonText = reader.readLine();
        return jsonText;
    }

//    public static List<Employee> jsonToList(String json){
//        List<Employee>
//        return listFromJson;
//    }

}