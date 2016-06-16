package com.agl.demo.birt.service;

import java.io.IOException;
import java.util.List;

import com.agl.demo.birt.model.Customer;

public interface IReportService {

	List<Customer> getAllCustomers() throws IOException;
}
