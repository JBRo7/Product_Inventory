package Model;
/**
 *  Class InHouse.java
 */

/**
 *
 * @author Justin Brown
 */
public class InHouse extends Part {
    private int machineID;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        setId(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);

        this.machineID = machineID;
    }
    /**
     * @return the machineID
     */
    public int getMachineID() {
        return machineID;
    }
    /**
     * @param machineID the id to set
     */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }


}
