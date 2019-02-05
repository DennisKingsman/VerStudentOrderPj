package edu.firstprog.studentproject;

import edu.firstprog.studentproject.dao.DictionaryDao;
import edu.firstprog.studentproject.dao.DictionaryDaoImpl;
import edu.firstprog.studentproject.dao.StudentDaoImpl;
import edu.firstprog.studentproject.dao.StudentOrderDao;
import edu.firstprog.studentproject.domain.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

public class SaveStudentOrder
{
    static long saveStudentOrder (StudentOrder studentOrder)
    {
        long answer = 199;
        System.out.println("saveStudentOrder: ");

        return answer;
    }
 }
