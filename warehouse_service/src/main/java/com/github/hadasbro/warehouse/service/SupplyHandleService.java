package com.github.hadasbro.warehouse.service;

import com.github.hadasbro.warehouse.configuration.ApiConfig;
import com.github.hadasbro.warehouse.domain.Store;
import com.github.hadasbro.warehouse.domain.SupplyBatch;
import com.github.hadasbro.warehouse.dto.ApiResponseDto;
import com.github.hadasbro.warehouse.dto.SupplyBatchDto;
import com.github.hadasbro.warehouse.service.graylog.ApploggerService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@SuppressWarnings({"WeakerAccess", "unused"})
@Service
public class SupplyHandleService {

    @Autowired
    ApploggerService apploggerService;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private ApiConfig apiCofig;

    @Autowired
    private HandleService handleService;

    /**
     * handleSupply
     *
     * @param supplyBatch -
     */
    public void handleSupply(SupplyBatchDto supplyBatch) {

        sendSupplyToClient(supplyBatch)
                .doOnError(err -> apploggerService.log(err))
                .flatMap(x -> {
                    if (x.getCode() != ApiResponseDto.CODE.OK) {
                        apploggerService.log(x.getMessage());
                        return Mono.empty();
                    }
                    return saveSupplyOrder(supplyBatch);
                })
                .subscribe(res -> {
                            // TODO - further operations
                        },
                        error -> apploggerService.log(error)
                );

    }

    /**
     * saveSupplyOrder
     *
     * @param supplyBatchDto -
     * @return Mono<SupplyBatch>
     */
    @HystrixCommand(
            fallbackMethod = "hystrixSendSupplyFallback",
            commandProperties = {
                    @HystrixProperty(
                            name = "execution.isolation.thread.timeoutInMilliseconds",
                            value = "10000"),
                    @HystrixProperty(
                            name = "circuitBreaker.requestVolumeThreshold",
                            value = "30"),
                    @HystrixProperty(
                            name = "circuitBreaker.errorThresholdPercentage",
                            value = "20"),
                    @HystrixProperty(
                            name = "metrics.rollingStats.timeInMilliseconds",
                            value = "20000"),
                    @HystrixProperty(
                            name = "circuitBreaker.sleepWindowInMilliseconds",
                            value = "60000")
            })
    public Mono<SupplyBatch> saveSupplyOrder(SupplyBatchDto supplyBatchDto) {

        SupplyBatch sab = SupplyBatch.fromSupplyBatchDto(supplyBatchDto);
        Store store = Store.fromStoreDto(supplyBatchDto.getStore());

        return handleService
                .upsertStore(Store.fromStoreDto(supplyBatchDto.getStore()))
                .flatMap(s -> handleService.upsertSupply(sab));

    }

    /**
     * sendSupplyToClient
     *
     * @param supplyBatch -
     */
    @HystrixCommand(
            fallbackMethod = "hystrixSendSupplyFallback",
            commandProperties = {
                    @HystrixProperty(
                            name = "execution.isolation.thread.timeoutInMilliseconds",
                            value = "10000"),
                    @HystrixProperty(
                            name = "circuitBreaker.requestVolumeThreshold",
                            value = "30"),
                    @HystrixProperty(
                            name = "circuitBreaker.errorThresholdPercentage",
                            value = "20"),
                    @HystrixProperty(
                            name = "metrics.rollingStats.timeInMilliseconds",
                            value = "20000"),
                    @HystrixProperty(
                            name = "circuitBreaker.sleepWindowInMilliseconds",
                            value = "60000")
            })
    public Flux<ApiResponseDto> sendSupplyToClient(SupplyBatchDto supplyBatch) {

        try {

            // TODO
            // do some stuff ....
            // handle some logic, check warehouse

            return sendSupplyOrderRequest(supplyBatch);

        } catch (Throwable t) {
            apploggerService.log(t);
            return Flux.just(
                    new ApiResponseDto("General error", ApiResponseDto.CODE.GENERAL)
            );
        }

    }

    /**
     * hystrixSendSupplyFallback
     *
     * @param supplyBatch -
     * @param exc         -
     */
    public void hystrixSendSupplyFallback(SupplyBatchDto supplyBatch, Throwable exc) {
        String msg = String.format("SupplyOrder: [%s], Fallback exception: [%s]",
                supplyBatch, exc.getMessage());
        Exception excInternal = new Exception(msg);
        apploggerService.log(excInternal);
    }

    /**
     * sendSupplyOrderRequest
     *
     * @param emailOrderDto -
     * @return Mono<ApiResponseDto> -
     */
    private Flux<ApiResponseDto> sendSupplyOrderRequest(SupplyBatchDto emailOrderDto) {

        WebClient client = webClientBuilder
                .baseUrl(apiCofig.getUrl_base())
                .defaultCookie("supply_from_warehouse", "1")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();


        return client
                .method(HttpMethod.POST)
                .uri(apiCofig.getUrl_resource())
                .body(BodyInserters.fromObject(emailOrderDto))
                .retrieve()
                .bodyToFlux(ApiResponseDto.class);

    }

}
