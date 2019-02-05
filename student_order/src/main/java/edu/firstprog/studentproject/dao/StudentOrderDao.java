package edu.firstprog.studentproject.dao;

import edu.firstprog.studentproject.domain.StudentOrder;
import edu.firstprog.studentproject.exception.DaoException;

import java.util.List;

public interface StudentOrderDao
{
    Long saveStudentOrder(StudentOrder s0) throws DaoException;

    List<StudentOrder> getStudentOrders() throws DaoException;
}
