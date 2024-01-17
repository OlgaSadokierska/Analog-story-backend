package com.olgasadokierska.analogstory.user.dtos;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private Long id;
    private Long userId;
    private Long productId;
    private Boolean isPurchased;
    private String productInfo;
}
