package antifraud.model;
public class TransactionResultResponse {
    TransactionResult result;

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
