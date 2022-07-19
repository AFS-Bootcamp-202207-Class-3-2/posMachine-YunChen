package pos.machine;

import pos.entities.Receipt;
import pos.entities.ReceiptItem;

import java.util.ArrayList;
import java.util.List;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        String startLine = "***<store earning no money>Receipt***" + System.lineSeparator();


        return null;
    }

    /**
     * load数据
     * 将商品条形码都转换为ItemInfo
     * @param barcodes
     * @return
     */
    public List<ItemInfo> convertToItemInfo(List<String> barcodes) {
        List<ItemInfo> itemInfos = ItemDataLoader.loadAllItemInfos();

        List<ItemInfo> reItemInfos = new ArrayList<>();
        for (int i = 0; i < barcodes.size(); i++) {
            String currBarcode = barcodes.get(i);
            for (int j = 0; j < itemInfos.size(); j++) {
                if (currBarcode!=null && itemInfos.get(j).getBarcode().equals(currBarcode)) {
                    ItemInfo data = itemInfos.get(j);
                    reItemInfos.add(new ItemInfo(data.getBarcode(), data.getName(), data.getPrice()));
                }
            }
        }
        return reItemInfos;
    }


    /**
     * 将全部ItemInfo转换为收据
     * @param itemsWithDetail
     * @return
     */
    public Receipt calculateReceipt(List<ItemInfo> itemsWithDetail) {
        return null;
    }

    /**
     * 将每个商品统计收据需要的信息条目
     * @param itemsWithDetail
     * @return
     */
    public List<ReceiptItem> calculateReceiptItems(List<ItemInfo> itemsWithDetail) {
        return null;
    }

    /**
     * 统计所有收据的总价格
     * @param receiptItems
     * @return
     */
    public int calculateTotalPrice(List<ReceiptItem> receiptItems) {
        return 0;
    }

    /**
     * 将处理好的收据信息整理为文本数据
     * @param receipt
     * @return
     */
    public String renderReceipt(Receipt receipt) {
        return null;
    }


    /**
     * 划分好各个商品信息的收据
     * @param receipt
     * @return
     */
    public String splicItemsDetail(Receipt receipt) {
        return null;
    }

    /**
     * 总结整理总体收据信息
     * @param receipt
     * @return
     */
    public String spliceReceipt(Receipt receipt) {
        return null;
    }

}
