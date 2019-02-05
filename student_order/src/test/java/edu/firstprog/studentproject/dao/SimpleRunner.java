package edu.firstprog.studentproject.dao;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class SimpleRunner
{
    public static void main(String[] args)
    {
        SimpleRunner sr = new SimpleRunner();

        sr.runTests();
    }

    private void runTests() {
        try {

            Class cl = Class.forName("edu.firstprog.studentproject.dao.DictionaryDaoImplTest");

            Constructor constructor = cl.getConstructor();
            Object entity = constructor.newInstance();

            Method[] methods = cl.getMethods();
            for(Method m: methods)
            {
                Test annatation = m.getAnnotation(Test.class);
                if(annatation != null)
                {
                   m.invoke(entity);
                }
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
