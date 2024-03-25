public class Clothing extends Product {

    private String size;
    private String colour;

    public Clothing(String productID, String productName, int noOfitems, double price, String size, String colour){
        super(productID, productName, noOfitems, price);
        this.size = size;
        this.colour = colour;
    }

    public String getSize(){
        return size;
    }

    public void setSize(String size){
        this.size = size;
    }

    public String getColour(){
        return colour;
    }

    public void setColour(String colour){
        this.colour = colour;
    }

    @Override
    public void displayInfo() {
        System.out.println("-> Product ID            : "+getProductID());
        System.out.println("-> Product Name          : "+getProductName());
        System.out.println("-> No of available items : "+getNoOfitems());
        System.out.println("-> Product Price         : "+getPrice());
        System.out.println("-> Size                  : "+getSize());
        System.out.println("-> Colour                : "+getColour());
    }

    // Can't use toString method cause you can't call toString method in Product. You need to cast Product class, if you want to call it in product method.
}
