package br.com.southsytem.fileprocessor.dao;

import java.util.Comparator;

import br.com.southsytem.fileprocessor.entity.Sale;

/**
 * Class to Simulate a table with Sales sata.
 * @author luis
 *
 */
public class SaleDao extends BaseDao<Sale> {

	/**
	 * Retrieves the Sale with greatest Value in the list.
	 * @return
	 */
	public Sale getGreatestSale() {
		return list.stream().max(Comparator.comparing(Sale::getTotal)).get();
	}
	
}
