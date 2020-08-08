package br.com.southsytem.fileprocessor.service;

import java.util.ArrayList;
import java.util.List;

import br.com.southsytem.fileprocessor.dao.SaleDao;
import br.com.southsytem.fileprocessor.dao.SalesmanDao;
import br.com.southsytem.fileprocessor.entity.Sale;
import br.com.southsytem.fileprocessor.entity.Salesman;

/**
 * Class with the Salesman rules 
 * @author luis
 *
 */
public class SalesmanService {

	SalesmanDao dao = new SalesmanDao();
	
	SaleDao saleDao = new SaleDao();
	
	public SalesmanService() {
		this.resetDatabase();
	}

	/**
	 * Clean all the data to compute a new file.
	 */
	public void resetDatabase() {
		dao.resetDatabase();
	}
	
	
	/**
	 * Convert a received line in a instance of Salesman and save it.
	 * @param data
	 */
	public void convertAndSave(String[] data) {
		dao.save(new Salesman(data[1], data[2], Double.parseDouble(data[3]), new ArrayList<>()));
	}
	
	
	/**
	 * Count the ammount of Salesman registered.
	 * @return
	 */
	public int count() {
		return dao.getNumberOfRecords();
	}
	
	
	/**
	 * Associate the Sales with the Salesman
	 */
	public void associateSalesWithSalesman() {
		for (Sale sale : saleDao.listAll()) {
			Salesman salesman = dao.findByName(sale.getSalesman());
			salesman.addSale(sale);
			dao.save(salesman);
		}
	}
	
	/**
	 * Get the worst Salesman
	 * @return
	 */
	public Salesman getWorstSalesman() {
		return dao.getWorstSalesman();
	}
	
	/**
	 * Get the best Salesman
	 * @return
	 */
	public Salesman getBestSalesman() {
		return dao.getBestSalesman();
	}
	
	/**
	 * Retrieves all records in the list.
	 * @return
	 */
	public List<Salesman> findAll(){
		return dao.listAll();
	}
}
