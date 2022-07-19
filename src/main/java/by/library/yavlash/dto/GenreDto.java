package by.library.yavlash.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GenreDto {
     Long id;
     String genreName;
}