package src.xmltest.parsers;


import org.w3c.dom.*;
import org.xml.sax.SAXException;
import src.xmltest.Parser;
import src.xmltest.content.Student;
import src.xmltest.content.Students;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DOM implements Parser {
    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;
    private Document document;
    private Document documentOutput;
    private NodeList studentList;
    private List<Student> students;

    public DOM() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        document = builder.parse("dom.xml");
        studentList = document.getElementsByTagName("student");
        students = new ArrayList<>();

    }

    @Override
    public void addStudent(Student student) {
        student.setId(students.get(students.size()-1).getId()+1);
        students.add(student);
    }

    @Override
    public void updateStudent(int id,int fieldNumber, String value){
        for(Student student:students){
            if(student.getId()==id){
                switch (fieldNumber){
                    case 1:
                        student.setFirstName(value);
                        break;
                    case 2:
                        student.setLastName(value);
                        break;
                    case 3:
                        student.setBirthDate(value);
                        break;
                    case 4:
                        student.setCourse(value);
                        break;
                    default:
                        System.out.println("Wrong input");
                }
            }
        }
        System.out.println(students.toString());

    }

    @Override
    public void deleteStudent(int id){
        Iterator<Student> iterator = students.iterator();
        while(iterator.hasNext()){
            Student student = iterator.next();
            if(student.getId()==id){
                iterator.remove();
            }
        }
    }

    public void update(){
        documentOutput = builder.newDocument();
        Element studentsRoot = documentOutput.createElement("students");
        for(Student student:students){
            Element studentElem = documentOutput.createElement("student");

            Element idElem = documentOutput.createElement("id");
            Element birthDateElem = documentOutput.createElement("birthDate");
            Element firstNameElem = documentOutput.createElement("firstName");
            Element lastNameElem = documentOutput.createElement("lastName");
            Element courseELem = documentOutput.createElement("course");

            idElem.appendChild(documentOutput.createTextNode(String.valueOf(student.getId())));
            birthDateElem.appendChild(documentOutput.createTextNode(student.getBirthDate()));
            firstNameElem.appendChild(documentOutput.createTextNode(student.getFirstName()));
            lastNameElem.appendChild(documentOutput.createTextNode(student.getLastName()));
            courseELem.appendChild(documentOutput.createTextNode(student.getCourse()));

            studentElem.appendChild(idElem);
            studentElem.appendChild(firstNameElem);
            studentElem.appendChild(lastNameElem);
            studentElem.appendChild(birthDateElem);
            studentElem.appendChild(courseELem);

            studentsRoot.appendChild(studentElem);

        }
        documentOutput.appendChild(studentsRoot);
    }

    @Override
    public List<Student> getStudents() {
        studentList = document.getElementsByTagName("student");
        for (int i = 0; i < studentList.getLength(); i++) {
            Node studentNode = studentList.item(i);
            NodeList params = studentNode.getChildNodes();
            Student student = new Student();
            for (int j = 0; j < params.getLength(); j++) {
                Node node = params.item(j);
                switch (node.getNodeName()){
                    case "id":
                        student.setId(Integer.valueOf(node.getTextContent()));
                        break;
                    case "firstName":
                        student.setFirstName(node.getTextContent());
                        break;
                    case "lastName":
                        student.setLastName(node.getTextContent());
                        break;
                    case "birthDate":
                        student.setBirthDate(node.getTextContent());
                        break;
                    case "course":
                        student.setCourse(node.getTextContent());
                        break;

                }
            }
            students.add(student);
        }
        return students;
    }

//    @Override
//    public void updateParameter(int pos, String paramName, String content) throws TransformerException {
//        Node student = studentList.item(pos);
//        NodeList studentParams = student.getChildNodes();
//        for (int j = 0; j < studentParams.getLength(); j++) {
//            Node node = studentParams.item(j);
//            if (node.getNodeName().equals(paramName)) {
//                node.setTextContent(content);
//            }
//        }
//
//    }
//
//    @Override
//    public void deleteParameter(int pos, String paramType) throws TransformerException {
//        Node student = studentList.item(pos);
//        NodeList studentParams = student.getChildNodes();
//        for (int j = 0; j < studentParams.getLength(); j++) {
//            Node node = studentParams.item(j);
//            if (node.getNodeName().equals(paramType)) {
//                student.removeChild(node);
//            }
//        }
//
//
//    }
//
//    @Override
//    public void addParameter(int pos, String paramName, String content) throws TransformerException {
//        Node student = studentList.item(pos);
//        Element additionElement = document.createElement(paramName);
//        additionElement.setTextContent(content);
//        student.appendChild(additionElement);
//
//    }

    public void updateXml() throws TransformerException {
        update();
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(documentOutput);
        StreamResult streamResult = new StreamResult(new File("dom.xml"));
        transformer.transform(domSource, streamResult);
    }
}
