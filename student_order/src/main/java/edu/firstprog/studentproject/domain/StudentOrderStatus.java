package edu.firstprog.studentproject.domain;

public enum StudentOrderStatus
{
    START, CHECKED;

    public static StudentOrderStatus fromValue (int value)
    {
        for (StudentOrderStatus sOstatus : StudentOrderStatus.values())
        {
            if (sOstatus.ordinal() == value)
            {
                return sOstatus;
            }
        }

        throw new RuntimeException("Unknown value : " + value );
    }
}
