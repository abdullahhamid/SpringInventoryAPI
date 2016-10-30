package inventoryapi;

public class Item {

	private long id;
    private String itemName;
    private int quantity;

    public Item(long id, String itemName, int quantity) {
        this.id = id;
        this.itemName = itemName;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format(
                "Item[id=%d, itemName='%s', quantity=%d]",
                id, itemName, quantity);
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
