package Model;

public class InHouse extends Part {

    private int machineId;

    public InHouse(int partId, String name, double price, int stock, int max, int min, int machineId) {

        setPartId(partId);
        setName(name);
        setPrice(price);
        setPartStock(stock);
        setMax(max);
        setMin(min);
        setMachineId(machineId);
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int id) {
        this.machineId = id;
    }

}
