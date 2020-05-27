package Model;

public class OutSourced extends Part {

    private String companyName;

    public OutSourced(int partId, String name, double price, int stock, int max, int min, String company) {
        setPartId(partId);
        setName(name);
        setPrice(price);
        setPartStock(stock);
        setMax(max);
        setMin(min);
        setCompanyName(company);
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String name) {
        this.companyName = name;
    }

}
