package br.com.southsytem.fileprocessor.service;

import java.util.ArrayList;
import java.util.List;

import br.com.southsytem.fileprocessor.dao.SaleDao;
import br.com.southsytem.fileprocessor.entity.Item;
import br.com.southsytem.fileprocessor.entity.Sale;

/**
 * Class with the Sale rules 
 * @author luis
 *
 */
public class SaleService {

	SaleDao dao = new SaleDao();
	
	public SaleService() {
		this.resetDatabase();
	}

	/**
	 * Clean all the data to compute a new file.
	 */
	public void resetDatabase() {
		dao.resetDatabase();
	}
	
	/**
	 * Convert a received line in a instance of Sale and save it.
	 * @param data
	 */
	public void convertAndSave(String[] data) {
		dao.save(new Sale(Integer.parseInt(data[1]), getItens(data[2]), data[3]));
	}
	
	/**
	 * Count the ammount of Sale registered.
	 * @return
	 */
	public int count() {
		return dao.getNumberOfRecords();
	}
	
	/**
	 * Retrieves the greatest Sale
	 * @return
	 */
	public Sale getGreatestSale() {
		return dao.getGreatestSale();
	}
	
	
	/**
	 * Retrieves all records in the list.
	 * @return
	 */
	public List<Sale> findAll(){
		return dao.listAll();
	}
	
	
	/**
	 * Convert a received list of itens in a List of Item.
	 * @param data
	 */
	private List<Item> getItens(String line){
		List<Item> itens = new ArrayList<>();
		String[] records = line.replace("[","").replace("]", "").split(",");
		for (String record : records) {
			String[] item = record.split("-");
			itens.add(new Item(Integer.parseInt(item[0]), Integer.parseInt(item[1]), Double.parseDouble(item[2])));
		}
		return itens;
	}
	
	
}
