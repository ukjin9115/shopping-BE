package ukgo.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesDto {
    private Integer itemId;
    private Integer price;
    private Integer memberId;
    private Integer count;
    private LocalDateTime created;
}
