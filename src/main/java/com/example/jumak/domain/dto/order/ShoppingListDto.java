package com.example.jumak.domain.dto.order;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@Data
@NoArgsConstructor
public class ShoppingListDto {
    private Long productNumber;
    private Long userNumber;
    private Long productCount;
}
