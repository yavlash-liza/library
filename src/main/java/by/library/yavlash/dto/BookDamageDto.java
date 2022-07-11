package by.library.yavlash.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BookDamageDto {
     Long id;
     String imagePath;
     String damageDescription;
     Long userId;
     Long orderId;
     Long bookCopyId;
}