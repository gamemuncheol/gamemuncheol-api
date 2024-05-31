package com.gamemoonchul.infrastructure.web.common;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class Pagination<T> {
    private Integer page; // 몇 번째 페이지를 보여줄지
    private Integer size; // 한 페이지에 보여줄 elements 수
    private Integer currentElements; // 현재 return된 elements 수
    private Integer totalPages; // 최대 페이지
    private Long totalElements; // 전체 elements 수
    private List<T> data;

    public Pagination(Page<T> page) {
        this.page = page.getNumber();
        this.size = page.getSize();
        this.currentElements = page.getNumberOfElements();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.data = page.getContent();
    }

    public Pagination(Page page, List<T> data) {
        this.page = page.getNumber();
        this.size = page.getSize();
        this.currentElements = page.getNumberOfElements();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.data = data;
    }
}
