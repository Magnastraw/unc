package src.xmltest.parsers;


import src.xmltest.Parser;
import src.xmltest.content.Student;
import src.xmltest.content.Students;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.util.Iterator;
import java.util.List;

public class JAXB implements Parser {
    private Students students;
    public JAXB() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Students.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        students = (Students) unmarshaller.unmarshal( new File("dom.xml"));
    }

    @Override
    public void updateStudent(int id, int fieldNumber, String value) {
        for(Student student:students.getStudents()){
            if(student.getId()==id){
                switch (fieldNumber){
                    case 1:
                        student.setFirstName(value);
                        break;
                    case 2:
                        student.setFirstName(value);
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
    public void addStudent(Student student) {
        student.setId(students.getStudents().get(students.getStudents().size()-1).getId()+1);
        students.getStudents().add(student);
    }

    @Override
    public void deleteStudent(int id) {
        Iterator<Student> iterator = students.getStudents().iterator();
        while(iterator.hasNext()){
            Student student = iterator.next();
            if(student.getId()==id){
                iterator.remove();
            }
        }
    }

    @Override
    public List<Student> getStudents() {
        return students.getStudents();
    }

    public void updateXml() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Students.class);
        Marshaller marshaller= context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(students, new File("dom.xml"));
    }
}
