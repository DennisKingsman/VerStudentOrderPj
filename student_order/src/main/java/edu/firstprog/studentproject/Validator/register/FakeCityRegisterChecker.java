package edu.firstprog.studentproject.Validator.register;

import edu.firstprog.studentproject.domain.Adult;
import edu.firstprog.studentproject.domain.Child;
import edu.firstprog.studentproject.domain.register.CityRegisterResponse;
import edu.firstprog.studentproject.domain.Person;
import edu.firstprog.studentproject.exception.CityRegisterException;
import edu.firstprog.studentproject.exception.TransportException;

public class FakeCityRegisterChecker implements CityRegisterChecker
{
    public static final String GOOD_1 = "1000";
    public static final String GOOD_2 = "2000";

    public static final String BAD_1 = "1001";
    public static final String BAD_2 = "2001";

    public static final String ERROR_1 = "1002";
    public static final String ERROR_2 = "2002";

    public static final String ERROR_T_1 = "1003";
    public static final String ERROR_T_2 = "2003";

    public CityRegisterResponse checkPerson(Person person)
            throws CityRegisterException, TransportException
    {
        CityRegisterResponse result = new CityRegisterResponse();

        if (person instanceof Adult)
        {
            Adult t = (Adult)person;
            String ps = t.getPassportSeria();
            if (ps.equals(GOOD_1) || ps.equals(GOOD_2))
            {
                result.setExisting(true);
                result.setTemporal(false);
            }
            if (ps.equals(BAD_1) || ps.equals(BAD_2))
            {
                result.setExisting(false);
            }
            if (ps.equals(ERROR_1) || ps.equals(ERROR_2))
            {
                CityRegisterException ex = new CityRegisterException("1", "GRN Error" + ps);
                throw ex;
            }
            if (ps.equals(ERROR_T_1) || ps.equals(ERROR_T_2))
            {
                TransportException ex = new TransportException("Transport Error" + ps);
                throw ex;
            }
        }

        if (person instanceof Child)
        {
            result.setTemporal(true);
            result.setExisting(true);
        }

        System.out.println(result);

        return result;
    }
}
