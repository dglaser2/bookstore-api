package com.glaserdavid.onlinebookstore.repositories;

import com.glaserdavid.onlinebookstore.domain.Order;
import com.glaserdavid.onlinebookstore.domain.OrderItem;
import com.glaserdavid.onlinebookstore.exceptions.BadRequestException;
import com.glaserdavid.onlinebookstore.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SQL_CREATE_ORDER = "INSERT INTO orders (user_id, order_date, total_amount) " +
            "VALUES (?, ?, ?)";
    private static final String SQL_CREATE_ORDER_ITEM = "INSERT INTO order_items (order_id, book_id, quantity) " +
            "VALUES (?, ?, ?)";
    private static final String SQL_FIND_ORDER_BY_ID = "SELECT * FROM orders WHERE order_id = ?";
    private static final String SQL_FIND_ORDER_ITEMS_BY_ORDER_ID = "SELECT * FROM order_items WHERE order_id = ?";

    @Override
    public Integer create(Order order) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE_ORDER, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, order.getUserId());
                ps.setTimestamp(2, order.getOrderDate());
                ps.setBigDecimal(3, order.getTotalAmount());
                return ps;
            }, keyHolder);

            Integer orderId = (Integer) keyHolder.getKeys().get("order_id");

            for (OrderItem item : order.getOrderItems()) {
                jdbcTemplate.update(SQL_CREATE_ORDER_ITEM, orderId, item.getBookId(), item.getQuantity());
            }
            return orderId;
        } catch (Exception e) {
            throw new BadRequestException("Order invalid");
        }
    }

    @Override
    public Order getOrderById(Integer orderId) throws ResourceNotFoundException {
        try {
            Order order = jdbcTemplate.queryForObject(SQL_FIND_ORDER_BY_ID, new Object[]{orderId}, orderRowMapper);
            List<OrderItem> orderItems = jdbcTemplate.query(SQL_FIND_ORDER_ITEMS_BY_ORDER_ID, new Object[]{orderId}, orderItemRowMapper);
            order.setOrderItems(orderItems);
            return order;
        } catch (Exception e) {
            throw new ResourceNotFoundException("Order with id " + orderId + " not found");
        }
    }

    private final RowMapper<Order> orderRowMapper = (rs, rowNum) -> {
        Order order = new Order(rs.getInt("order_id"),
                rs.getInt("user_id"),
                rs.getTimestamp("order_date"),
                rs.getBigDecimal("total_amount"),
        null);
        return order;
    };

    private final RowMapper<OrderItem> orderItemRowMapper = (rs, rowNum) -> {
        OrderItem item = new OrderItem(
            rs.getInt("order_item_id"),
            rs.getInt("order_id"),
            rs.getInt("book_id"),
            rs.getInt("quantity"));
        return item;
    };
}
