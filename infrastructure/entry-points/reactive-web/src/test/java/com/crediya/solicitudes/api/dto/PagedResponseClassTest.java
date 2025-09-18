package com.crediya.solicitudes.api.dto;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PagedResponseClassTest {

    @Test
    void constructor_ShouldCreatePagedResponse() {
        List<String> content = List.of("item1", "item2");
        PagedResponseClass<String> response = new PagedResponseClass<>(content, 0, 10, 2, 1);

        assertEquals(content, response.getContent());
        assertEquals(0, response.getPage());
        assertEquals(10, response.getSize());
        assertEquals(2, response.getTotalElements());
        assertEquals(1, response.getTotalPages());
    }

    @Test
    void noArgsConstructor_ShouldCreateEmptyPagedResponse() {
        PagedResponseClass<String> response = new PagedResponseClass<>();

        assertNull(response.getContent());
        assertEquals(0, response.getPage());
        assertEquals(0, response.getSize());
        assertEquals(0, response.getTotalElements());
        assertEquals(0, response.getTotalPages());
    }

    @Test
    void setters_ShouldUpdateFields() {
        PagedResponseClass<String> response = new PagedResponseClass<>();
        List<String> content = List.of("test");

        response.setContent(content);
        response.setPage(1);
        response.setSize(5);
        response.setTotalElements(10);
        response.setTotalPages(2);

        assertEquals(content, response.getContent());
        assertEquals(1, response.getPage());
        assertEquals(5, response.getSize());
        assertEquals(10, response.getTotalElements());
        assertEquals(2, response.getTotalPages());
    }

    @Test
    void equals_ShouldWorkCorrectly() {
        List<String> content = List.of("item");
        PagedResponseClass<String> response1 = new PagedResponseClass<>(content, 0, 10, 1, 1);
        PagedResponseClass<String> response2 = new PagedResponseClass<>(content, 0, 10, 1, 1);

        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    void toString_ShouldReturnStringRepresentation() {
        PagedResponseClass<String> response = new PagedResponseClass<>(List.of("test"), 0, 10, 1, 1);
        String result = response.toString();

        assertNotNull(result);
        assertTrue(result.contains("PagedResponseClass"));
    }
}