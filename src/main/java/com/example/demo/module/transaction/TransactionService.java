package com.example.demo.module.transaction;

import com.example.demo.client.rajaongkir.RajaongkirClientService;
import com.example.demo.client.rajaongkir.dto.request.CostRequest;
import com.example.demo.client.rajaongkir.dto.response.CostResponse;
import com.example.demo.client.rajaongkir.dto.response.Costs;
import com.example.demo.entity.Product;
import com.example.demo.entity.Transaction;
import com.example.demo.enums.ResponseEnum;
import com.example.demo.exception.SpringBootSimpleAppException;
import com.example.demo.module.BaseResponse;
import com.example.demo.module.transaction.request.InquiryRequest;
import com.example.demo.module.transaction.request.PaymentRequest;
import com.example.demo.module.transaction.response.InquiryResponse;
import com.example.demo.module.transaction.response.PaymentResponse;
import com.example.demo.repository.ProductRepositoryService;
import com.example.demo.repository.TransactionRepositoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class TransactionService {

    @Value("${rajaongkir.request.origin}")
    private String origin;

    @Value("${rajaongkir.request.destination}")
    private String destination;

    @Value("${rajaongkir.courier.jne}")
    private int jneCourierType;

    @Value("${rajaongkir.courier.tiki}")
    private int tikiCourierType;

    @Value("${rajaongkir.courier.pos}")
    private int posCourierType;

    @Autowired
    TransactionRepositoryService transactionRepositoryService;

    @Autowired
    ProductRepositoryService productRepositoryService;

    @Autowired
    RajaongkirClientService rajaongkirClientService;

    public BaseResponse<InquiryResponse> inquiry(InquiryRequest request) {
        log.info("Received inquiry for product with ID: [" + request.getProductId() + "] and quantity: [" + request.getQuantity() + "]");
        Product product = productRepositoryService.findById(request.getProductId());
        if(product == null) {
            log.warn("Found no product with specified ID: [" + request.getProductId() + "]");
            throw new SpringBootSimpleAppException(ResponseEnum.PRODUCT_NOT_FOUND.getMessage(), ResponseEnum.PRODUCT_NOT_FOUND.getStatus(), HttpStatus.BAD_REQUEST);
        }

        BigDecimal subTotal = product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity()));
        String inquiryCode = UUID.randomUUID().toString();
        Transaction transaction = Transaction.builder()
                .product(product)
                .quantity(request.getQuantity())
                .subTotal(subTotal)
                .inquiryCode(inquiryCode)
                .status(ResponseEnum.INQUIRED.getStatus())
                .build();
        transactionRepositoryService.save(transaction);

        log.info("Created new transaction with inquiry code [" + transaction.getInquiryCode() + "] for product ID [" + request.getProductId() + "] and quantity [" + request.getQuantity() + "]");
        return BaseResponse.<InquiryResponse>builder()
                .status(ResponseEnum.INQUIRED.getStatus())
                .message(ResponseEnum.INQUIRED.getMessage())
                .data(InquiryResponse.builder()
                        .inquiryCode(transaction.getInquiryCode())
                        .build())
                .build();
    }

    public BaseResponse<PaymentResponse> payment(PaymentRequest request) {
        log.info("Received payment with inquiry code: [" + request.getInquiryCode() + "] and courier cost: [" + request.getCourierCost() + "]");
        Transaction transaction = transactionRepositoryService.findByInquiryCode(request.getInquiryCode());
        if (null == transaction) {
            log.warn("Found no transaction with specified inquiry code! [" + request.getInquiryCode() + "]");
            throw new SpringBootSimpleAppException(ResponseEnum.TRANSACTION_NOT_FOUND.getMessage(), ResponseEnum.TRANSACTION_NOT_FOUND.getStatus(), HttpStatus.BAD_REQUEST);
        } else if (transaction.getStatus().equals(ResponseEnum.SUCCESS.getStatus())) {
            log.warn("Transaction is already completed! [" + request.getInquiryCode() + "]");
            throw new SpringBootSimpleAppException(ResponseEnum.TRANSACTION_ALREADY_PROCESSED.getMessage(), ResponseEnum.TRANSACTION_ALREADY_PROCESSED.getStatus(), HttpStatus.BAD_REQUEST);
        } else {
            transaction.setCourierCost(request.getCourierCost());
            transaction.setTotal(transaction.getSubTotal().add(request.getCourierCost()));
            transaction.setStatus(ResponseEnum.SUCCESS.getStatus());
            transactionRepositoryService.save(transaction);

            log.info("Transaction with code [" + request.getInquiryCode() + "] has been completed");
            return BaseResponse.<PaymentResponse>builder()
                    .status(ResponseEnum.SUCCESS.getStatus())
                    .message(ResponseEnum.SUCCESS.getMessage())
                    .data(PaymentResponse.builder()
                            .inquiryCode(transaction.getInquiryCode())
                            .build())
                    .build();
        }
    }

    @Cacheable(value = "courier_cost", key = "#courier + #weight")
    public BaseResponse<BigDecimal> getCourierCost(String courier, Integer weight) {
        log.debug("Checking courier cost for weight [" + weight + "] and courier [" + courier + "]");
        CostRequest costRequest = CostRequest.builder()
                .origin(origin)
                .destination(destination)
                .weight(weight.toString())
                .courier(courier)
                .build();
        CostResponse costResponse = rajaongkirClientService.getCost(costRequest);

        List<Costs> costs = costResponse.getRajaongkir().getResults().get(0).getCosts();
        BigDecimal courierCost;
        switch (courier) {
            case "jne":
                courierCost = costs.get(jneCourierType).getCost().get(0).getValue();
                break;
            case "tiki":
                courierCost = costs.get(tikiCourierType).getCost().get(0).getValue();
                break;
            case "pos":
                courierCost = costs.get(posCourierType).getCost().get(0).getValue();
                break;
            default:
                log.warn("Unknown courier type: [" + courier + "]");
                throw new SpringBootSimpleAppException(ResponseEnum.UNKNOWN_ERROR.getMessage(), ResponseEnum.UNKNOWN_ERROR.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return BaseResponse.<BigDecimal>builder()
                .status(ResponseEnum.SUCCESS.getStatus())
                .message(ResponseEnum.SUCCESS.getMessage())
                .data(courierCost)
                .build();
    }
}
