package application;

public class ProductInWarehouse {

	String warehouseID;
	String productID;
	String quantity;
	
	public ProductInWarehouse(String warehouseID, String productID, String quantity) {
		super();
		this.warehouseID = warehouseID;
		this.productID = productID;
		this.quantity = quantity;
	}

	public String getWarehouseID() {
		return warehouseID;
	}

	public void setWarehouseID(String warehouseID) {
		this.warehouseID = warehouseID;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	
}
