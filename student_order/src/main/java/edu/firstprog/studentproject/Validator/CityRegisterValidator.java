package edu.firstprog.studentproject.Validator;

import edu.firstprog.studentproject.Validator.register.CityRegisterChecker;
import edu.firstprog.studentproject.Validator.register.FakeCityRegisterChecker;
import edu.firstprog.studentproject.domain.Person;
import edu.firstprog.studentproject.domain.register.AnswerCityRegister;
import edu.firstprog.studentproject.domain.Child;
import edu.firstprog.studentproject.domain.register.AnswerCityRegisterItem;
import edu.firstprog.studentproject.domain.register.CityRegisterResponse;
import edu.firstprog.studentproject.domain.StudentOrder;
import edu.firstprog.studentproject.exception.CityRegisterException;
import edu.firstprog.studentproject.exception.TransportException;

import java.util.List;

public class CityRegisterValidator
{
    public static final String IN_CODE = "NO_GRN";

    private CityRegisterChecker personChecker;

    public CityRegisterValidator()
    {
        personChecker = new FakeCityRegisterChecker();
    }

    public AnswerCityRegister checkCityRegister (StudentOrder sO)
    {
        AnswerCityRegister ans = new AnswerCityRegister();

        ans.addItem(checkPerson(sO.getHusband()));
        ans.addItem(checkPerson(sO.getWife()));

        for (Child child: sO.getChildren())
        {
            ans.addItem(checkPerson(child));
        }

        return ans;
    }

    private AnswerCityRegisterItem checkPerson(Person person)
    {
        AnswerCityRegisterItem.CityStatus status;
        AnswerCityRegisterItem.CityError error = null;

        try
        {

            CityRegisterResponse tmp = personChecker.checkPerson(person);
            status = tmp.isExisting() ? AnswerCityRegisterItem.CityStatus.YES :
                                        AnswerCityRegisterItem.CityStatus.NO;

        }   catch (CityRegisterException e)
        {
            e.printStackTrace(System.out);
            status = AnswerCityRegisterItem.CityStatus.ERROR;
            error = new AnswerCityRegisterItem.CityError(e.getCode(), e.getMessage());
        }   catch (TransportException e)
        {
            e.printStackTrace(System.out);
            status = AnswerCityRegisterItem.CityStatus.ERROR;
            error = new AnswerCityRegisterItem.CityError(IN_CODE, e.getMessage());
        }   catch (Exception e)
        {
            e.printStackTrace(System.out);
            status = AnswerCityRegisterItem.CityStatus.ERROR;
            error = new AnswerCityRegisterItem.CityError(IN_CODE, e.getMessage());
        }

        AnswerCityRegisterItem ans = new AnswerCityRegisterItem(status, person, error);

        return ans;
    }
}
