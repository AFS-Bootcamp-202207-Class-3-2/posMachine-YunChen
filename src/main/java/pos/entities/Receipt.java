package pos.entities;

import lombok.Data;

import java.util.List;

@Data
public class Receipt {
    private List<ReceiptItem> itemDetail;
    private int totalPrice;

}
