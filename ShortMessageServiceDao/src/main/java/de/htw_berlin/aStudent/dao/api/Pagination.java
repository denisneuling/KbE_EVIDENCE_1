package de.htw_berlin.aStudent.dao.api;

/** <p>
* Pagination class.
* </p>
*
* @author Denis Neuling (denisneuling@gmail.com)
*
*/
public class Pagination {

	private Integer page;
	private Integer size;

    public Pagination(){}
	/**
	 * <p>
	 * Constructor for Pagination.
	 * </p>
	 *
	 * @param start
	 *            a {@link java.lang.Integer} object.
	 * @param end
	 *            a {@link java.lang.Integer} object.
	 */
	public Pagination(Integer page, Integer size) {
		this.page = page;
		this.size = size;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "Pagination [page=" + page + ", size=" + size + "]";
	}
}
