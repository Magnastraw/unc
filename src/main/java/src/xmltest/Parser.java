package src.xmltest;


import src.xmltest.content.Student;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface Parser {
    void updateStudent(int id,int fieldNumber, String value);
    void addStudent(Student student);
    void deleteStudent(int id);
    List<Student> getStudents();
    void updateXml() throws JAXBException, TransformerException, FileNotFoundException, UnsupportedEncodingException, XMLStreamException;

}
