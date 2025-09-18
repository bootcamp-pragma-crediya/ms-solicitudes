package com.crediya.solicitudes.r2dbc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoanTypeDataTest {

    @Test
    void constructor_ShouldCreateLoanTypeData() {
        LoanTypeData data = new LoanTypeData("PERSONAL", "Personal Loan", true);

        assertEquals("PERSONAL", data.getCode());
        assertEquals("Personal Loan", data.getName());
        assertTrue(data.isActive());
    }

    @Test
    void builder_ShouldCreateLoanTypeData() {
        LoanTypeData data = LoanTypeData.builder()
                .code("MORTGAGE")
                .name("Mortgage Loan")
                .active(false)
                .build();

        assertEquals("MORTGAGE", data.getCode());
        assertEquals("Mortgage Loan", data.getName());
        assertFalse(data.isActive());
    }

    @Test
    void noArgsConstructor_ShouldCreateEmptyLoanTypeData() {
        LoanTypeData data = new LoanTypeData();

        assertNull(data.getCode());
        assertNull(data.getName());
        assertFalse(data.isActive());
    }

    @Test
    void setters_ShouldUpdateFields() {
        LoanTypeData data = new LoanTypeData();

        data.setCode("AUTO");
        data.setName("Auto Loan");
        data.setActive(true);

        assertEquals("AUTO", data.getCode());
        assertEquals("Auto Loan", data.getName());
        assertTrue(data.isActive());
    }

    @Test
    void equals_ShouldWorkCorrectly() {
        LoanTypeData data1 = LoanTypeData.builder()
                .code("PERSONAL")
                .name("Personal Loan")
                .active(true)
                .build();

        LoanTypeData data2 = LoanTypeData.builder()
                .code("PERSONAL")
                .name("Personal Loan")
                .active(true)
                .build();

        assertEquals(data1, data2);
        assertEquals(data1.hashCode(), data2.hashCode());
    }

    @Test
    void toString_ShouldReturnStringRepresentation() {
        LoanTypeData data = LoanTypeData.builder()
                .code("PERSONAL")
                .name("Personal Loan")
                .active(true)
                .build();

        String result = data.toString();
        assertNotNull(result);
        assertTrue(result.contains("LoanTypeData"));
    }
}