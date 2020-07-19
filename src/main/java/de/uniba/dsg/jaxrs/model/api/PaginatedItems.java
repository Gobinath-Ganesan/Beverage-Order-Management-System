package de.uniba.dsg.jaxrs.model.api;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.net.URI;
import java.util.List;

@XmlRootElement
@XmlType(propOrder = {"pagination", "pageItems", "href"})
public class PaginatedItems<T> {
    private Pagination pagination;
    private List<T> pageItems;
    private URI href;

    public PaginatedItems() {

    }

    public PaginatedItems(final Pagination pagination,
                          final List<T> pageItems,
                          final URI href) {
        this.pagination = pagination;
        this.pageItems = pageItems;
        this.href = href;
    }

    public Pagination getPagination() {
        return this.pagination;
    }

    public void setPagination(final Pagination pagination) {
        this.pagination = pagination;
    }

    public List<T> getPageItems() {
        return this.pageItems;
    }

    public void setPageItems(final List<T> beverages) {
        this.pageItems = beverages;
    }

    public URI getHref() {
        return this.href;
    }

    public void setHref(final URI href) {
        this.href = href;
    }
}

