package edu.firstprog.studentproject.Validator;

import edu.firstprog.studentproject.domain.children.AnswerChildren;
import edu.firstprog.studentproject.domain.StudentOrder;

public class ChildrenValidator
{
    public AnswerChildren checkChildren (StudentOrder sO)
    {
        System.out.println("Children is running");
        return new AnswerChildren();
    }
}
