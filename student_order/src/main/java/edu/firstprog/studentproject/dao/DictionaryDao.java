package edu.firstprog.studentproject.dao;

import edu.firstprog.studentproject.domain.CountryArea;
import edu.firstprog.studentproject.domain.PassportOffice;
import edu.firstprog.studentproject.domain.RegisterOffice;
import edu.firstprog.studentproject.domain.Street;
import edu.firstprog.studentproject.exception.DaoException;

import java.util.List;

public interface DictionaryDao
{
    List<Street> findStreets(String pattern) throws DaoException;
    List<PassportOffice> findPassportOffices(String areaId) throws DaoException;
    List<RegisterOffice> findRegisterOffices(String areaId) throws DaoException;
    List<CountryArea> findAreas(String areaId) throws DaoException;

}
