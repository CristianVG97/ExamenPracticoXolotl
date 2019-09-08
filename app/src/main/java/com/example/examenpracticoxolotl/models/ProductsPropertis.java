package com.example.examenpracticoxolotl.models;

public class ProductsPropertis {


    public String getProductDisplayName() {
        return productDisplayName;
    }

    public void setProductDisplayName(String productDisplayName) {
        this.productDisplayName = productDisplayName;
    }

    private String productDisplayName;
    private String listPrice;
    private  String  smImage;

    public String getListPrice() {
        return listPrice;
    }

    public void setListPrice(String listPrice) {
        this.listPrice = listPrice;
    }

    public String getSmImage() {
        return smImage;
    }

    public void setSmImage(String smImage) {
        this.smImage = smImage;
    }
}
