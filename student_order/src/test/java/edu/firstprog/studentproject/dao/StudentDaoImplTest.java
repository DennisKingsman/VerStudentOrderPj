package edu.firstprog.studentproject.dao;

import edu.firstprog.studentproject.domain.*;
import edu.firstprog.studentproject.exception.DaoException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class StudentDaoImplTest {

    @BeforeClass //running only once
    public static void startUP() throws Exception {
        DBInit.startUp();
    }

    @Test
    public void saveStudentOrder() throws DaoException {
        StudentOrder studentOrder = buildStudentOrder(10);
        Long id = new StudentDaoImpl().saveStudentOrder(studentOrder);
    }

    @Test(expected = DaoException.class)
    public void saveStudentOrderError() throws DaoException {
        StudentOrder studentOrder = buildStudentOrder(10);
        studentOrder.getHusband().setSurName(null);
        Long id = new StudentDaoImpl().saveStudentOrder(studentOrder);
    }

    @Test
    public void getStudentOrders() throws DaoException {
        List<StudentOrder> list = new StudentDaoImpl().getStudentOrders();

    }

    public StudentOrder buildStudentOrder(long id)
    {
        StudentOrder sO = new StudentOrder();
        sO.setStudentOrderID(id);
        StudentOrder sO1 = sO;

        sO.setMarriageCertificateId("" + (123456000 + id));
        sO.setMarriageDate(LocalDate.of(2016, 7, 4));

        RegisterOffice registerOffice = new RegisterOffice(1L, " ", " ");
        sO.setMarriageOffice(registerOffice);

        Street street = new Street(1L, "First street");

        Address address = new Address("195000", street, "12", "", "142");

        // Husband
        Adult husband = new Adult("Petrov", "Victor" , "Sergeevich", LocalDate.of(1997, 8 , 24));
        husband.setPassportSeria("" + (1000 + id));
        husband.setPassportNumber("" + (100000 + id));
        husband.setIssueDate(LocalDate.of(2017, 9, 15));

        PassportOffice passportOffice1 = new PassportOffice(1L, "", "");
        husband.setIssueDepartment(passportOffice1);
        husband.setStudentID("" + (100000 + id));
        husband.setAddress(address);
        husband.setUniversity(new University(2L, " "));
        husband.setStudentID("HH12345");

        // Wife
        Adult wife = new Adult("Петрова", "Вероника", "Алекссевна", LocalDate.of(1998, 3, 12));
        wife.setPassportSeria("" + (2000 + id));
        wife.setPassportNumber("" + (200000 + id));
        wife.setIssueDate(LocalDate.of(2018, 4, 5));

        PassportOffice passportOffice2 = new PassportOffice(2L, "", "");
        wife.setIssueDepartment(passportOffice2);
        wife.setStudentID("" + (200000 + id));
        wife.setAddress(address);
        wife.setUniversity(new University(1L, " "));
        wife.setStudentID("WW12345");

        // Child1
        Child child1 = new Child("Петрова", "Ирина", "Викторовна", LocalDate.of(2018, 6, 29));
        child1.setCertificateNumber("" + (300000 + id));
        child1.setIssueDate(LocalDate.of(2018, 6, 11 ));

        RegisterOffice registerOffice2 = new RegisterOffice(2L, " ", " ");
        child1.setIssueDepartment(registerOffice2);
        child1.setAddress(address);
        // Child1
        Child child2 = new Child("Петров", "Евгений", "Викторович", LocalDate.of(2018, 6, 29));
        child2.setCertificateNumber("" + (400000 + id));
        child2.setIssueDate(LocalDate.of(2018, 7, 19));

        RegisterOffice registerOffice3 = new RegisterOffice(3L, " ", " ");
        child2.setIssueDepartment(registerOffice3);
        child2.setAddress(address);


        sO.setHusband(husband);
        sO.setWife(wife);
        sO.addChild(child2);
        sO.addChild(child1);

        return sO;
    }

}