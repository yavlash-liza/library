package by.library.yavlash.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@AllArgsConstructor
public class BookDamage extends BaseEntity {
    private Long id;
    private String imagePath;
    private String damageDescription;
    private Long userId;
    private Long orderId;
    private Long bookCopyId;
}