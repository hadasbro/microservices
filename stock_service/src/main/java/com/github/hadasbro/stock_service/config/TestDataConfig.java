package com.github.hadasbro.stock_service.config;

import com.github.hadasbro.stock_service.repository.OrderRepository;
import com.github.hadasbro.stock_service.repository.ProductRepository;
import com.github.hadasbro.stock_service.repository.UserRepository;
import com.github.hadasbro.stock_service.model.Order;
import com.github.hadasbro.stock_service.model.Product;
import com.github.hadasbro.stock_service.model.User;
import com.github.hadasbro.stock_service.udt.ManufacturerUdt;
import com.github.hadasbro.stock_service.udt.ProductBatchUdt;
import com.github.hadasbro.stock_service.udt.UserUdt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * TestDataConfig
 */
@SuppressWarnings({"unused"})
@Profile("test")
@Configuration
public class TestDataConfig {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    private Flux<Product> allProducts;
    private Flux<Order> allOrders;
    private Flux<User> allUsers;

    /**
     * passwordEncoder
     *
     * @return BCryptPasswordEncoder
     */
    private BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * dataLoader
     *
     * @return CommandLineRunner
     */
    @Bean
    public CommandLineRunner dataLoader() {
        return args -> {

            loadUsers();
            loadManufacturers();
            loadProducts();
            loadOrders();

        };
    }

    /**
     * loadUsers
     */
    protected void loadUsers() {

        List<User> users = new ArrayList<>();

        for (int i = 0; i <= 40; i++) {

            User user = new User();
            user.setAddress("Super Address " + i);
            user.setEmail(String.format("superemail%s@gmail.com", i));
            user.setFullname("John Doe " + i);
            user.setPassword(this.passwordEncoder().encode("password"));
            user.setPhoneNumber("0777888999");
            user.setUsername("userUdt" + i);

            users.add(user);

        }

        this.allUsers = userRepository.saveAll(users);

    }

    /**
     * loadManufacturers
     */
    private void loadManufacturers() {

        List<ManufacturerUdt> manufacturerUdts = new ArrayList<>();

        for (int i = 0; i <= 40; i++) {
            ManufacturerUdt manufacturerUdt = new ManufacturerUdt();
            manufacturerUdt.setAddress("Address " + (i % 8));
            manufacturerUdt.setCompanyName("Company " + (i % 8));
            manufacturerUdts.add(manufacturerUdt);
        }

    }

    /**
     * loadProducts
     */
    private void loadProducts() {

        List<Product> products = new ArrayList<>();

        for (int i = 0; i <= 40; i++) {

            ManufacturerUdt manufacturerUdt = new ManufacturerUdt();
            manufacturerUdt.setAddress("Address " + (i % 8));
            manufacturerUdt.setCompanyName("Company " + (i % 8));

            Product prod = new Product();
            prod.setAvailableQuantity(20);
            prod.setCatalogId(String.valueOf(i));
            prod.setCreatedAt(new Date());
            prod.setDescription("products description ###" + i);
            prod.setName("My product " + i);
            prod.setManufacturerUdt(manufacturerUdt);

            products.add(prod);

        }

        this.allProducts = productRepository.saveAll(products);

    }

    /**
     * loadOrders
     */
    private void loadOrders() {

        List<Order> orders = new ArrayList<>();

        Flux<List<User>> allUsersBuf = allUsers.buffer();
        List<User> allUsersList = allUsersBuf.blockFirst();

        Flux<List<Product>> allProductsBuf = allProducts.buffer();
        List<Product> allProductsList = allProductsBuf.blockFirst();

        for (int i = 0; i <= 40; i++) {

            // rand user
            assert allUsersList != null;
            int asize = allUsersList.size();
            int rn = i % asize;
            rn = rn == 0 ? 1 : rn;
            rn = rn == asize ? asize - 1 : rn;

            User user = allUsersList.get(rn);
            UserUdt userUdt = new UserUdt(user.getUsername(), user.getEmail());

            // rand product batch

            List<ProductBatchUdt> productBatchUdts = new ArrayList<>();

            for (int ki = 0; ki <= 5; ki++) {

                asize = allProductsList.size();
                rn = ki % asize;
                rn = rn == 0 ? 1 : rn;
                rn = rn == asize ? asize - 1 : rn;
                Product product = allProductsList.get(rn);

                ProductBatchUdt productBatchUdt = new ProductBatchUdt();
                productBatchUdt.setCatalogId(product.getCatalogId());
                productBatchUdt.setQuantity((i + 5) % 30);

                productBatchUdts.add(productBatchUdt);

            }

            Order.Status x = Order.Status.READY_TO_COLLECT;


            Order.Type oType;
            Order.Status oStatus;

            if (i % 3 == 0) {
                oType = Order.Type.IN_PLACE;
                oStatus = Order.Status.COMPLETED;
            } else if (i % 3 == 1) {
                oType = Order.Type.REMOTE_TO_SEND;
                oStatus = Order.Status.READY_TO_SEND;
            } else {
                oType = Order.Type.REMOTE_TO_SEND;
                oStatus = Order.Status.RECEIVED;
            }

            Order order = new Order();
            order.setPlacedAt(new Date());
            order.setProductBatchUdts(productBatchUdts);
            order.setStatus(oStatus);
            order.setType(oType);

            order.setUserUdt(userUdt);

            orders.add(order);

        }

        this.allOrders = orderRepository.saveAll(orders);

    }

}
