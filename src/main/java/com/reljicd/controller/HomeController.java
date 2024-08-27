package com.reljicd.controller;

import com.reljicd.model.Product;
import com.reljicd.service.ProductService;
import com.reljicd.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

/*@Controller
public class HomeController {

    private static final int INITIAL_PAGE = 0;

    private final ProductService productService;

    @Autowired
    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/home")
    public ModelAndView home(@RequestParam("page") Optional<Integer> page) {

        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<Product> products = productService.findAllProductsPageable(new PageRequest(evalPage, 5));
        Pager pager = new Pager(products);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("products", products);
        modelAndView.addObject("pager", pager);
        modelAndView.setViewName("/home");
        return modelAndView;
    }

}*/



@Controller
public class HomeController {

    // Default page number to be used if none is provided
    private static final int DEFAULT_PAGE_NUMBER = 0;

    // Service to manage products
    private final ProductService productService;

    // Constructor for dependency injection of ProductService
    @Autowired
    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    // Controller method to display the home page with paginated products
    @GetMapping("/home")
    public ModelAndView displayHomePage(@RequestParam("page") Optional<Integer> page) {

        // Determine the current page number. If the page number is not provided or is invalid,
        // default to the first page. Adjust for zero-based indexing.
        int currentPage = page.filter(p -> p > 0).map(p -> p - 1).orElse(DEFAULT_PAGE_NUMBER);

        // Fetch a pageable list of products with the specified page size
        Page<Product> productPage = productService.findAllProductsPageable(new PageRequest(currentPage, 5));

        // Create a helper object for handling pagination (assumes PaginationHelper is implemented)
        Pager pager = new Pager(productPage);

        // Create the ModelAndView object, add products and pagination details, and set the view name
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("products", productPage);
        modelAndView.addObject("pager", pager);
        modelAndView.setViewName("/home");

        // Return the ModelAndView object to render the home page
        return modelAndView;
    }
}

