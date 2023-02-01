package antifraud.model;

import antifraud.annotation.RegionConstraint;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long transactionId;
    @Positive
    @NotNull
    Long amount;
    @NotEmpty
    String ip;
    @NotEmpty
    String number;
    @RegionConstraint
    String region;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime date;

    String result;


    String feedback;

    public Transaction(Long amount) {
        this.amount = amount;
    }

    public Transaction() {

    }

    public String getFeedback() {
        return Objects.requireNonNullElse(feedback, "");
    }
}
