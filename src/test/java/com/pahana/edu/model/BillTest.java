package com.pahana.edu.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BillTest {

    @Test
    @DisplayName("Default constructor should set Java defaults")
    void defaultConstructor_defaults() {
        Bill b = new Bill();
        assertAll(
                () -> assertEquals(0, b.getBillId()),
                () -> assertEquals(0, b.getAccountNumber()),
                () -> assertEquals(0.0, b.getTotalAmount(), 0.000001),
                () -> assertNull(b.getBillDate()),
                () -> assertNull(b.getItems())
        );
    }

    @Test
    @DisplayName("Setters/Getters should update and return values")
    void settersGetters_work() {
        Bill b = new Bill();
        LocalDateTime when = LocalDateTime.of(2025, 1, 1, 10, 30, 0);
        List<BillItem> items = new ArrayList<>();

        b.setBillId(101);
        b.setAccountNumber(2001);
        b.setTotalAmount(1234.5678);
        b.setBillDate(when);
        b.setItems(items);

        assertAll(
                () -> assertEquals(101, b.getBillId()),
                () -> assertEquals(2001, b.getAccountNumber()),
                () -> assertEquals(1234.5678, b.getTotalAmount(), 0.000001),
                () -> assertEquals(when, b.getBillDate()),
                () -> assertSame(items, b.getItems(), "Should keep the same list reference")
        );
    }

    @Test
    @DisplayName("Items list can be replaced after construction")
    void itemsList_isMutableReference() {
        Bill b = new Bill();
        List<BillItem> first = new ArrayList<>();
        List<BillItem> second = new ArrayList<>();
        b.setItems(first);
        assertSame(first, b.getItems());
        b.setItems(second);
        assertSame(second, b.getItems());
    }

    @Test
    @DisplayName("Total amount allows precision (double with tolerance)")
    void totalAmount_precision() {
        Bill b = new Bill();
        b.setTotalAmount(999.999999);
        assertEquals(1000.0, b.getTotalAmount(), 0.0005); // tolerance for double math
    }
}
