package edu.firstprog.studentproject.domain;

public class Street
{
    private Long streetCode;
    private String streetName;

    public Street() {}

    public Street(Long streetCode, String streetName)
    {
        this.streetCode = streetCode;
        this.streetName = streetName;
    }

    public void setStreetCode(Long streetCode) { this.streetCode = streetCode; }

    public Long getStreetCode() { return streetCode; }

    public void setStreetName(String streetName) {this.streetName = streetName; }

    public String getStreetName() {return streetName;}

    @Override
    public String toString() {
        return "Street{" +
                "streetCode=" + streetCode +
                ", streetName='" + streetName + '\'' +
                '}';
    }
}
