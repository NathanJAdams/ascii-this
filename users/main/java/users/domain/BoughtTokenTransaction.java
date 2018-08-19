package users.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoughtTokenTransaction extends TokenTransaction {
    private String currency;
    private LocalDateTime dateTime;
}
