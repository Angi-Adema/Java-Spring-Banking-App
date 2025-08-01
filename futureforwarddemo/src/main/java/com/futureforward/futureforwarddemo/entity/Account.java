package com.futureforward.futureforwarddemo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double balance;

    @Column(nullable = false)
    private String accountType;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "sourceAccount", cascade = CascadeType.ALL)
    private List<Transaction> outgoingTransactions;

    @OneToMany(mappedBy = "destinationAccount", cascade = CascadeType.ALL)
    private List<Transaction> incomingTransactions;

}
