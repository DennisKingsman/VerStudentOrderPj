package edu.firstprog.studentproject.domain;

public class PassportOffice
{
    private Long officeID;
    private String officeAreaID;
    private String officeName;

    public PassportOffice () {}

    public PassportOffice (Long officeID, String officeAreaID, String officeName)
    {
        this.officeID = officeID;
        this.officeAreaID = officeAreaID;
        this.officeName = officeName;
    }

    public void setOfficeID (Long officeID){this.officeID = officeID; }

    public long getOfficeID() { return officeID; }

    public void setOfficeAreaID (String officeAreaID) {this.officeAreaID = officeAreaID; }

    public String getOfficeAreaID() { return officeAreaID; }

    public String getOfficeName() { return officeName; }

    public void setOfficeName(String officeName) { this.officeName = officeName; }

    @Override
    public String toString() {
        return "PassportOffice{" +
                "officeID=" + officeID +
                ", officeAreaID='" + officeAreaID + '\'' +
                ", officeName='" + officeName + '\'' +
                '}';
    }
}
