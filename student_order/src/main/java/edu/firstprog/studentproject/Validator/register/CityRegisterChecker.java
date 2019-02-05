package edu.firstprog.studentproject.Validator.register;

import edu.firstprog.studentproject.domain.register.CityRegisterResponse;
import edu.firstprog.studentproject.domain.Person;
import edu.firstprog.studentproject.exception.CityRegisterException;
import edu.firstprog.studentproject.exception.TransportException;

public interface CityRegisterChecker
{
    CityRegisterResponse checkPerson(Person person)
            throws CityRegisterException, TransportException;
}
