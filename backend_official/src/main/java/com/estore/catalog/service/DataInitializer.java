package com.estore.catalog.service;

import com.estore.catalog.entity.Category;
import com.estore.catalog.entity.Product;
import com.estore.catalog.repository.CategoryRepository;
import com.estore.catalog.repository.ProductRepository;
import com.estore.inventory.entity.Inventory;
import com.estore.inventory.repository.InventoryRepository;
import com.estore.customer.entity.User;
import com.estore.customer.entity.Profile;
import com.estore.customer.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;
    private final InventoryRepository inventoryRepo;
    private final UserRepository userRepo;

    public DataInitializer(ProductRepository productRepo,
                           CategoryRepository categoryRepo,
                           InventoryRepository inventoryRepo,
                           UserRepository userRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.inventoryRepo = inventoryRepo;
        this.userRepo = userRepo;
    }

    @Override
    public void run(String... args) throws Exception {

        // Categories
        Category electronics = categoryRepo.save(new Category(null, "Electronics", "Devices and Gadgets", null));
        Category books       = categoryRepo.save(new Category(null, "Books", "Physical and E-books", null));
        Category sport       = categoryRepo.save(new Category(null, "Sport", "Sports Equipment", null));
        Category clothing    = categoryRepo.save(new Category(null, "Clothing", "Apparel and Fashion", null));

        // Electronics — real Unsplash images
        Product p1  = productRepo.save(new Product(null, "Laptop Pro",
            "Powerful workstation laptop with 16GB RAM and 512GB SSD", 1500.0,
            "https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=400&h=260&fit=crop", electronics));
        Product p2  = productRepo.save(new Product(null, "Smartphone X",
            "Latest flagship phone with AMOLED display and 5G", 999.0,
            "https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=400&h=260&fit=crop", electronics));
        Product p3  = productRepo.save(new Product(null, "Wireless Headphones",
            "Noise-cancelling over-ear headphones with 30h battery", 199.0,
            "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=400&h=260&fit=crop", electronics));
        Product p4  = productRepo.save(new Product(null, "Mechanical Keyboard",
            "RGB backlit mechanical gaming keyboard with Cherry MX switches", 89.0,
            "https://images.unsplash.com/photo-1587829741301-dc798b83add3?w=400&h=260&fit=crop", electronics));
        Product p5  = productRepo.save(new Product(null, "USB-C Monitor",
            "27-inch 4K USB-C monitor, perfect for productivity and design", 450.0,
            "http://localhost:8080/images/usb-c-monitor.jpg", electronics));

        // Books
        Product p6  = productRepo.save(new Product(null, "Java Programming",
            "Learn Spring Boot and microservices from scratch", 45.0,
            "https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?w=400&h=260&fit=crop", books));
        Product p7  = productRepo.save(new Product(null, "Clean Code",
            "Best practices in software engineering by Robert C. Martin", 39.0,
            "http://localhost:8080/images/clean-code.jpg", books));
        Product p8  = productRepo.save(new Product(null, "Design Patterns",
            "Gang of Four — the classic software design patterns explained", 42.0,
            "https://images.unsplash.com/photo-1481627834876-b7833e8f5570?w=400&h=260&fit=crop", books));

        // Sport
        Product p9  = productRepo.save(new Product(null, "Running Shoes",
            "Lightweight breathable sport running shoes for all terrains", 120.0,
            "https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=400&h=260&fit=crop", sport));
        Product p10 = productRepo.save(new Product(null, "Yoga Mat",
            "Non-slip premium yoga and fitness mat — 6mm thickness", 35.0,
            "https://images.unsplash.com/photo-1599901860904-17e6ed7083a0?w=400&h=260&fit=crop", sport));

        // Clothing
        Product p11 = productRepo.save(new Product(null, "Hoodie Classic",
            "Comfortable 100% cotton hoodie for everyday casual wear", 55.0,
            "http://localhost:8080/images/hoodie-classic.jpg", clothing));
        Product p12 = productRepo.save(new Product(null, "Slim Fit Jeans",
            "Modern slim fit denim jeans — available in multiple sizes", 70.0,
            "https://images.unsplash.com/photo-1542272604-787c3835535d?w=400&h=260&fit=crop", clothing));

        // Stock
        inventoryRepo.save(new Inventory(null, 10, p1));
        inventoryRepo.save(new Inventory(null, 25, p2));
        inventoryRepo.save(new Inventory(null, 50, p3));
        inventoryRepo.save(new Inventory(null, 40, p4));
        inventoryRepo.save(new Inventory(null, 15, p5));
        inventoryRepo.save(new Inventory(null, 100, p6));
        inventoryRepo.save(new Inventory(null, 80, p7));
        inventoryRepo.save(new Inventory(null, 60, p8));
        inventoryRepo.save(new Inventory(null, 30, p9));
        inventoryRepo.save(new Inventory(null, 75, p10));
        inventoryRepo.save(new Inventory(null, 45, p11));
        inventoryRepo.save(new Inventory(null, 35, p12));

        // Test user
        Profile profile = new Profile();
        User user = new User(null, "admin@estore.com", "admin123", "Admin", "User", profile);
        userRepo.save(user);

        System.out.println(">> [SUCCESS] Database initialized with 4 categories, 12 products and Test User!");
    }
}
