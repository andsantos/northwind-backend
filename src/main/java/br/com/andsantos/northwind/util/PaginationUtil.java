package br.com.andsantos.northwind.util;

import org.springframework.data.domain.Page;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

public class PaginationUtil {

    private PaginationUtil() {}

    public static <T> Pagina<T> paginar(Page<T> page) {
        UriComponentsBuilder uriBuilder = ServletUriComponentsBuilder.fromCurrentRequest();
        Pagina<T> pagina = new Pagina<>();

        int pageNumber = page.getNumber();
        int pageSize = page.getSize();

        if (pageNumber < page.getTotalPages() - 1) {
            pagina.setNext(preparePageUri(uriBuilder, pageNumber + 1, pageSize));
        }

        if (pageNumber > 0) {
            pagina.setPrev(preparePageUri(uriBuilder, pageNumber - 1, pageSize));
        }

        pagina.setTotalPages(page.getTotalPages());
        pagina.setCurrentPage(page.getNumber());
        pagina.setSize(page.getNumberOfElements());
        pagina.setTotalItems(page.getTotalElements());
        pagina.setResults(page.getContent());
        return pagina;
    }

    private static String preparePageUri(UriComponentsBuilder uriBuilder, int pageNumber, int pageSize) {
        return uriBuilder.replaceQueryParam("page", Integer.toString(pageNumber))
            .replaceQueryParam("size", Integer.toString(pageSize))
            .toUriString()
            .replace(",", "%2C")
            .replace(";", "%3B");
    }
}
