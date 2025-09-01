// DTOs protect sensitive information by creating a template of what is to be returned to the client. Otherwise we risk everything in the entity being exposed.
package com.futureforward.futureforwarddemo.dto;

// Jakarta rule annotations like @NotNull, @NotBlank, @Email, @Size are rules applied to fields. When the request comes into the controller, Spring
// automatically runs those rules befor the controller method executes. If the rules are broken, a MethodArgumentNotValidException is thrown by Spring.
// Here we are saying that in order to create a new customer account, we must have the customer ID to identify the account holder as well as the type of
// account that should be created.
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data   // @Data generates getters/setters, equals/hashCode, and toString methods at compile time. (lombok.data)
public class CreateAccountRequest {

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotBlank(message = "Account type is required")
    private String accountType;
}


// DTOs are data carriers, so they always need getters/setters, equals/hashCode, and toString methods. Lombok saves having to write all this extra code.
// Without Lombok:
// package com.futureforward.futureforwarddemo.dto;

// import jakarta.validation.constraints.NotNull;
// import jakarta.validation.constraints.NotBlank;

// public class CreateAccountRequest {

//     @NotNull(message = "Customer ID is required")
//     private Long customerId;

//     @NotBlank(message = "Account type is required")
//     private String accountType;

//     // Getters
//     public Long getCustomerId() {
//         return customerId;
//     }

//     public String getAccountType() {
//         return accountType;
//     }

//     // Setters
//     public void setCustomerId(Long customerId) {
//         this.customerId = customerId;
//     }

//     public void setAccountType(String accountType) {
//         this.accountType = accountType;
//     }

//     // toString
//     @Override
//     public String toString() {
//         return "CreateAccountRequest{" +
//                 "customerId=" + customerId +
//                 ", accountType='" + accountType + '\'' +
//                 '}';
//     }

//     // equals
//     @Override
//     public boolean equals(Object o) {
//         if (this == o) return true;
//         if (!(o instanceof CreateAccountRequest)) return false;
//         CreateAccountRequest that = (CreateAccountRequest) o;
//         return (customerId != null ? customerId.equals(that.customerId) : that.customerId == null) &&
//                (accountType != null ? accountType.equals(that.accountType) : that.accountType == null);
//     }

//     // hashCode
//     @Override
//     public int hashCode() {
//         int result = (customerId != null ? customerId.hashCode() : 0);
//         result = 31 * result + (accountType != null ? accountType.hashCode() : 0);
//         return result;
//     }
// }
