package edu.firstprog.studentproject.Validator;

import edu.firstprog.studentproject.domain.wedding.AnswerWedding;
import edu.firstprog.studentproject.domain.StudentOrder;

public class WeddingValidator
{
    public AnswerWedding checkWedding (StudentOrder sO)
    {
        System.out.println("Wedding is running");
        return new AnswerWedding();
    }
}
