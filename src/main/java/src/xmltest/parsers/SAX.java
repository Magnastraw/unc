package src.xmltest.parsers;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.DefaultHandler;
import src.xmltest.Parser;
import src.xmltest.content.Student;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SAX extends DefaultHandler implements Parser {
    private String currentElement;
    private List<Student> students;
    private Student student;

    public SAX() throws ParserConfigurationException, SAXException, IOException {
        students = new ArrayList<>();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        InputStream xmlData = new FileInputStream("dom.xml");
        parser.parse(xmlData, this);
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

    @Override
    public List<Student> getStudents() {
        return students;
    }

    @Override
    public void updateXml() throws FileNotFoundException, UnsupportedEncodingException, XMLStreamException {

        OutputStream outputStream = new FileOutputStream(new File("dom.xml"));

        XMLStreamWriter out = XMLOutputFactory.newInstance().createXMLStreamWriter(
                new OutputStreamWriter(outputStream, "utf-8"));

        out.writeStartDocument();
        out.writeStartElement("students");
        for(Student student:students){
            out.writeStartElement("student");

            out.writeStartElement("id");
            out.writeCharacters(String.valueOf(student.getId()));
            out.writeEndElement();

            out.writeStartElement("firstName");
            out.writeCharacters(student.getFirstName());
            out.writeEndElement();

            out.writeStartElement("lastName");
            out.writeCharacters(student.getLastName());
            out.writeEndElement();

            out.writeStartElement("birthDate");
            out.writeCharacters(student.getBirthDate());
            out.writeEndElement();

            out.writeStartElement("course");
            out.writeCharacters(student.getCourse());
            out.writeEndElement();

            out.writeEndElement();

        }
        out.writeEndElement();
        out.writeEndDocument();
        out.close();
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentElement = qName;
        if (qName.equals("student")) {
            student = new Student();
        }
        super.startElement(uri, localName, qName, attributes);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        currentElement="";
        if (qName.equals("student")) {
            students.add(student);
        }
        super.endElement(uri, localName, qName);

    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        switch (currentElement) {
            case "id":
                student.setId(Integer.valueOf(new String(ch, start, length)));
                break;
            case "firstName":
                student.setFirstName(new String(ch, start, length));
                break;
            case "lastName":
                student.setLastName(new String(ch, start, length));
                break;
            case "birthDate":
                student.setBirthDate(new String(ch, start, length));
                break;
            case "course":
                student.setCourse(new String(ch, start, length));
                break;
        }
        super.characters(ch, start, length);
    }

}

