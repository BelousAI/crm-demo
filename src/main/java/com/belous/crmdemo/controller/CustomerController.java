package com.belous.crmdemo.controller;

import com.belous.crmdemo.entity.Customer;
import com.belous.crmdemo.service.CustomerService;
import com.belous.crmdemo.util.SortUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    // need to inject the customer service
    @Autowired
    private CustomerService customerService;

    @GetMapping("/list")
    public String listCustomers(Model theModel, @RequestParam(required = false) String sort) {

        // get customers from the service
        List<Customer> customers = null;

        // check for sort field
        if (sort != null) {
            int theSortField = Integer.parseInt(sort);
            customers = customerService.getCustomers(theSortField);
        }
        else {
            // no sort field provided ... default to sorting by last name
            customers = customerService.getCustomers(SortUtils.LAST_NAME);
        }

        // add the customers to the model of SpringMVC
        theModel.addAttribute("customers", customers);

        return "list-customers";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {

        // create model attribute to bind form data
        Customer theCustomer = new Customer();

        // set customer as a model attribute
        theModel.addAttribute("customer", theCustomer);

        return "customer-form";
    }

    @PostMapping("/saveCustomer")
    public String saveCustomer(@ModelAttribute("customer") Customer theCustomer) {

        // save the customer using our service
        customerService.saveCustomer(theCustomer);

        return "redirect:/customer/list";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("customerId") int theId, Model theModel) {

        // get the customer by id from our service
        Customer theCustomer = customerService.getCustomer(theId);

        // set customer as a model attribute to pre-populate the form
        theModel.addAttribute("customer", theCustomer);

        // send over to our form
        return "customer-form";
    }

    @GetMapping("/deleteCustomer")
    public String deleteCustomer(@RequestParam("customerId") int theId) {

        // delete the customer by id from our service
        customerService.deleteCustomer(theId);

        return "redirect:/customer/list";
    }

    @GetMapping("/search")
    public String searchCustomers(@RequestParam("theSearchName") String theSearchName, Model theModel) {

        // search customers from the service
        List<Customer> theCustomers = customerService.searchCustomers(theSearchName);

        // set customers as a model attribute
        theModel.addAttribute("customers", theCustomers);

        return "list-customers";
    }
}
