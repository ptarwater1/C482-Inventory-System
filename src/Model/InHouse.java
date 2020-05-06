package Model;

public class InHouse extends Part {

    private int machineId;

    public InHouse(int partID, String name, double price, int numStock, int min, int max, int machineId) {

        setPartID(partID);
        setName(name);
        setPrice(price);
        setStock(numStock);
        setMin(min);
        setMax(max);
        setMachineId(machineId);
    }

    public void setMachineId(int id) {
        this.machineId = id;
    }

    public int getMachineId() {
        return machineId;
    }






}
