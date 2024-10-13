package com.itbk.Outlet_Store.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PageResult<T> {

    private List<T> items;
    private int totalPages;

    public PageResult(List<T> items, int totalPages) {
        this.items = items;
        this.totalPages = totalPages;
    }
}
