package Model;

public class InHouse extends Part {

    public InHouse(int partId, String name, double price, int stock, int min, int max, int machineId) {

        setPartId(partId);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
        setMachineId(machineId);
    }

    private int machineId;

    public void setMachineId(int id) {
        this.machineId = id;
    }

    public int getMachineId() {
        return machineId;
    }






}
