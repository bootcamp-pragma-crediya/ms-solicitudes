package com.crediya.solicitudes.api.dto;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PagedResponseTest {

    @Test
    void shouldCreatePagedResponseWithAllFields() {
        // Given
        List<String> content = List.of("item1", "item2");
        int page = 0;
        int size = 10;
        long totalElements = 2L;
        int totalPages = 1;
        
        // When
        PagedResponse<String> response = new PagedResponse<>(content, page, size, totalElements, totalPages);
        
        // Then
        assertThat(response.content()).isEqualTo(content);
        assertThat(response.page()).isEqualTo(page);
        assertThat(response.size()).isEqualTo(size);
        assertThat(response.totalElements()).isEqualTo(totalElements);
        assertThat(response.totalPages()).isEqualTo(totalPages);
    }
    
    @Test
    void shouldSupportEqualsAndHashCode() {
        // Given
        List<String> content = List.of("item1", "item2");
        PagedResponse<String> response1 = new PagedResponse<>(content, 0, 10, 2L, 1);
        PagedResponse<String> response2 = new PagedResponse<>(content, 0, 10, 2L, 1);
        
        // Then
        assertThat(response1).isEqualTo(response2);
        assertThat(response1.hashCode()).isEqualTo(response2.hashCode());
    }
    
    @Test
    void shouldSupportToString() {
        // Given
        List<String> content = List.of("item1", "item2");
        PagedResponse<String> response = new PagedResponse<>(content, 0, 10, 2L, 1);
        
        // When
        String result = response.toString();
        
        // Then
        assertThat(result).contains("item1");
        assertThat(result).contains("item2");
        assertThat(result).contains("0");
        assertThat(result).contains("10");
        assertThat(result).contains("2");
        assertThat(result).contains("1");
    }
    
    @Test
    void shouldHandleEmptyContent() {
        // Given
        List<String> content = List.of();
        
        // When
        PagedResponse<String> response = new PagedResponse<>(content, 0, 10, 0L, 0);
        
        // Then
        assertThat(response.content()).isEmpty();
        assertThat(response.totalElements()).isEqualTo(0L);
        assertThat(response.totalPages()).isEqualTo(0);
    }
}