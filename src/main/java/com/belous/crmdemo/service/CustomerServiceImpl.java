package com.belous.crmdemo.service;

import com.belous.crmdemo.dao.CustomerDao;
import com.belous.crmdemo.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDAO;

    @Override
    @Transactional("transactionManager")
    public List<Customer> getCustomers(int theSortField) {
        return customerDAO.getCustomers(theSortField);
    }

    @Override
    @Transactional("transactionManager")
    public void saveCustomer(Customer theCustomer) {
        customerDAO.saveCustomer(theCustomer);
    }

    @Override
    @Transactional("transactionManager")
    public Customer getCustomer(int theId) {
        return customerDAO.getCustomer(theId);
    }

    @Override
    @Transactional("transactionManager")
    public void deleteCustomer(int theId) {
        customerDAO.deleteCustomer(theId);
    }

    @Override
    @Transactional("transactionManager")
    public List<Customer> searchCustomers(String theSearchName) {
        return customerDAO.searchCustomers(theSearchName);
    }
}
