package de.uniba.dsg.jaxrs.Resources;

import de.uniba.dsg.jaxrs.model.api.Pagination;

import javax.ws.rs.core.UriInfo;
import java.util.List;

public class PaginationHelper<T> {

    private final List<T> items;
    private int startIndex;
    private int endIndex;

    public PaginationHelper(final List<T> items) {
        this.startIndex = 0;
        this.endIndex = 0;
        this.items = items;
    }

    public Pagination getPagination(final UriInfo info, int page, final int pageLimit) {
        // pagination
        final int itemSize = this.items.size();
        this.startIndex = (page - 1) * pageLimit;
        if (this.startIndex > itemSize) {
            this.startIndex = 0;
            page = 1;
        }
        this.endIndex = this.startIndex + pageLimit;
        if (this.endIndex > itemSize) {
            this.endIndex = itemSize;
        }

        return new Pagination(info.getAbsolutePath(), page, pageLimit, itemSize);
    }

    public List<T> getPaginatedList() {
        return this.items.subList(this.startIndex, this.endIndex);
    }
}
