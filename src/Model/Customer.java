package Model;

public class Customer {
    private String name;
    private String phone;
    private String address;
    private int id;
    private String postalCode;
    private int divisionId;



    public Customer(int id, String name, String zip, String address, String phone, int dId) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.id = id;
        this.postalCode = zip;
        this.divisionId = dId;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return name;
    }

}
