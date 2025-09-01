// This DTO defines the shape of the JSON response that the API sends back to the client when they request customer data.
// When it sends customer data to the outside world (Swagger, front-end, mobile app, payment service), this is all that will be shared.
package com.futureforward.futureforwarddemo.dto;

import lombok.Data;

@Data
public class CustomerResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
