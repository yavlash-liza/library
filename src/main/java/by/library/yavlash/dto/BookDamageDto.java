package by.library.yavlash.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookDamageDto {
    private Long id;
    private String imagePath;
    private String damageDescription;
    private Long userId;
    private Long orderId;
}