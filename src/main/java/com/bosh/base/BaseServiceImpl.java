/**
 * 
 */
package com.bosh.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wangmt
 * @date 2018年9月20日
 */
@SuppressWarnings("rawtypes")
public class BaseServiceImpl<E extends JpaRepository, T> implements BaseService<T> {
	
	@Autowired
	protected E repository;

	/* (non-Javadoc)
	 * @see com.bosh.base.BaseService#findOne(int)
	 */
	@SuppressWarnings("unchecked")
	public T findOne(int key) {
		return (T) repository.findById(key).get();
	}

	/* (non-Javadoc)
	 * @see com.bosh.base.BaseService#save(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public T save(T entity) {
		return (T) repository.save(entity);
	}

	/* (non-Javadoc)
	 * @see com.bosh.base.BaseService#delete(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public void delete(Object key) {
		repository.delete(key);

	}

	/* (non-Javadoc)
	 * @see com.bosh.base.BaseService#findAll()
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return repository.findAll();
	}

	/* (non-Javadoc)
	 * @see com.bosh.base.BaseService#deleteInBatch(java.lang.Iterable)
	 */
	@SuppressWarnings("unchecked")
	public void deleteInBatch(Iterable<T> iterable) {
		repository.deleteInBatch(iterable);

	}

	/* (non-Javadoc)
	 * @see com.bosh.base.BaseService#findAll(java.lang.Iterable)
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll(Iterable<Integer> iterable) {
		/**   repository.findAll(iterable);   */
		return repository.findAllById(iterable);
	}

	/* (non-Javadoc)
	 * @see com.bosh.base.BaseService#save(java.lang.Iterable)
	 */
	@SuppressWarnings("unchecked")
	public List<T> save(Iterable<T> iterable) {
		return (List<T>) repository.save(iterable);
	}

}
