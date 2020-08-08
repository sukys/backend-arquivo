package br.com.southsytem.fileprocessor.dao;

import java.util.Comparator;

import br.com.southsytem.fileprocessor.entity.Salesman;

/**
 * Class to Simulate a table with Salesman data.
 * @author luis
 *
 */
public class SalesmanDao extends BaseDao<Salesman> {

	/**
	 * Retrieves the Salesman with the informed name.
	 * @return
	 */
	public Salesman findByName(String name) {
		return list.stream().filter(v -> name.equalsIgnoreCase(v.getName())).findFirst().get() ;
	}
	
	/**
	 * Retrieves the worst Salesman (with smallest ammount of sales) in the list.
	 * @return
	 */
	public Salesman getWorstSalesman() {
		return list.stream().min(Comparator.comparing(Salesman::getTotalOfSales)).get();
	}

	/**
	 * Retrieves the best Salesman (with greatest ammount of sales) in the list.
	 * @return
	 */
	public Salesman getBestSalesman() {
		return list.stream().max(Comparator.comparing(Salesman::getTotalOfSales)).get();
	}
	

}
