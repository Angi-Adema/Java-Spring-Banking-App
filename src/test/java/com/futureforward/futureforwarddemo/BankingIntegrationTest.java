// package com.futureforward.futureforwarddemo;

// import com.futureforward.futureforwarddemo.entity.*;
// import com.futureforward.futureforwarddemo.service.*;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;

// import java.util.List;

// import static org.junit.jupiter.api.Assertions.*;

// @SpringBootTest
// public class BankingIntegrationTest {

//     // Create and save a new customer.
//     @Autowired
//     private CustomerService customerService;

//     // Create multiple accounts. 
//     @Autowired
//     private AccountService accountService;

//     // Deposit and transfer money between accounts.
//     @Autowired
//     private TransactionService transactionService;

//     @Test
//     void testAccountCreationAndTransfer() {
//         // Create customer
//         Customer alice = new Customer(null, "Alice", "Smith", "alice@example.com", "password", null);
//         alice = customerService.registerCustomer(alice);

//         // Create 2 accounts
//         Account checking = accountService.createAccount(alice, "checking");
//         Account savings = accountService.createAccount(alice, "savings");

//         // Deposit $1000 to checking
//         transactionService.deposit(checking, 1000.00);

//         // Transfer $250 to savings
//         boolean success = transactionService.transfer(checking, savings, 250.00);

//         assertTrue(success);
//         assertEquals(750.00, accountService.getAccountsByCustomerId(alice.getId())
//                 .stream().filter(a -> a.getAccountType().equals("checking")).findFirst().get().getBalance());

//         assertEquals(250.00, accountService.getAccountsByCustomerId(alice.getId())
//                 .stream().filter(a -> a.getAccountType().equals("savings")).findFirst().get().getBalance());

//         List<Transaction> allTransactions = transactionService.getAllTransactionsForAccount(checking.getId());
//         assertEquals(2, allTransactions.size()); // deposit + transfer
//     }
// }
