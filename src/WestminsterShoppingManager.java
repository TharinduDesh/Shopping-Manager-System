import java.io.*;
import java.sql.SQLOutput;
import java.util.*;
public class WestminsterShoppingManager implements ShoppingManager {


    private static final int MAX_PRODUCT = 50;
    private List<Product> systemProductList;
    private static Scanner input = new Scanner(System.in);

    public WestminsterShoppingManager() {
        this.systemProductList = new ArrayList<>(); // Initialize the systemProductList
        loadFromFile();
    }


    public static void main(String[] args) {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        shoppingManager.displayMenu();
    }

    public void displayMenu() {
        while (true) {

            System.out.println("<------------ Menu ------------>");
            System.out.println("1. Add a new product");
            System.out.println("2. Delete a product");
            System.out.println("3. Print the list of the products");
            System.out.println("4. Save in file");
            System.out.println("5. Load GUI");
            System.out.println("6. Exit");
            System.out.println(" ");

            System.out.print("Enter your choice : ");
            int mgChoice = input.nextInt();

            switch (mgChoice) {
                case 1:
                    addProduct();
//                    saveItemsToFile();
                    break;
                case 2:
                    deleteProduct();
//                    saveItemsToFile();
                    break;
                case 3:
                    printProductList();
                    break;
                case 4:
                    saveItemsToFile();
                    break;
                case 5:
                    System.out.println("GUI Launching........"+ "\n");
                    WestminsterShoppingCenterGUI gui2 = new WestminsterShoppingCenterGUI();
                    gui2.setProducts(systemProductList);
                    break;
                case 6:
                    System.out.println("Exiting the application.");
                    System.exit(0);

                default:
                    System.out.println("Invalid input. Please enter valid input");
            }
        }
    }


    public void addProduct(){


        //Checking if products are exceed the limit before adding new products

        if (systemProductList.size() > MAX_PRODUCT){
            System.out.println("Can't add more products.Maximum limit reached.");
            return;
        }

        System.out.println("<------------ Add a new product ------------>");
        System.out.println("1. Electronics");
        System.out.println("2. Clothing");


        System.out.print("Enter the type of the product : ");
        // validate user's product choice (if didn't input no between 1 and 2 it'll ask again for input)
        int productType = 0;
        while (productType != 1 && productType != 2) {
            try {
                productType = input.nextInt();
                if (productType != 1 && productType != 2) {
                    System.out.print("Invalid input. Please enter 1 for Electronics or 2 for Clothing : ");
                }
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter a number : ");
                input.nextLine(); // Consume the invalid input
            }
        }

        // ask again for product id input if user leave it as empty

        input.nextLine();

        System.out.print("Enter product ID : ");
        String productID = input.nextLine();

        while (productID.isEmpty()) {
            System.out.println("Product ID cannot be empty. Please enter a valid product ID.");
            System.out.print("Enter product ID : ");
            productID = input.nextLine();
        }




        System.out.print("Enter product name : ");
        String productName = input.nextLine();


        System.out.print("Enter number of available items : ");
        int availableItem = 0;
        while (true) {
            try {
                availableItem = Integer.parseInt(input.next());
                if (availableItem < 0 ) {
                    System.out.print("Invalid input.Please enter a valid number of items : ");
                } else {
                    break; // Exit the loop if the input is valid
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number of items : ");
            }
        }
        input.nextLine();


        System.out.print("Enter price : ");

        double productPrice = 0.0;
        // validating whether user inputting right price
        while (true) {
            try {
                productPrice = Double.parseDouble(input.next());
                if (productPrice <= 0) {
                    System.out.print("Invalid input. Please enter a positive number for the price : ");
                } else {
                    break; // Exit the loop if the input is valid
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number for the price : ");
            }
        }
        input.nextLine();


        if (productType ==1){

            System.out.print("Enter the brand name : ");
            String brand = input.nextLine();

            System.out.print("Enter warranty period : ");
            int warranty = input.nextInt();

            Electronic electronics = new Electronic(productID, productName, availableItem, productPrice,
                    brand, warranty);
            systemProductList.add(electronics);
            System.out.println("Electronic product added successfully \n");

        } else if (productType==2) {
            System.out.print("Enter size : ");
            String size = input.nextLine();

            System.out.print("Enter color : ");
            String colour = input.nextLine();

            Clothing clothings = new Clothing(productID, productName, availableItem, productPrice, size, colour);
            systemProductList.add(clothings);
            System.out.println("Clothing product added successfully \n");
            System.out.println(" ");
        } else{
            System.out.println("Invalid product type. Enter valid product type.");
        }
    }

    public void deleteProduct(){
        //Checking if the systemProductList is empty
        if(systemProductList.isEmpty()){
            System.out.println("No product in the system to remove.");
            // if there is no items in system this code will execute and return. Then it'll print the menu again
            return;
        }

        System.out.print("Enter the product ID to delete : ");
        String deleteProduct = input.next();

        // Creating an iterator for the systemProductList to iterate

        Iterator<Product> iterator = systemProductList.iterator();

        while (iterator.hasNext()){
            Product product = iterator.next();

            // Checking if the current product has the entered productID for deletion
            if(product.getProductID().equals(deleteProduct)){
                iterator.remove();
                System.out.println("Product deleted successfully");
                product.displayInfo();
                System.out.println("Total number of available product in the system" + " "+systemProductList.size());


                // Update the file after deleting the product
                saveItemsToFile();
                return;

            }
        }

        System.out.println("Product with ID" + deleteProduct + "not found in the system.");
    }



    public void printProductList(){
        //Checking if the systemProductList is empty
        if(systemProductList.isEmpty()){
            System.out.println("No product available to print.");
            return;
        }

        //Sorting the systemProductList based on ProductID

        Collections.sort(systemProductList, Comparator.comparing(Product::getProductID));

        System.out.println("List of products in the system.");

        for(Product product : systemProductList){
            product.displayInfo();
            // Checking the type of the product using instanceof
            if (product instanceof Electronic){
                System.out.println("-> Type : Electronics \n");
            } else if (product instanceof Clothing){
                System.out.println("-> Type : Clothing \n");
            }
        }
    }


    public void saveItemsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Inventory.txt"))) {
            for (Product product : systemProductList) {
                writer.write(getProductTypeString(product) + "|");
                writer.write(product.getProductID() + "|");
                writer.write(product.getProductName() + "|");
                writer.write(product.getNoOfitems() + "|");
                writer.write(product.getPrice() + "|");

                // Checking if the product is an instance of Electronic or Clothing
                if (product instanceof Electronic) {
                    writer.write(((Electronic) product).getBrand() + "|");
                    writer.write(((Electronic) product).getWarranty() + "|");
                } else if (product instanceof Clothing) {
                    writer.write(((Clothing) product).getSize() + "|");
                    writer.write(((Clothing) product).getColour() + "|");
                }

                writer.newLine();
            }
            System.out.println("Product inventory saved to file successfully \n");
        } catch (IOException e) {
            System.out.println("\n");
            // Handling IOException if there is an issue writing to the file
            System.out.println("Error: Saving to file failed." + e.getMessage());
        }
    }

    private String getProductTypeString(Product product){
        if (product instanceof Electronic){
            return "Electronic";
        } else if (product instanceof Clothing){
            return "Clothing";
        }
        // If the product is not an instance of Electronic or Clothing, throw an exception
        throw new IllegalArgumentException("Invalid product type : "+product.getClass().getSimpleName());
    }

    private void loadFromFile(){
        try(BufferedReader reader = new BufferedReader(new FileReader("Inventory.txt"))){
                String line;
                while((line = reader.readLine()) != null ){
                    // Splitting the line into fields using the "|"
                    String[] fields = line.split("\\|");

                    if(fields.length < 5){
                        System.out.println("Invalid data in the file"+ line);
                        continue;
                    }


                    String productType = fields[0];
                    String productID = fields[1];
                    String productName = fields[2];
                    int availableItems = Integer.parseInt(fields[3]);
                    double price = Double.parseDouble(fields[4]);

                    Product productFromFile = createProduct(productType,productID,productName,availableItems,
                            price,fields);
                    systemProductList.add(productFromFile);

                }
            System.out.println("\nInventory loaded from file successfully.\n");
            } catch (IOException | NumberFormatException e){
                System.out.println("Error : Loading from file failed."+e.getMessage());
        }
    }


    private Product createProduct (String productType, String productID, String productName, int availableItems,
                                   double price, String[] fields){
        switch (productType){
            case "Electronic":
                String brand = fields[5];
                int warrantyPeriod = Integer.parseInt(fields[6]);
                return new Electronic(productID,productName,availableItems,price,brand,warrantyPeriod);
            case "Clothing":
                String size = fields[5];
                String colour = fields[6];
                return new Clothing(productID,productName,availableItems,price,size,colour);
            default :
                throw new IllegalArgumentException("Invalid product type : "+productType);
        }
    }

}

















