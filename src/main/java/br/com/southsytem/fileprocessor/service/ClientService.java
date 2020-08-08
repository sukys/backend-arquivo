package br.com.southsytem.fileprocessor.service;

import java.util.List;

import br.com.southsytem.fileprocessor.dao.ClientDao;
import br.com.southsytem.fileprocessor.entity.Client;
/**
 * Class with the 'Client' rules 
 * @author luis
 *
 */
public class ClientService {

	ClientDao dao = new ClientDao();
	
	
	public ClientService() {
		this.resetDatabase();
	}

	/**
	 * Clean all the data to compute a new file.
	 */
	public void resetDatabase() {
		dao.resetDatabase();
	}
	
	/**
	 * Convert a received line in a instance of Client and save it.
	 * @param data
	 */
	public void convertAndSave(String[] data) {
		dao.save(new Client(data[1], data[2], data[3]));
	}
	
	
	/**
	 * Count the ammount of 'Client' registered.
	 * @return
	 */
	public int count() {
		return dao.getNumberOfRecords();
	}
	
	
	/**
	 * Retrieves all records in the list.
	 * @return
	 */
	public List<Client> findAll(){
		return dao.listAll();
	}
	
	
}
