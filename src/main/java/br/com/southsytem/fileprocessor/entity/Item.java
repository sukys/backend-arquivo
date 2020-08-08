package br.com.southsytem.fileprocessor.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {

	private Integer itemId;
	
	private Integer quantity;
	
	private Double price;
	
	public Double getTotal() {
		return this.quantity * this.price;
	}
	
}
