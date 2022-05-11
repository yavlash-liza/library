package by.library.yavlash.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Value
public class BookDamageDto {
     Long id;
     String imagePath;
     String damageDescription;
     Long userId;
     Long orderId;
     Long bookCopyId;
}