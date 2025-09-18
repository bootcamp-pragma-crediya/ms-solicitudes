package com.crediya.solicitudes.api.dto;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PagedResponseTest {

    @Test
    void shouldCreatePagedResponse() {
        List<String> content = Arrays.asList("item1", "item2", "item3");
        PagedResponse<String> response = new PagedResponse<>(content, 0, 10, 3L, 1);

        assertEquals(content, response.content());
        assertEquals(0, response.page());
        assertEquals(10, response.size());
        assertEquals(3L, response.totalElements());
        assertEquals(1, response.totalPages());
    }

    @Test
    void shouldCreateEmptyPagedResponse() {
        List<String> content = Collections.emptyList();
        PagedResponse<String> response = new PagedResponse<>(content, 0, 10, 0L, 0);

        assertTrue(response.content().isEmpty());
        assertEquals(0, response.page());
        assertEquals(10, response.size());
        assertEquals(0L, response.totalElements());
        assertEquals(0, response.totalPages());
    }

    @Test
    void shouldSupportEqualsAndHashCode() {
        List<String> content = Arrays.asList("item1", "item2");
        PagedResponse<String> response1 = new PagedResponse<>(content, 1, 5, 10L, 2);
        PagedResponse<String> response2 = new PagedResponse<>(content, 1, 5, 10L, 2);

        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    void shouldSupportToString() {
        List<Integer> content = Arrays.asList(1, 2, 3);
        PagedResponse<Integer> response = new PagedResponse<>(content, 2, 3, 9L, 3);

        String toString = response.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("content"));
        assertTrue(toString.contains("page"));
        assertTrue(toString.contains("size"));
    }

    @Test
    void shouldHandleNullContent() {
        PagedResponse<String> response = new PagedResponse<>(null, 0, 10, 0L, 0);

        assertNull(response.content());
        assertEquals(0, response.page());
        assertEquals(10, response.size());
        assertEquals(0L, response.totalElements());
        assertEquals(0, response.totalPages());
    }

    @Test
    void shouldWorkWithDifferentTypes() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        PagedResponse<Integer> numberResponse = new PagedResponse<>(numbers, 0, 5, 5L, 1);

        assertEquals(numbers, numberResponse.content());
        assertEquals(5, numberResponse.content().size());

        List<Boolean> booleans = Arrays.asList(true, false);
        PagedResponse<Boolean> booleanResponse = new PagedResponse<>(booleans, 1, 2, 4L, 2);

        assertEquals(booleans, booleanResponse.content());
        assertEquals(2, booleanResponse.content().size());
    }
}