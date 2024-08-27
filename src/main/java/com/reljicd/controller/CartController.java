package com.reljicd.controller;

import com.reljicd.exception.NotEnoughProductsInStockException;
import com.reljicd.service.ProductService;
import com.reljicd.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

/**
 * This controller manages the shopping cart operations such as viewing the cart,
 * adding or removing products from the cart, and checking out.
 */
@Controller
public class CartController {

    // Service to handle shopping cart operations
    private final ShoppingCartService cartService;

    // Service to handle product operations
    private final ProductService productService;

    /**
     * Constructor to inject dependencies via Spring's @Autowired.
     *
     * @param cartService the service responsible for managing the shopping cart
     * @param productService the service responsible for managing products
     */
    @Autowired
    public CartController(ShoppingCartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    /**
     * Displays the current shopping cart, including all products and the total price.
     *
     * @return a ModelAndView containing the cart view and the current state of the shopping cart
     */
    @GetMapping("/shoppingCart")
    public ModelAndView viewCart() {
        // Initialize the ModelAndView with the view name for the shopping cart
        ModelAndView mav = new ModelAndView("/shoppingCart");

        // Add the list of products currently in the cart to the model
        mav.addObject("products", cartService.getProductsInCart());

        // Add the total cost of the products in the cart to the model
        mav.addObject("total", cartService.getTotal().toString());

        // Return the ModelAndView to render the cart view
        return mav;
    }

    /**
     * Adds a product to the shopping cart based on the product's ID.
     *
     * @param productId the ID of the product to be added to the cart
     * @return a ModelAndView displaying the updated shopping cart
     */
    @GetMapping("/shoppingCart/addProduct/{productId}")
    public ModelAndView addProductToCart(@PathVariable("productId") Long productId) {
        // Find the product by ID and, if present, add it to the shopping cart
        productService.findById(productId).ifPresent(cartService::addProduct);

        // Return the updated shopping cart view
        return viewCart();
    }

    /**
     * Removes a product from the shopping cart based on the product's ID.
     *
     * @param productId the ID of the product to be removed from the cart
     * @return a ModelAndView displaying the updated shopping cart
     */
    @GetMapping("/shoppingCart/removeProduct/{productId}")
    public ModelAndView removeProductFromCart(@PathVariable("productId") Long productId) {
        // Find the product by ID and, if present, remove it from the shopping cart
        productService.findById(productId).ifPresent(cartService::removeProduct);

        // Return the updated shopping cart view
        return viewCart();
    }

    /**
     * Proceeds to checkout, attempting to finalize the purchase of all products in the cart.
     *
     * @return a ModelAndView displaying the updated shopping cart or an out-of-stock message if checkout fails
     */
    @GetMapping("/shoppingCart/checkout")
    public ModelAndView checkout() {
        try {
            // Attempt to checkout all products in the cart
            cartService.checkout();
        } catch (NotEnoughProductsInStockException e) {
            // If there aren't enough products in stock, display an error message
            return viewCart().addObject("outOfStockMessage", e.getMessage());
        }

        // Return the cart view after successful checkout
        return viewCart();
    }
}
