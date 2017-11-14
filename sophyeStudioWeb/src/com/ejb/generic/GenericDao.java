package com.ejb.generic;

public interface GenericDao<E> {
	/**
	 * Metodo para guardar / editar una entidad
	 * 
	 * @param entity
	 * @return
	 */
	boolean merge(E entity);

	/**
	 * Metodo para eliminar una entidad
	 * 
	 * @param entity
	 * @return
	 */
	void remove(E entity) throws Exception;
}