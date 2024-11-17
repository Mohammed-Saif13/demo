package pojo;

import java.util.List;

public class Orders 
{

	public List<OrderDetails> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderDetails> orders) {
		this.orders = orders;
	}

	private List<OrderDetails> orders;
	


}
