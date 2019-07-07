package ir.mab.booksreviews.history;

import java.util.ArrayList;
import java.util.List;

public class BarcodeList {
    private List<Barcode> barcodeList;

    public BarcodeList() {
        this.barcodeList = new ArrayList<>();
    }

    public List<Barcode> getBarcodeList() {
        return barcodeList;
    }

    public void setBarcodeList(List<Barcode> barcodeList) {
        this.barcodeList = barcodeList;
    }

    public void addBarcode(Barcode barcode){
        this.barcodeList.add(barcode);
    }
}
