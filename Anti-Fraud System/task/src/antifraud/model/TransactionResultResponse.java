package antifraud.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransactionResultResponse {
    TransactionResult result;
    String info;

    public TransactionResultResponse(TransactionResult result) {
        this.result = result;
    }

    public TransactionResult getResult() {
        return result;
    }
    public TransactionResultResponse(){

    }

    public void setResult(TransactionResult result) {
        this.result = result;
    }
}
