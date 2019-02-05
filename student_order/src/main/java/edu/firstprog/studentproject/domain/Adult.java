package edu.firstprog.studentproject.domain;

import edu.firstprog.studentproject.domain.Person;

import java.time.LocalDate;

public class Adult extends Person
{
    private String passportSeria;
    private String passportNumber;
    private LocalDate issueDate;
    private PassportOffice issueDepartment;
    private University university;
    private String studentID;

    public Adult() {}

    public Adult(String surName, String givenName, String patronymic, LocalDate dateOfBirth)
    {
        super(surName, givenName, patronymic, dateOfBirth);

    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String stringID) {
        this.studentID = stringID;
    }

    public String getPassportSeria() {
        return passportSeria;
    }

    public void setPassportSeria(String passportSeria) {
        this.passportSeria = passportSeria;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public PassportOffice getIssueDepartment() {
        return issueDepartment;
    }

    public void setIssueDepartment(PassportOffice issueDepartment) {
        this.issueDepartment = issueDepartment;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    @Override
    public String toString() {
        return "Adult{" +
                "passportSeria='" + passportSeria + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                ", issueDate=" + issueDate +
                ", issueDepartment=" + issueDepartment +
                ", university=" + university +
                ", studentID='" + studentID + '\'' +
                "} " + super.toString();
    }
}
