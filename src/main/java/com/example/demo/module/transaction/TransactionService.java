package com.example.demo.module.transaction;

import com.example.demo.client.rajaongkir.RajaongkirClientService;
import com.example.demo.client.rajaongkir.dto.request.CostRequest;
import com.example.demo.client.rajaongkir.dto.response.CostResponse;
import com.example.demo.entity.Product;
import com.example.demo.entity.Transaction;
import com.example.demo.enums.TransactionStatusEnum;
import com.example.demo.module.transaction.request.InquiryRequest;
import com.example.demo.module.transaction.request.PaymentRequest;
import com.example.demo.module.transaction.response.InquiryResponse;
import com.example.demo.module.transaction.response.PaymentResponse;
import com.example.demo.repository.ProductRepositoryService;
import com.example.demo.repository.TransactionRepositoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Service
public class TransactionService {

    @Value("${rajaongkir.request.origin}")
    private String origin;

    @Value("${rajaongkir.request.destination}")
    private String destination;

    @Autowired
    TransactionRepositoryService transactionRepositoryService;

    @Autowired
    ProductRepositoryService productRepositoryService;

    @Autowired
    RajaongkirClientService rajaongkirClientService;

    public InquiryResponse inquiry(InquiryRequest request) {
        log.info("Received inquiry for product with ID: " + request.getProductId() + " and quantity: " + request.getQuantity());

        Product product = productRepositoryService.findById(request.getProductId());
        if(product == null) {
            // exception handling
        }

        BigDecimal subTotal = product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity()));
        String inquiryCode = UUID.randomUUID().toString();

        Transaction transaction = Transaction.builder()
                .product(product)
                .quantity(request.getQuantity())
                .subTotal(subTotal)
                .inquiryCode(inquiryCode)
                .status(TransactionStatusEnum.INQUIRED.getStatus())
                .build();
        transactionRepositoryService.save(transaction);

        return InquiryResponse.builder()
                .inquiryCode(transaction.getInquiryCode())
                .status(TransactionStatusEnum.INQUIRED.getStatus())
                .message(TransactionStatusEnum.INQUIRED.getMessage())
                .build();
    }

    public PaymentResponse payment(PaymentRequest request) {
        log.info("Received payment with inquiry code: " + request.getInquiryCode() + " and courier: " + request.getCourier());
        Transaction transaction = transactionRepositoryService.findByInquiryCode(request.getInquiryCode());

        String responseStatus;
        String responseMessage;
        if (null == transaction) {
            log.warn("Found no transaction with specified inquiry code!");
            responseStatus = TransactionStatusEnum.FAILED.getStatus();
            responseMessage = TransactionStatusEnum.FAILED.getMessage();
        } else if (transaction.getStatus().equals(TransactionStatusEnum.SUCCESS.getStatus())) {
            log.warn("Transaction is already completed!");
            responseStatus = TransactionStatusEnum.FAILED.getStatus();
            responseMessage = TransactionStatusEnum.FAILED.getMessage();
        } else {
            String productWeight = transaction.getProduct().getWeight().toString();
            BigDecimal courierCost = getCourierCost(productWeight, request.getCourier());
            BigDecimal total = transaction.getSubTotal().add(courierCost);

            transaction.setTotal(total);
            transaction.setCourier(request.getCourier());
            transaction.setStatus(TransactionStatusEnum.SUCCESS.getStatus());
            transactionRepositoryService.save(transaction);

            log.debug("Transaction completed");
            responseStatus = TransactionStatusEnum.SUCCESS.getStatus();
            responseMessage = TransactionStatusEnum.SUCCESS.getMessage();
        }

        return PaymentResponse.builder()
                .status(responseStatus)
                .message(responseMessage)
                .build();
    }

    private BigDecimal getCourierCost(String weight, String courier) {
        CostRequest costRequest = CostRequest.builder()
                .origin(origin)
                .destination(destination)
                .weight(weight)
                .courier(courier)
                .build();
        CostResponse costResponse = rajaongkirClientService.getCost(costRequest);
        return costResponse.getRajaongkir().getResult().get(0).getCosts().get(0).getCost().get(0).getValue();
    }
}
