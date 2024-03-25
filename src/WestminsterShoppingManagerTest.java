//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class WestminsterShoppingManagerTest {
//
//    private WestminsterShoppingManager shoppingManager;
//
//    @BeforeEach
//    void setUp() {
//        shoppingManager = new WestminsterShoppingManager();
//    }
//
//    @Test
//    void addProduct() {
//        // Test adding an electronic product
//        shoppingManager.addProduct("Electronic", "E001", "Laptop", 5, 800.00, "Dell", 12);
//        assertEquals(1, shoppingManager.getSystemProductList().size());
//
//        // Test adding a clothing product
//        shoppingManager.addProduct("Clothing", "C001", "T-Shirt", 10, 20.00, "M", "Blue");
//        assertEquals(2, shoppingManager.getSystemProductList().size());
//    }
//
//    @Test
//    void deleteProduct() {
//        // Test deleting an existing product
//        shoppingManager.deleteProduct("E001");
//        assertEquals(0, shoppingManager.getSystemProductList().size());
//
//        // Test deleting a non-existing product
//        shoppingManager.deleteProduct("InvalidID");
//        assertEquals(0, shoppingManager.getSystemProductList().size());
//    }
//
//    @Test
//    void saveItemsToFile() {
//        // Test saving items to a file
//        shoppingManager.addProduct("Electronic", "E001", "Laptop", 5, 800.00, "Dell", 12);
//        shoppingManager.addProduct("Clothing", "C001", "T-Shirt", 10, 20.00, "M", "Blue");
//
//        assertDoesNotThrow(() -> shoppingManager.saveItemsToFile());
//        // Additional assertions can be added to check the content of the saved file
//    }
//
//    @Test
//    void loadFromFile() {
//        // Test loading items from a file
//        assertDoesNotThrow(() -> shoppingManager.loadFromFile());
//        assertEquals(2, shoppingManager.getSystemProductList().size());
//        // Additional assertions can be added to check the loaded items
//    }
//
//    @Test
//    void createProduct() {
//        // Test creating an electronic product
//        Product electronic = shoppingManager.createProduct("Electronic", "E001", "Laptop", 5, 800.00, new String[]{"Dell", "12"});
//        assertTrue(electronic instanceof Electronic);
//
//        // Test creating a clothing product
//        Product clothing = shoppingManager.createProduct("Clothing", "C001", "T-Shirt", 10, 20.00, new String[]{"M", "Blue"});
//        assertTrue(clothing instanceof Clothing);
//
//        // Test creating a product with an invalid type
//        assertThrows(IllegalArgumentException.class,
//                () -> shoppingManager.createProduct("InvalidType", "P001", "Product", 1, 10.00, new String[]{"Info"}));
//    }
//}
