package br.com.southsytem.fileprocessor.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true, of = {"name"})
public class Salesman extends BaseEntity {

	private String cfp;
	
	private String name;
		
	private Double salary;

	private List<Sale> sales;
	
	public void addSale(Sale sale) {
		if(Objects.isNull(this.sales)) {
			sales = new ArrayList<>();
		}
		sales.add(sale);
	}
	
	public double getTotalOfSales() {
		return sales.stream()
			  .mapToDouble(Sale::getTotal)
		      .sum();
	}
	
}
