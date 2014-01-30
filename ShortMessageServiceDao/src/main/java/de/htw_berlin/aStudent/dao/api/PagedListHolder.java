package de.htw_berlin.aStudent.dao.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonView;

import de.htw_berlin.aStudent.model.api.view.BaseView;

public class PagedListHolder<E> implements Serializable {
	private static final long serialVersionUID = 5946205161900472507L;

	public static final int DEFAULT_PAGE_SIZE = 10;

	public static final int DEFAULT_MAX_LINKED_PAGES = 10;

	@JsonView(BaseView.class)
	private List<E> result;

	@JsonView(BaseView.class)
	private Date refreshDate;

	@JsonView(BaseView.class)
	private int pageSize = DEFAULT_PAGE_SIZE;

	@JsonView(BaseView.class)
	private int page = 0;

	@JsonView(BaseView.class)
	private int maxLinkedPages = DEFAULT_MAX_LINKED_PAGES;


	/**
	 * Create a new holder instance.
	 * You'll need to set a source list to be able to use the holder.
	 * @see #setSource
	 */
	public PagedListHolder() {
		this(new ArrayList<E>(0));
	}

	/**
	 * Create a new holder instance with the given source list, starting with
	 * a default sort definition (with "toggleAscendingOnProperty" activated).
	 * @param source the source List
	 * @see MutableSortDefinition#setToggleAscendingOnProperty
	 */
	public PagedListHolder(List<E> source) {
		this.setResult(source);
	}

	/**
	 * Set the source list for this holder.
	 */
	public void setResult(List<E> source) {
		Assert.notNull(source, "Source List must not be null");
		this.result = source;
		this.refreshDate = new Date();
	}

	/**
	 * Return the source list for this holder.
	 */
	public List<E> getResult() {
		return this.result;
	}

	/**
	 * Return the last time the list has been fetched from the source provider.
	 */
	public Date getRefreshDate() {
		return this.refreshDate;
	}

	/**
	 * Set the current page size.
	 * Resets the current page number if changed.
	 * <p>Default value is 10.
	 */
	public void setPageSize(int pageSize) {
		if (pageSize != this.pageSize) {
			this.pageSize = pageSize;
		}
	}

	/**
	 * Return the current page size.
	 */
	public int getPageSize() {
		return this.pageSize;
	}

	/**
	 * Set the current page number.
	 * Page numbering starts with 0.
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * Return the current page number.
	 * Page numbering starts with 0.
	 */
	public int getPage() {
		if(result==null || result.isEmpty()){
			return -1;
		}
		return this.page;
	}

	/**
	 * Set the maximum number of page links to a few pages around the current one.
	 */
	public void setMaxLinkedPages(int maxLinkedPages) {
		this.maxLinkedPages = maxLinkedPages;
	}

	/**
	 * Return the maximum number of page links to a few pages around the current one.
	 */
	public int getMaxLinkedPages() {
		return this.maxLinkedPages;
	}


	/**
	 * Return the number of pages for the current source list.
	 */
	public int getPageCount() {
		float nrOfPages = (float) getNrOfElements() / getPageSize();
		return (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages);
	}

	/**
	 * Return if the current page is the first one.
	 */
	public boolean isFirstPage() {
		return getPage() == 0;
	}

	/**
	 * Switch to previous page.
	 * Will stay on first page if already on first page.
	 */
	public void previousPage() {
		if (!isFirstPage()) {
			this.page--;
		}
	}

	/**
	 * Return the total number of elements in the source list.
	 */
	public int getNrOfElements() {
		return getResult().size();
	}

	/**
	 * Return the element index of the first element on the current page.
	 * Element numbering starts with 0.
	 */
	public int getFirstElementOnPage() {
		return (getPageSize() * getPage());
	}

	/**
	 * Return the element index of the last element on the current page.
	 * Element numbering starts with 0.
	 */
	public int getLastElementOnPage() {
		int endIndex = getPageSize() * (getPage() + 1);
		int size = getNrOfElements();
		return (endIndex > size ? size : endIndex) - 1;
	}

	/**
	 * Return a sub-list representing the current page.
	 */
	public List<E> getPageList() {
		return getResult().subList(getFirstElementOnPage(), getLastElementOnPage() + 1);
	}

	/**
	 * Return the first page to which create a link around the current page.
	 */
	public int getFirstLinkedPage() {
		return Math.max(0, getPage() - (getMaxLinkedPages() / 2));
	}
}