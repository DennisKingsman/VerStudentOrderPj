package edu.firstprog.studentproject.domain;

public class University
{
    private Long universityId;
    private String universityName;

    public University () {}

    public University (Long universityId, String universityName)
    {
        this.universityId = universityId;
        this.universityName = universityName;
    }

    public void setUniversityId (Long universityId) {
        this.universityId = universityId;
    }

    public Long getUniversityId () {
        return universityId;
    }

    public  void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getUniversityName () {
        return universityName;
    }

    @Override
    public String toString() {
        return "University{" +
                "universityId=" + universityId +
                ", universityName='" + universityName + '\'' +
                '}';
    }
}
