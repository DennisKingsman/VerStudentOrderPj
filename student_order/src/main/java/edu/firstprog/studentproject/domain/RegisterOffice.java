package edu.firstprog.studentproject.domain;

public class RegisterOffice
{
    private Long officeId;
    private String officeAreaID;
    private String officeName;

    public RegisterOffice () {};

    public RegisterOffice (Long officeId, String officeAreaID, String officeName)
    {
        this.officeId = officeId;
        this.officeAreaID = officeAreaID;
        this.officeName = officeName;
    }

    public Long getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Long officeId) {
        this.officeId = officeId;
    }

    public String getOfficeAreaID() {
        return officeAreaID;
    }

    public void setOfficeAreaID(String officeAreaID) {
        this.officeAreaID = officeAreaID;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    @Override
    public String toString() {
        return "RegisterOffice{" +
                "officeId=" + officeId +
                ", officeAreaID='" + officeAreaID + '\'' +
                ", officeName='" + officeName + '\'' +
                '}';
    }
}
