import java.util.ArrayList;
import java.util.List;
public class ShoppingCart {


    private List<Product> shoppingCartProductList;

    public ShoppingCart(){
        this.shoppingCartProductList = new ArrayList<>();
    }


//    To add a product to the shopping cart
    public void addProduct (Product product){

        shoppingCartProductList.add(product);
    }

//    To remove product from the shopping cart
    public void removeProduct (Product product){

        shoppingCartProductList.remove(product);
    }

//    To calculate the total cost of products
    public double totalCost(){
        double total = 0.0;


//        for each loop

        for (Product product : shoppingCartProductList){
            total += product.getPrice();
        }
        return total;
    }

    public List<Product> getProducts(){
        return shoppingCartProductList;
    }



}
