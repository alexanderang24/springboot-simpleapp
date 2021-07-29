package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trx {
    @Id
    @SequenceGenerator(name="trx_id_seq", sequenceName="trx_id_seq", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator= "trx_id_seq")
    private Long id;
    @Column(nullable = false)
    private String productName;
    @Column(nullable = false)
    private Integer amount;
    @Column(nullable = false)
    private String inquiryCode;
    @Column(nullable = false)
    private String trxStatus;
}
