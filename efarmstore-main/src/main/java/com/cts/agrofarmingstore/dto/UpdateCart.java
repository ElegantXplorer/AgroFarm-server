package com.cts.agrofarmingstore.dto;

import lombok.Data;
// Creating dto class for required variable of cart class used in cart service
@Data
public class UpdateCart {
    private int quantity;
    private Long productId;
}
