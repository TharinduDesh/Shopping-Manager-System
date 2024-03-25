

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class WestminsterShoppingCenterGUI extends JFrame {
    private List<Product> productList;
    private JComboBox<String> productTypeCombo;
    private JTable productTable;
    private JTextArea productDetailsText;
    private JButton addToCart;
    private JButton viewShoppingCart;
    public ShoppingCart shoppingCart;

    public WestminsterShoppingCenterGUI() {

        // Initialize product list as an ArrayList and create a new shopping cart
        this.productList = new ArrayList<>();
        this.shoppingCart = new ShoppingCart();


        // Set the title, size, and default close operation for the JFrame
        setTitle("Westminster Shopping Center");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        InitiateComponents();
        createProductTable();

        setLayout(new BorderLayout());
        add(TopPanel(), BorderLayout.NORTH);
        add(TablePanel(), BorderLayout.CENTER);
        add(ProductDetails(), BorderLayout.SOUTH);

        // Set the location of the JFrame to the center of the screen
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void InitiateComponents() {

        // Create a JComboBox for selecting
        productTypeCombo = new JComboBox<>(new String[]{"All", "Electronic", "Clothing"});

        // add table , text area and buttons
        productTable = new JTable();
        productDetailsText = new JTextArea(10, 50);
        addToCart = new JButton("Add to cart");
        viewShoppingCart = new JButton("View shopping cart");

        addToCart.setEnabled(false);

        // Add an ActionListener to the "View shopping cart" button to perform an action when clicked
        viewShoppingCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showShoppingCart();
            }
        });
    }

    private JPanel TopPanel() {

        // Create a new JPanel to hold components for the top panel
        JPanel panel = new JPanel();
        JPanel selectPanel = new JPanel();

        // Add label for combo box
        selectPanel.add(new JLabel("Select Product Category"));
        selectPanel.add(productTypeCombo);

        panel.add(selectPanel, BorderLayout.CENTER);

        JPanel shoppingCartButtonPanel = new JPanel();
        shoppingCartButtonPanel.add(viewShoppingCart);
        panel.add(shoppingCartButtonPanel, BorderLayout.WEST);

        // Add an ActionListener to the productTypeCombo to update the table
        productTypeCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable();
            }

        });
        return panel;
    }

    private JPanel TablePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(productTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel ProductDetails() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));

        // Add a label indicating the selected product details to the detailsPanel
        detailsPanel.add(new JLabel("Selected Product - Details"));

        JScrollPane scrollPane = new JScrollPane(productDetailsText);
        detailsPanel.add(scrollPane);
        panel.add(detailsPanel, BorderLayout.CENTER);

        JPanel addToCartButtonPanel = new JPanel();
        addToCartButtonPanel.add(addToCart);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.add(Box.createHorizontalGlue());
        centerPanel.add(addToCartButtonPanel);
        centerPanel.add(Box.createHorizontalGlue());

        panel.add(centerPanel, BorderLayout.SOUTH);


        // Add an ActionListener to the "Add to Cart" button to perform an action
        addToCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToShoppingCart();
            }
        });

        return panel;
    }

    private void createProductTable() {

        // Create a DefaultTableModel with a custom getColumnClass method

        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return String.class;
            }
        };


        // Add columns to the model for the product table
        model.addColumn("Product ID");
        model.addColumn("Name");
        model.addColumn("No of available item");
        model.addColumn("Category");
        model.addColumn("Price (€)");
        model.addColumn("Info");

        productTable.setModel(model);

        // Set the selection mode to allow only single row selection
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        // Set a custom cell renderer to highlight rows with low available items in red
        productTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component renderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                int availableItemsColumnIndex = 2;
                int availableItems = Integer.parseInt(table.getValueAt(row, availableItemsColumnIndex).toString());

                // If the number of available items is less than 3, set the text color to red
                if (availableItems < 3) {
                    renderer.setForeground(Color.RED);
                } else {
                    renderer.setForeground(table.getForeground());
                }
                return renderer;
            }
        });

        productTable.getSelectionModel().addListSelectionListener(e -> {
            int row = productTable.getSelectedRow();
            addToCart.setEnabled(row != -1);

            if (row != -1) {
                // If a row is selected, retrieve the product ID from the selected row
                String pid = productTable.getValueAt(row, 0).toString();
                Product selectedProduct = findProByID(pid);

                // creating/adding product details

                if (selectedProduct != null) {

                    // Determine the category of the selected product (Electronic or Clothing)
                    String category = (selectedProduct instanceof Electronic) ? "Electronic" : "Clothing";

                    // Create a product details based on the type of product
                    String detail = "Product ID: " + selectedProduct.getProductID() + "\n" +
                            "Category: " + category + "\n" +
                            "Name : "+ selectedProduct.getProductName();

                    if (selectedProduct instanceof Electronic) {
                        Electronic electronics = (Electronic) selectedProduct;
                        detail += "\nBrand: " + electronics.getBrand() + "\n" +
                                "Warranty Period: " + electronics.getWarranty() + " months" +"\n"+
                                "Items Available: " + selectedProduct.getNoOfitems() + "\n" ;
                    } else if (selectedProduct instanceof Clothing) {
                        Clothing clothing = (Clothing) selectedProduct;
                        detail += "\nSize: " + clothing.getSize() + "\n" +
                                "Color: " + clothing.getColour() + "\n" +
                                "Items Available: " + selectedProduct.getNoOfitems() + "\n" ;
                    }

                    productDetailsText.setText(detail);
                }
            } else {
                productDetailsText.setText("");
            }
        });
    }

    private void updateTable() {

        // Get the DefaultTableModel from the productTable
        DefaultTableModel model=(DefaultTableModel) productTable.getModel();
        model.setRowCount(0);

        String selType=(String) productTypeCombo.getSelectedItem() ;

        // Sort the productList based on Product ID

        productList.sort(Comparator.comparing(Product::getProductID));


        // Iterate through the productList
        for (Product product : productList) {
            if(selType.equals("All") || (selType.equals("Electronic") && product instanceof Electronic) ||
                    (selType.equals("Clothing") && product instanceof Clothing)) {

                String info="";

                if(product instanceof Electronic) {
                    Electronic electronics=(Electronic) product;
                    info =electronics.getBrand()+","+electronics.getWarranty();
                }
                else if (product instanceof Clothing) {
                    Clothing clothing =(Clothing) product;
                    info=clothing.getColour()+","+clothing.getSize();
                }

                // Create an Object array with product details

                Object[] row={product.getProductID(), product.getProductName(),product.getNoOfitems(),
                        (product instanceof Electronic)? "Electronics" : "Clothing",
                        String.format("%.2f", product.getPrice()),
                        info};

                // Add the row to the model
                model.addRow(row);
            }

            addToCart.setEnabled(false);
            productDetailsText.setText("");
        }
    }



    private void addToShoppingCart() {

        // Get the selected row index from the productTable
        int row=productTable.getSelectedRow();

        if(row != -1)
        {
            // Retrieve the product ID from the selected row
            String id=productTable.getValueAt(row,0).toString();


            // Find the product in the product list based on product ID
            Product productSelected=findProByID(id);

            if (productSelected.getNoOfitems()>0) {
                // Decrement the number of available items for the selected product
                productSelected.setNoOfitems(productSelected.getNoOfitems() -1);

                // Add the selected product to the shopping cart
                shoppingCart.addProduct(productSelected);
                JOptionPane.showMessageDialog(this,"Product Is SuccessFully Added To The Cart");
                updateTable();

            }
            else
            {
                // // Display a message if there is no product avilable
                JOptionPane.showMessageDialog(this,"Product is not added to the cart. No Available Items");
            }
        }
    }

    private void showShoppingCart() {
        DefaultTableModel cart= new DefaultTableModel();
        cart.addColumn("Product");
        cart.addColumn("Quantity");
        cart.addColumn("Price");

        for(Product product: shoppingCart.getProducts()) {
            Object[] rowCart={product.getProductName(),1, product.getPrice()};
            cart.addRow(rowCart);
        }

        JTable cTable=new JTable(cart);
        JScrollPane cartScroll = new JScrollPane(cTable);

        double total=shoppingCart.totalCost();
        double finalprice=CalculateFinal();

        JPanel cDetails=new JPanel(new BorderLayout());
        cDetails.add(cartScroll, BorderLayout.CENTER);

        JPanel totalDiscount=new JPanel(new GridLayout(6,2));
        totalDiscount.add(new JLabel("Total "));
        JLabel totalCost=new JLabel(String.valueOf(total) + "€");
        totalCost.setHorizontalAlignment(SwingConstants.RIGHT);
        totalDiscount.add(totalCost);

//         discount

        if(finalprice < total) {
            double firstDiscount = total * 0.1;
            totalDiscount.add(new JLabel("First Purchase Discount (10%) "));

            // Format the discount value to two decimal places
            String formattedDiscount = String.format("%.2f", firstDiscount);

            JLabel firstPurchase = new JLabel("-"+ formattedDiscount + "€");
            firstPurchase.setHorizontalAlignment(SwingConstants.RIGHT);
            totalDiscount.add(firstPurchase);
        }

        if (shoppingCart.getProducts().size()>=3)
        {
            double catergoryDis=total*0.2;
            totalDiscount.add(new JLabel("Three Items In Same Category Discount(20%)"));
            JLabel cdl=new JLabel("-"+catergoryDis + "€");
            cdl.setHorizontalAlignment(SwingConstants.RIGHT);
            totalDiscount.add(cdl);
        }

        totalDiscount.add(new JLabel("Final Total "));
        JLabel finalPrice=new JLabel(String.valueOf(finalprice)+ "€");
        finalPrice.setHorizontalAlignment(SwingConstants.RIGHT);
        totalDiscount.add(finalPrice);

        JFrame cartDetail=new JFrame("Shopping Cart");
        cartDetail.setLayout(new BorderLayout());
        cartDetail.add(cDetails, BorderLayout.CENTER);

        cartDetail.add(totalDiscount,BorderLayout.SOUTH);
        cartDetail.setSize(600,300);
        cartDetail.setLocationRelativeTo(null);
        cartDetail.setVisible(true);
    }

    private double CalculateFinal() {
        double total=shoppingCart.totalCost();
        double finalprice=total;

        if(shoppingCart.getProducts().size()>=3)
        {
            finalprice*=0.8; // This one for 20%
        }
        if(total>0)
        {
            finalprice*=0.9; // This one for 10%
        }
        return finalprice;
    }


    // Method to find a product in the productList based on its product ID
    private Product findProByID(String pid) {

        // Iterate through the productList
        for (Product product : productList)
        {
            if(pid != null && pid.equals(product.getProductID()))
            {
                // Return the product if a match is found
                return product;
            }
        }
        // Return null if no match is found
        return null;
    }

    // Method to set the productList and update the table
    public void setProducts(List<Product> productList) {
        this.productList=productList;
        // Update the product table to reflect changes
        updateTable();
    }
}

