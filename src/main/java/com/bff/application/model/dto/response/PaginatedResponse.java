package com.bff.application.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedResponse<T> {

    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean last;

    public static <T> PaginatedResponse<T> of(List<T> content, int page, int size) {
        int totalPages = (int) Math.ceil((double) content.size() / size);
        int fromIndex = Math.min(page * size, content.size());
        int toIndex = Math.min(fromIndex + size, content.size());
        List<T> pageContent = content.subList(fromIndex, toIndex);
        return PaginatedResponse.<T>builder()
                .content(pageContent)
                .page(page)
                .size(size)
                .totalElements(content.size())
                .totalPages(totalPages)
                .last(toIndex >= content.size())
                .build();
    }

}
