package pos.entities;


import lombok.Data;

@Data
public class ReceiptItem {
    private String name;
    private int quantity;
    private int unitPrice;
    private int subTotal;
}
