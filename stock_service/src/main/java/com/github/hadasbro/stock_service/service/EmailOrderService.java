package com.github.hadasbro.stock_service.service;

import com.github.hadasbro.stock_service.dto.EmailSupplyOrderDto;
import com.github.hadasbro.stock_service.dto.SupplyOrderBatchDto;
import com.github.hadasbro.stock_service.model.Product;
import com.github.hadasbro.stock_service.model.SupplyOrderBatch;
import com.github.hadasbro.stock_service.model.User;
import com.github.hadasbro.stock_service.repository.OrderRepository;
import com.github.hadasbro.stock_service.repository.ProductRepository;
import com.github.hadasbro.stock_service.repository.UserRepository;
import com.github.hadasbro.stock_service.service.graylog.ApploggerService;
import com.github.hadasbro.stock_service.udt.ProductBatchUdt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * EmailOrderService
 */
@Service
@SuppressWarnings("unused")
public class EmailOrderService {

    @Autowired
    private ApploggerService apploggerService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productsRepository;

    @Autowired
    private SupplyOrderService supplyOrderService;

    @Autowired
    private OrderRepository orderRepository;

    /**
     * validateEmailOrder
     *
     * @param emailSupplyOrderDto -
     * @return Mono<Boolean> -
     */
    private Mono<Boolean> validateEmailOrder(EmailSupplyOrderDto emailSupplyOrderDto) {

        try {

            Mono<User> user = userRepository.findByEmail(emailSupplyOrderDto.getSenderEmail());

            List<EmailSupplyOrderDto.ProductQty> productQtys = emailSupplyOrderDto.getProductQtys();

            Map<String, Integer> productQtysByCatalogId = productQtys
                    .stream()
                    .distinct()
                    .collect(
                            Collectors.toMap(
                                    EmailSupplyOrderDto.ProductQty::getCatalogId,
                                    EmailSupplyOrderDto.ProductQty::getQuantity
                            ));

            Flux<Product> productsAvailable = productsRepository
                    .findByCatalogIdIn(
                            productQtys
                                    .stream()
                                    .map(
                                            EmailSupplyOrderDto.ProductQty::getCatalogId
                                    )
                                    .collect(
                                            Collectors.toList()
                                    )
                    )
                    .distinct()
                    .filter(
                            prod -> prod.getAvailableQuantity() >= productQtysByCatalogId.get(prod.getCatalogId())
                    );

            return Mono
                    .zip(user.hasElement(), productsAvailable.count().map(l -> l > 0))
                    .flatMap(tuple -> {

                        if (!tuple.getT1()) {
                            return Mono.error(new Error("No User found"));
                        }

                        if (!tuple.getT2()) {
                            String productsId = "#123"; // TODO
                            return Mono.error(new Error(String.format("No Products %s found", productsId)));
                        }

                        return Mono.just(true);

                    });

        } catch (Throwable t) {
            return Mono.error(t);
        }
    }

    /**
     * handleSupplyOrder
     *
     * @param emailSupplyOrderDto -
     * @return Mono<SupplyOrderBatchDto> -
     */
    Mono<SupplyOrderBatchDto> handleSupplyOrder(EmailSupplyOrderDto emailSupplyOrderDto) {

        List<ProductBatchUdt> productBatchList = new ArrayList<ProductBatchUdt>() {{
            addAll(
                    emailSupplyOrderDto
                            .getProductQtys()
                            .stream()
                            .map(
                                    pqt -> {
                                        ProductBatchUdt productBatchUdt = new ProductBatchUdt();
                                        productBatchUdt.setQuantity(pqt.getQuantity());
                                        productBatchUdt.setCatalogId(pqt.getCatalogId());
                                        return productBatchUdt;
                                    }
                            )
                            .collect(Collectors.toList())
            );
        }};

        return validateEmailOrder(emailSupplyOrderDto)
                .flatMap(x -> {

                    SupplyOrderBatch sbatch = new SupplyOrderBatch();
                    sbatch.setProducts(productBatchList);
                    sbatch.setDate(new Date());
                    sbatch.setStatus(SupplyOrderBatch.STATUS.NONE);

                    /*
                    TODO
                    this logic should be completely separated
                    but for presentation purpose it is here
                    [ this logic is separate part, it is responsible
                     for sending supply orders to Suppliers ]
                     */
                    return supplyOrderService.orderSupply(sbatch);

                })
                .doOnError(err -> apploggerService.log(err))
                .map(SupplyOrderBatchDto::from);

    }
}
