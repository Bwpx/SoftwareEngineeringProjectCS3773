package edu.softwareengineeringprojectcs3773.repository;

import edu.softwareengineeringprojectcs3773.database.TestDatabase;
import edu.softwareengineeringprojectcs3773.model.Account;
import edu.softwareengineeringprojectcs3773.model.Order;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OrderRepositoryTest {

    private OrderRepository orderRepository;
    private AccountRepository accountRepository;
    private int accountId;

    @BeforeEach
    void setUp() {
        TestDatabase.reset();

        orderRepository = new OrderRepository();
        accountRepository = new AccountRepository();

        Account account = new Account(
                0,
                "Martin",
                "Gonzalez",
                "martin123",
                "martin@example.com",
                "password123",
                "2105551234"
        );

        Account savedAccount =
                accountRepository.save(account);

        assertNotNull(savedAccount);

        accountId = savedAccount.getAccountId();
    }

    @AfterAll
    static void tearDownAll() {
        TestDatabase.deleteTestDatabase();
        TestDatabase.stopUsingTestDatabase();
    }

    @Test
    void saveAssignsGeneratedOrderIdAndPreservesDecimalTotal() {
        Order order = createOrder(
                accountId,
                19.99,
                "Processing"
        );

        Order savedOrder = orderRepository.save(order);

        assertNotNull(savedOrder);
        assertTrue(savedOrder.getOrderId() > 0);

        Order foundOrder =
                orderRepository.findById(
                        savedOrder.getOrderId()
                );

        assertNotNull(foundOrder);
        assertEquals(
                19.99,
                foundOrder.getTotal(),
                0.001
        );
    }

    @Test
    void findByIdReturnsCorrectOrder() {
        Order savedOrder = orderRepository.save(
                createOrder(
                        accountId,
                        42.50,
                        "Processing"
                )
        );

        Order foundOrder =
                orderRepository.findById(
                        savedOrder.getOrderId()
                );

        assertNotNull(foundOrder);
        assertEquals(
                savedOrder.getOrderId(),
                foundOrder.getOrderId()
        );
        assertEquals(accountId, foundOrder.getAccountId());
        assertEquals(
                Date.valueOf(LocalDate.now()),
                foundOrder.getDate()
        );
        assertEquals(
                "Processing",
                foundOrder.getStatus()
        );
        assertEquals(
                "Delivery",
                foundOrder.getDeliveryType()
        );
    }

    @Test
    void findByAccountIdReturnsOnlyThatAccountsOrders() {
        orderRepository.save(
                createOrder(
                        accountId,
                        15.25,
                        "Processing"
                )
        );

        orderRepository.save(
                createOrder(
                        accountId,
                        27.75,
                        "Completed"
                )
        );

        Account secondAccount = accountRepository.save(
                new Account(
                        0,
                        "John",
                        "Smith",
                        "john123",
                        "john@example.com",
                        "password456",
                        "2105555678"
                )
        );

        orderRepository.save(
                createOrder(
                        secondAccount.getAccountId(),
                        99.99,
                        "Processing"
                )
        );

        ArrayList<Order> martinOrders =
                orderRepository.findByAccountId(accountId);

        assertEquals(2, martinOrders.size());

        for (Order order : martinOrders) {
            assertEquals(
                    accountId,
                    order.getAccountId()
            );
        }
    }

    @Test
    void updateOrderChangesStoredInformation() {
        Order savedOrder = orderRepository.save(
                createOrder(
                        accountId,
                        35.50,
                        "Processing"
                )
        );

        Order updatedOrder = new Order(
                savedOrder.getOrderId(),
                accountId,
                Date.valueOf(LocalDate.now()),
                40.75,
                "Completed",
                "Pickup",
                "View Receipt"
        );

        Order result =
                orderRepository.updateOrder(updatedOrder);

        Order foundOrder =
                orderRepository.findById(
                        savedOrder.getOrderId()
                );

        assertNotNull(result);
        assertNotNull(foundOrder);
        assertEquals(
                40.75,
                foundOrder.getTotal(),
                0.001
        );
        assertEquals(
                "Completed",
                foundOrder.getStatus()
        );
        assertEquals(
                "Pickup",
                foundOrder.getDeliveryType()
        );
        assertEquals(
                "View Receipt",
                foundOrder.getActions()
        );
    }

    private Order createOrder(
            int orderAccountId,
            double total,
            String status
    ) {
        return new Order(
                0,
                orderAccountId,
                Date.valueOf(LocalDate.now()),
                total,
                status,
                "Delivery",
                "View Details"
        );
    }
}