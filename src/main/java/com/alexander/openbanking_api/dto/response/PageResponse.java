package com.alexander.openbanking_api.dto.response;

import lombok.*;

import java.util.List;

// generic paginated response
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponse<T> {

    // records on the current page
    private List<T> content;

    // current page number
    private int page;

    // page size
    private int size;

    // total records
    private long totalElements;

    // total pages
    private int totalPages;

    // first page?
    private boolean first;

    // last page?
    private boolean last;

}