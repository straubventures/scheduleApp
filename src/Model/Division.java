package Model;

public class Division {
    private int countryId;
    private int divisionId;
    private String division;

    public Division(int countryId, int divisionId, String division) {
        this.countryId = countryId;
        this.divisionId = divisionId;
        this.division = division;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    @Override
    public String toString() {
        return division;
    }
}
