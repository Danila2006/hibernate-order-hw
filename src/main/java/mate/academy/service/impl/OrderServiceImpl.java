package mate.academy.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import mate.academy.dao.OrderDao;
import mate.academy.dao.ShoppingCartDao;
import mate.academy.lib.Inject;
import mate.academy.model.Order;
import mate.academy.model.ShoppingCart;
import mate.academy.model.Ticket;
import mate.academy.model.User;
import mate.academy.service.OrderService;

public class OrderServiceImpl implements OrderService {
    @Inject
    private OrderDao orderDao;
    @Inject
    private ShoppingCartDao shoppingCartDao;

    @Override
    public Order comppleteOrder(ShoppingCart shoppingCart) {
        List<Ticket> tickets = new ArrayList<>();
        for (Ticket ticket : shoppingCart.getTickets()) {
            tickets.add(ticket);
        }

        Order order = new Order();
        order.setUser(shoppingCart.getUser());
        order.setOrderDate(LocalDateTime.now());
        order.setTickets(tickets);

        orderDao.add(order);

        shoppingCart.getTickets().clear();
        shoppingCartDao.update(shoppingCart);

        return order;
    }

    @Override
    public List<Order> getOrdersHistory(User user) {
        if (user != null) {
            return orderDao.getByUser(user);
        }
        return List.of();
    }
}
