package com.cts.agrofarmingstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
// Creating dto class for required variable of cart class used in cart service
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CartDTO {
    private int quantity;
    private Long productId;
    private Long userId;
}
