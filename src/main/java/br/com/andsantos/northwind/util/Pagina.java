package br.com.andsantos.northwind.util;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pagina<T> {
    private long totalPages;
    private long currentPage;
    private long size;
    private long totalItems;
    private String prev;
    private String next;
    private List<T> results;
}
