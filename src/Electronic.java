public class Electronic extends Product {

    private String brand;
    private int warranty;

    public Electronic (String productID, String productName, int noOfitems, double price, String brand, int warranty) {
        super(productID,productName,noOfitems,price);
        this.brand = brand;
        this.warranty = warranty;
    }


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarranty() {
        return warranty;
    }

    public void setWarranty(int warranty) {
        this.warranty = warranty;
    }

    @Override
    public void displayInfo() {
        System.out.println("-> Product ID            : "+getProductID());
        System.out.println("-> Product Name          : "+getProductName());
        System.out.println("-> No of available items : "+getNoOfitems());
        System.out.println("-> Product Price         : "+getPrice());
        System.out.println("-> Product Brand         : "+getBrand());
        System.out.println("-> Product warranty      :"+getWarranty());
    }




}
