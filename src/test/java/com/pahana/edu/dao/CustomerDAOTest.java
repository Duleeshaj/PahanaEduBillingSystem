package com.pahana.edu.dao;

import com.pahana.edu.exception.DaoException;
import com.pahana.edu.model.Customer;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDAOTest {

    private CustomerDAO dao;

    @BeforeEach
    void setUp() throws Exception {
        dao = new FakeCustomerDAO();
        // seed data
        dao.addCustomer(new Customer(1001, "Alice Perera", "123 Main St", "0771234567", "alice@example.com"));
        dao.addCustomer(new Customer(1002, "Bob Silva", "456 Lake Rd", "0719876543", "bob@example.com"));
        dao.addCustomer(new Customer(1003, "Carol N.", "789 Park Ave", "0752223344", "carol@example.com"));
    }

    @Test
    @DisplayName("addCustomer inserts a new unique customer")
    void addCustomer_success() throws Exception {
        assertTrue(dao.addCustomer(new Customer(1004, "Dan", "A St", "0700000000", "dan@example.com")));
        assertTrue(dao.doesCustomerExist(1004));
        Customer c = dao.getCustomerByAccountNumber(1004);
        assertEquals("Dan", c.getName());
    }

    @Test
    @DisplayName("addCustomer throws on duplicate accountNumber")
    void addCustomer_duplicate() {
        DaoException ex = assertThrows(DaoException.class,
                () -> dao.addCustomer(new Customer(1001, "Alice Clone", "X", "0", "x@x.com")));
        assertTrue(ex.getMessage().toLowerCase().contains("exists"));
    }

    @Test
    @DisplayName("getCustomerByAccountNumber returns a copy of stored data")
    void getCustomer_returnsCopy() throws Exception {
        Customer c1 = dao.getCustomerByAccountNumber(1002);
        assertNotNull(c1);
        assertEquals("Bob Silva", c1.getName());

        // mutate returned object; DAO data should remain unchanged
        c1.setName("Hacked");
        Customer c2 = dao.getCustomerByAccountNumber(1002);
        assertEquals("Bob Silva", c2.getName());
    }

    @Test
    @DisplayName("updateCustomer replaces existing record")
    void updateCustomer_success() throws Exception {
        Customer u = new Customer(1003, "Carol Nirmala", "New Addr", "0759999999", "carol@new.com");
        assertTrue(dao.updateCustomer(u));

        Customer re = dao.getCustomerByAccountNumber(1003);
        assertEquals("Carol Nirmala", re.getName());
        assertEquals("New Addr", re.getAddress());
        assertEquals("0759999999", re.getTelephone());
        assertEquals("carol@new.com", re.getEmail());
    }

    @Test
    @DisplayName("updateCustomer returns false when account does not exist")
    void updateCustomer_notFound() throws Exception {
        assertFalse(dao.updateCustomer(new Customer(9999, "Ghost", "N/A", "0", "ghost@x.com")));
    }

    @Test
    @DisplayName("deleteCustomer removes record and returns boolean")
    void deleteCustomer_works() throws Exception {
        assertTrue(dao.deleteCustomer(1002));
        assertFalse(dao.doesCustomerExist(1002));
        assertNull(dao.getCustomerByAccountNumber(1002));
        assertFalse(dao.deleteCustomer(1002)); // already deleted
    }

    @Test
    @DisplayName("getAllCustomers returns sorted list (by accountNumber)")
    void getAll_sorted() throws Exception {
        List<Customer> all = dao.getAllCustomers();
        assertEquals(3, all.size());
        assertEquals(1001, all.get(0).getAccountNumber());
        assertEquals(1002, all.get(1).getAccountNumber());
        assertEquals(1003, all.get(2).getAccountNumber());
    }

    @Test
    @DisplayName("searchCustomersByName is case-insensitive and substring-based")
    void searchByName_caseInsensitive() throws Exception {
        List<Customer> res1 = dao.searchCustomersByName("per");
        assertEquals(1, res1.size());
        assertEquals("Alice Perera", res1.get(0).getName());

        List<Customer> res2 = dao.searchCustomersByName("silva");
        assertEquals(1, res2.size());
        assertEquals("Bob Silva", res2.get(0).getName());

        List<Customer> res3 = dao.searchCustomersByName("a"); // many
        assertTrue(res3.size() >= 2);
    }

    @Test
    @DisplayName("doesCustomerExist reflects presence")
    void exists_check() {
        assertTrue(dao.doesCustomerExist(1001));
        assertFalse(dao.doesCustomerExist(9999));
    }
}
