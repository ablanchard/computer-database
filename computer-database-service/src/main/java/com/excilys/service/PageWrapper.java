package com.excilys.service;

import org.springframework.data.domain.Page;

/**
 * Created by excilys on 22/04/14.
 */
public class PageWrapper<T>  {

    private Page<T> page ;

    private String search;

    public PageWrapper(Page<T> page,String search){
        this.page = page;
        this.search = search;
    }

    public Page<T> getPage() {
        return page;
    }

    public void setPage(Page<T> page) {
        this.page = page;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }


}
