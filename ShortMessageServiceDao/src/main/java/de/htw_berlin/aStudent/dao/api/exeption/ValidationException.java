package de.htw_berlin.aStudent.dao.api.exeption;

import java.util.Set;

import javax.validation.ConstraintViolation;

/**
 * <p>
 * ValidationException class.
 * </p>
 * 
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
public class ValidationException extends RuntimeException {
	private static final long serialVersionUID = -4971115570135519005L;

	private static <T> String createMessage(Set<ConstraintViolation<T>> contraintViolations) {
		String message = "Invalid Bean found[\r\n";
		for (ConstraintViolation<?> violation : contraintViolations) {
			message += "\t" + violation.getLeafBean() + "#" + violation.getPropertyPath() + " " + violation.getMessage() + ",\r\n";
		}
		message += "]";
		return message;
	}

	/**
	 * <p>
	 * Constructor for ValidationException.
	 * </p>
	 * 
	 * @param contraintViolations
	 *            a {@link java.util.Set} object.
	 * @param <T>
	 *            a T object.
	 */
	public <T> ValidationException(Set<ConstraintViolation<T>> contraintViolations) {
		super(createMessage(contraintViolations));
	}

}