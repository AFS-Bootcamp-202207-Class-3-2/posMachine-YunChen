package pos.machine;

import pos.entities.Receipt;
import pos.entities.ReceiptItem;

import java.util.*;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {



        List<ItemInfo> totItemInfos = convertToItemInfo(barcodes);


        Receipt receipt = calculateReceipt(totItemInfos);


        return renderReceipt(receipt);
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
     * 将全部ItemInfo转换为收据对象
     * @param itemsWithDetail
     * @return
     */
    public Receipt calculateReceipt(List<ItemInfo> itemsWithDetail) {

        List<ReceiptItem> receiptItems = calculateReceiptItems(itemsWithDetail);
        int totalPrice = calculateTotalPrice(receiptItems);

        Receipt receipt = new Receipt();
        receipt.setItemDetail(receiptItems);
        receipt.setTotalPrice(totalPrice);

        return receipt;
    }

    /**
     * 将每个商品统计收据需要的信息条目
     * @param itemsWithDetail
     * @return
     */
    public List<ReceiptItem> calculateReceiptItems(List<ItemInfo> itemsWithDetail) {
        Map<String, Integer> record = new HashMap<>();
        Map<String, ItemInfo> barcodeMapItem = new HashMap<>();
        for (int i = 0; i < itemsWithDetail.size(); i++) {
            ItemInfo currItemInfo = itemsWithDetail.get(i);
            String itemBarcode = currItemInfo.getBarcode();
            if (!barcodeMapItem.containsKey(itemBarcode)) {
                barcodeMapItem.put(itemBarcode, currItemInfo);
            }

            if (record.containsKey(itemBarcode)) {
                record.put(itemBarcode, record.get(itemBarcode) + 1);
            } else {
                record.put(currItemInfo.getBarcode(), 1);
            }
        }

        List<ReceiptItem> receiptItems = new ArrayList<>();
        Set<String> checkIsExist = new HashSet<>();


        for (int i = 0; i < itemsWithDetail.size(); i++) {
            String currBarcode = itemsWithDetail.get(i).getBarcode();
            if(checkIsExist.contains(currBarcode)) continue;
            checkIsExist.add(currBarcode);
            int quantity = record.get(currBarcode);
            ReceiptItem receiptItem = new ReceiptItem();
            receiptItem.setName(barcodeMapItem.get(currBarcode).getName());
            receiptItem.setQuantity(quantity);
            receiptItem.setUnitPrice(barcodeMapItem.get(currBarcode).getPrice());
            receiptItem.setSubTotal(receiptItem.getUnitPrice() * quantity);

            receiptItems.add(receiptItem);
        }



        return receiptItems;
    }

    /**
     * 统计所有收据的总价格
     * @param receiptItems
     * @return
     */
    public int calculateTotalPrice(List<ReceiptItem> receiptItems) {
        int totalPrice = 0;
        for (int i = 0; i < receiptItems.size(); i++) {
            totalPrice += receiptItems.get(i).getSubTotal();
        }
        return totalPrice;
    }

    /**
     * 将处理好的收据信息整理为文本数据
     * @param receipt
     * @return
     */
    public String renderReceipt(Receipt receipt) {


        return spliceReceipt(receipt);
    }


    /**
     * 划分好各个商品信息的收据
     * @param receipt
     * @return
     */
    public String splicItemsDetail(Receipt receipt) {
        List<ReceiptItem> receiptItemDetail = receipt.getItemDetail();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < receiptItemDetail.size(); i++) {
            ReceiptItem receiptItem = receiptItemDetail.get(i);
            String data = "Name: " + receiptItem.getName() + ", Quantity: " + receiptItem.getQuantity() + ", Unit price: " + receiptItem.getUnitPrice() + " (yuan), Subtotal: " + receiptItem.getSubTotal() + " (yuan)";
            stringBuilder.append(data);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * 总结整理总体收据信息
     * @param receipt
     * @return
     */
    public String spliceReceipt(Receipt receipt) {
        String startLine = "***<store earning no money>Receipt***" + "\n";
        String itemsDetail = splicItemsDetail(receipt);


        String separator = "----------------------\n";
        String totalPrice = "Total: " + receipt.getTotalPrice() + " (yuan)" + "\n";
        String endSeparator = "**********************";
        return startLine + itemsDetail + separator + totalPrice + endSeparator;
    }

}
