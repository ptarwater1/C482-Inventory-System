package Model;

public class OutSourced extends Part {

    public OutSourced(int partId, String name, double price, int stock, int min, int max, String companyName) {

        setPartId(partId);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
        setCompanyName(companyName);
    }

    private String companyName;

    public void setCompanyName(String name) {
        this.companyName = name;
    }

    public String getCompanyName() {
        return companyName;
    }
}
