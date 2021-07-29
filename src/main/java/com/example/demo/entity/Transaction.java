package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @SequenceGenerator(name="transaction_id_seq", sequenceName="transaction_id_seq", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator= "transaction_id_seq")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "product_id"))
    private Product product;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private BigDecimal subTotal;
    @Column()
    private String courier;
    @Column()
    private BigDecimal total;
    @Column(nullable = false)
    private String inquiryCode;
    @Column(nullable = false)
    private String status;
}
