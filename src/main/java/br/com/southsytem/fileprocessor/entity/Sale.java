package br.com.southsytem.fileprocessor.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Sale extends BaseEntity {

	private Integer saleId;

	private List<Item> products;
	
	private String salesman;

	public double getTotal() {
		return products.stream()
				.mapToDouble(Item::getTotal)
				.sum();
	}

}
