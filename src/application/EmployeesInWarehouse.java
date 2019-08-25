package application;

public class EmployeesInWarehouse {

	String warehouseID;
	String EmployeeID;
	String empName;
	
	public EmployeesInWarehouse(String warehouseID, String EmployeeID, String empName) {
		super();
		this.warehouseID = warehouseID;
		this.EmployeeID = EmployeeID;
		this.empName = empName;
	}

	public String getWarehouseID() {
		return warehouseID;
	}

	public void setWarehouseID(String warehouseID) {
		this.warehouseID = warehouseID;
	}

	public String getEmployeeID() {
		return EmployeeID;
	}

	public void setEmployeeID(String EmployeeID) {
		this.EmployeeID = EmployeeID;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

}
