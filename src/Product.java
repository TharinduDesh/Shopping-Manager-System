public abstract class Product {



/*  making superclass variables private promotes encapsulation
    and controlled access to the internal state of the class.
    If variables are not private, it can lead to a breakdown of encapsulation, potentially causing problems
    in terms of data integrity and maintainability.*/

    private String productID;
    private String productName;
    private int noOfitems;
    private double price;

//    Constructor
    public Product(String productID, String productName, int noOfitems, double price) {
        this.productID = productID;
        this.productName = productName;
        this.noOfitems = noOfitems;
        this.price = price;
    }

    public abstract void displayInfo();

//    Getters and Setters
    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getNoOfitems() {
        return noOfitems;
    }

    public void setNoOfitems(int noOfitems) {
        this.noOfitems = noOfitems;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
