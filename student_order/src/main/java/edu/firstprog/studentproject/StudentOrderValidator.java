package edu.firstprog.studentproject;

import edu.firstprog.studentproject.Mail.MailSender;
import edu.firstprog.studentproject.Validator.ChildrenValidator;
import edu.firstprog.studentproject.Validator.CityRegisterValidator;
import edu.firstprog.studentproject.Validator.StudentValidator;
import edu.firstprog.studentproject.Validator.WeddingValidator;
import edu.firstprog.studentproject.dao.StudentDaoImpl;
import edu.firstprog.studentproject.domain.*;
import edu.firstprog.studentproject.domain.children.AnswerChildren;
import edu.firstprog.studentproject.domain.register.AnswerCityRegister;
import edu.firstprog.studentproject.domain.student.AnswerStudent;
import edu.firstprog.studentproject.domain.wedding.AnswerWedding;
import edu.firstprog.studentproject.exception.DaoException;

import java.util.LinkedList;
import java.util.List;

public class StudentOrderValidator
{
    private CityRegisterValidator cityRegisterVal;
    private WeddingValidator weddingVal;
    private ChildrenValidator childrenVal;
    private StudentValidator studentVal;
    private MailSender mailSender;

    public StudentOrderValidator ()
    {
        cityRegisterVal = new CityRegisterValidator();
        weddingVal = new WeddingValidator();
        childrenVal = new ChildrenValidator();
        studentVal = new StudentValidator();
        mailSender = new MailSender();
    }

    public static void main(String[] args)
    {
        StudentOrderValidator sov = new StudentOrderValidator();
        sov.checkAll();
    }

    void checkAll()
    {
        try {
            List<StudentOrder> soList = readStudentOrders();

            for (StudentOrder sO : soList) {
                checkOneOrder(sO);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<StudentOrder> readStudentOrders() throws DaoException {
        return new StudentDaoImpl().getStudentOrders();
    }

    public void checkOneOrder(StudentOrder sO)
    {
        AnswerCityRegister cityAnswer = checkCityRegister(sO);
     //   AnswerWedding weddingAnswer = checkWedding(sO);
     //   AnswerChildren childAnswer = checkChildren(sO);
     //   AnswerStudent studentAnswer = checkStudent(sO);
     //   sendMail(sO);
    }

    public AnswerCityRegister checkCityRegister (StudentOrder sO)
    {
        return cityRegisterVal.checkCityRegister(sO);
    }

    public AnswerWedding checkWedding(StudentOrder sO)
    {
        return weddingVal.checkWedding(sO);
    }

    public AnswerChildren checkChildren(StudentOrder sO)
    {
        return childrenVal.checkChildren(sO);
    }

    public AnswerStudent checkStudent(StudentOrder sO)
    {
        return studentVal.checkStudent(sO);
    }

    public void sendMail(StudentOrder sO)
    {
        mailSender.sendMail(sO);
    }
}
