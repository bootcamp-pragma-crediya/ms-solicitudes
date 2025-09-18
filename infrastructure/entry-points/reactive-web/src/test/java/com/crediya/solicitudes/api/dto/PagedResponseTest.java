package com.crediya.solicitudes.api.dto;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PagedResponseTest {

    @Test
    void shouldCreatePagedResponse() {
        List<String> content = List.of("item1", "item2");
        PagedResponse<String> response = new PagedResponse<>(
                content, 0, 10, 2, 1
        );

        assertEquals(content, response.content());
        assertEquals(0, response.page());
        assertEquals(10, response.size());
        assertEquals(2, response.totalElements());
        assertEquals(1, response.totalPages());
    }

    @Test
    void shouldBeEqualWhenSameValues() {
        List<String> content = List.of("item1");
        PagedResponse<String> response1 = new PagedResponse<>(content, 0, 10, 1, 1);
        PagedResponse<String> response2 = new PagedResponse<>(content, 0, 10, 1, 1);

        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
    }
}