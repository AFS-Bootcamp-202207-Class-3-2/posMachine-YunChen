package pos.entities;

import lombok.Data;
import lombok.Setter;
import pos.machine.ItemDataLoader;
import pos.machine.PosMachine;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Data
@Setter
public class Receipt {
    private List<ReceiptItem> itemDetail;
    private int totalPrice;

    public List<ReceiptItem> getItemDetail() {
        return itemDetail;
    }

    public void setItemDetail(List<ReceiptItem> itemDetail) {
        this.itemDetail = itemDetail;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
