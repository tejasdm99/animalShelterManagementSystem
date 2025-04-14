//E-commerce Order Management System
import java.util.*;

// ---------- Interfaces ----------

interface IPaymentProcessor {
    boolean processPayment(double amount);
}

interface IStockManager {
    boolean checkStock(int quantity);
    void updateStock(int quantity);
}

// ---------- Product Class ----------

class Product implements IStockManager {
    private String name;
    private double price;
    private int stock;

    public Product(String name, double price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String getDetails() {
        return name + " - $" + price + " - Stock: " + stock;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public boolean checkStock(int quantity) {
        return stock >= quantity;
    }

    @Override
    public void updateStock(int quantity) {
        stock -= quantity;
    }
}

// ---------- Customer Class ----------

class Customer {
    private String name;
    private String email;
    private String address;

    public Customer(String name, String email, String address) {
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public String getContactInfo() {
        return name + ", " + email + ", " + address;
    }
}

// ---------- OrderItem & Order Class ----------

class OrderItem {
    Product product;
    int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }
}

class Order {
    private Customer customer;
    private List<OrderItem> items = new ArrayList<>();

    public Order(Customer customer) {
        this.customer = customer;
    }

public void addProduct(Product product, int quantity) {
    if (product.checkStock(quantity)) {
        items.add(new OrderItem(product, quantity));
        product.updateStock(quantity);
        System.out.println(quantity + " x " + product.getName() + " added to order.");
    } else {
        System.out.println("Not enough stock for " + product.getName());
    }
}

    public List<OrderItem> getItems() {
        return items;
    }

    public double getTotalAmount() {
        return items.stream().mapToDouble(OrderItem::getTotalPrice).sum();
    }

    public Customer getCustomer() {
        return customer;
    }
}

// ---------- Payment Processors ----------

class CreditCardProcessor implements IPaymentProcessor {
    public boolean processPayment(double amount) {
        System.out.println("Processing credit card payment of $" + amount);
        return true;
    }
}

class PayPalProcessor implements IPaymentProcessor {
    public boolean processPayment(double amount) {
        System.out.println("Processing PayPal payment of $" + amount);
        return true;
    }
}

// ---------- Invoice Generator ----------

class InvoiceGenerator {
    public void generateInvoice(Order order) {
        System.out.println("\n--- Invoice ---");
        for (OrderItem item : order.getItems()) {
            System.out.println(item.product.getName() + " x" + item.quantity + " = $" + item.getTotalPrice());
        }
        System.out.println("Total: $" + order.getTotalAmount());
        System.out.println("Customer: " + order.getCustomer().getContactInfo());
    }
}

// ---------- Order Processor ----------

class OrderProcessor {
    private IPaymentProcessor paymentProcessor;
    private InvoiceGenerator invoiceGenerator;

    public OrderProcessor(IPaymentProcessor paymentProcessor, InvoiceGenerator invoiceGenerator) {
        this.paymentProcessor = paymentProcessor;
        this.invoiceGenerator = invoiceGenerator;
    }

    public void processOrder(Order order) {
        System.out.println("\nStep 2: Checking stock and processing order...");

        for (OrderItem item : order.getItems()) {
            if (!item.product.checkStock(item.quantity)) {
                System.out.println("Order failed: Not enough stock for " + item.product.getName());
                return;
            }
        }

        double total = order.getTotalAmount();
        boolean paid = paymentProcessor.processPayment(total);

        if (paid) {
            for (OrderItem item : order.getItems()) {
                item.product.updateStock(item.quantity);
            }
            System.out.println("Step 3: Payment successful.");
            invoiceGenerator.generateInvoice(order);
            System.out.println("Step 4: Invoice generated and stock updated.");
        } else {
            System.out.println("Payment failed. Order not completed.");
        }
    }
}

// ---------- Main App ----------

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Products
        Product laptop = new Product("Laptop", 1000.0, 10);
        Product phone = new Product("Smartphone", 500.0, 20);
        Product headphones = new Product("Headphones", 100.0, 30);

        List<Product> products = Arrays.asList(laptop, phone, headphones);

        // Customer Info
        System.out.println("Enter Customer Details:");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();

        Customer customer = new Customer(name, email, address);
        Order order = new Order(customer);

        // Step 1: Select products
        System.out.println("\nStep 1: Customer selects products to add to the order.");
        while (true) {
            System.out.println("\nAvailable Products:");
            for (int i = 0; i < products.size(); i++) {
                System.out.println((i + 1) + ". " + products.get(i).getDetails());
            }
            System.out.print("Enter product number to add to cart (or 0 to finish): ");
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice == 0) break;
            if (choice < 1 || choice > products.size()) {
                System.out.println("Invalid choice.");
                continue;
            }

            System.out.print("Enter quantity: ");
            int qty = Integer.parseInt(scanner.nextLine());
            order.addProduct(products.get(choice - 1), qty);
        }

        // If no products selected, exit
        if (order.getItems().isEmpty()) {
            System.out.println("\nNo products selected. Exiting without processing the order.");
            return;
        }

        // Ask for payment method
        System.out.print("\nSelect payment type (credit/paypal): ");
        String paymentType = scanner.nextLine().toLowerCase();

        IPaymentProcessor paymentProcessor;
        switch (paymentType) {
            case "credit":
                paymentProcessor = new CreditCardProcessor();
                break;
            case "paypal":
                paymentProcessor = new PayPalProcessor();
                break;
            default:
                System.out.println("Invalid payment type.");
                return;
        }

        OrderProcessor processor = new OrderProcessor(paymentProcessor, new InvoiceGenerator());
        processor.processOrder(order);

        scanner.close();
    }
}
