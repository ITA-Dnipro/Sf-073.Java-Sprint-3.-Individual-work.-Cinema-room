package antifraud.domain.model;

import antifraud.domain.model.enums.TransactionResult;
import antifraud.domain.model.enums.WorldRegion;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PACKAGE)
@Entity
@ToString
@Table(name = "transactions", indexes = {
        @Index(name = "card_date_idx", columnList = "cardNumber, dateTime"),
})
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.PRIVATE)
    private Long id;
    private Long money;
    private String ipAddress;
    private String cardNumber;
    @Enumerated(EnumType.STRING)
    private TransactionResult transactionResult;
    @Enumerated(EnumType.STRING)
    private WorldRegion worldRegion;
    private LocalDateTime dateTime;
    private String transactionInfo;
}