package src.xmltest;


import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import src.xmltest.content.Student;
import src.xmltest.parsers.DOM;
import src.xmltest.parsers.JAXB;
import src.xmltest.parsers.SAX;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.*;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.*;
import javax.xml.transform.sax.SAXSource;
import java.io.*;

public class Main {
    private static Parser myParser;
    private static String input;
    private static int id;
    private static int fieldNumber;
    private static String firstName;
    private static String lastName;
    private static String birthDate;
    private static String course;
    private static String value;

    public static void main(String[] args) throws ParserConfigurationException, IOException, TransformerException, SAXException, JAXBException, XMLStreamException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        do {
            System.out.println("Input 1-DOM, 2-SAX, 3-JAXB:");
            input = bufferedReader.readLine();
            switch (input) {
                case "1":
                    myParser = new DOM();
                    break;
                case "2":
                    myParser = new SAX();
                    break;
                case "3":
                    myParser = new JAXB();
                    break;
                default:
                    System.out.println("Input error");
                    break;
            }
            System.out.println(myParser.getStudents().toString());
            System.out.println("Input 1-update, 2-add, 3-delete:");
            input = bufferedReader.readLine();

            switch (input) {
                case "1":
                    System.out.println("Input student id, field number, value");
                    id = Integer.valueOf(bufferedReader.readLine());
                    fieldNumber = Integer.valueOf(bufferedReader.readLine());
                    value = bufferedReader.readLine();
                    myParser.updateStudent(id, fieldNumber, value);
                    break;
                case "2":
                    System.out.println("Input student first name, last name, birth date, course");
                    firstName = bufferedReader.readLine();
                    lastName = bufferedReader.readLine();
                    birthDate= bufferedReader.readLine();
                    course= bufferedReader.readLine();
                    myParser.addStudent(new Student(birthDate,firstName,lastName,course));
                    break;
                case "3":
                    System.out.println("Input student id");
                    id = Integer.valueOf(bufferedReader.readLine());
                    myParser.deleteStudent(id);
                    break;
                default:
                    System.out.println("Input error");
                    break;
            }
            myParser.updateXml();
            System.out.println("Type 'exit' to exit.");
            input = bufferedReader.readLine();
        }while (!input.equals("exit"));

    }
}
