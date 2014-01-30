package de.htw_berlin.aStudent.model.api;

import java.io.Serializable;

/**
 * <p>
 * Abstract AbstractModel class.
 * </p>
 * 
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
@SuppressWarnings("serial")
public abstract class AbstractModel<T extends AbstractModel<T, ID>, ID extends Serializable> implements Serializable {

	public AbstractModel(){}
	
	public abstract ID getId();
}
