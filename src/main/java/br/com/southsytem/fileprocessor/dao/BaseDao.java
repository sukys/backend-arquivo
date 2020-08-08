package br.com.southsytem.fileprocessor.dao;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.southsytem.fileprocessor.entity.BaseEntity;


/**
 * Class with Generic methods used by all instances of DAO's
 * @author luis
 *
 * @param <T>
 */
public abstract class BaseDao<T extends BaseEntity>  {

	Set<T> list = new HashSet<>();
		
	public BaseDao() {
		resetDatabase();
	}

	/**
	 * Clear the list to process a new File.
	 */
	public void resetDatabase() {
		this.list = new HashSet<>();
	}
	
	/**
	 * Retrieves the next 'id', according with exiting ones.
	 * @return
	 */
	private int getNextId() {
		return list.isEmpty() ? 1 : list.stream().max(Comparator.comparing(T::getId)).get().getId() + 1;
	}
	
	
	/**
	 * Insert or update a entity in the list.
	 * @param entity
	 * @return
	 */
	public T save(T entity) {
		if(entity.getId() == null) {
			entity.setId(getNextId());
		} 
		list.add(entity);
		return entity;
	}
	
	/**
	 * Count the num ber of records in the list.
	 * @return
	 */
	public int getNumberOfRecords() {
		return list.size();
	}
	
	
	/**
	 * Retrieves all records in the list.
	 * @return
	 */
	public List<T> listAll(){
		return this.list.stream().collect(Collectors.toList());
	}
}
