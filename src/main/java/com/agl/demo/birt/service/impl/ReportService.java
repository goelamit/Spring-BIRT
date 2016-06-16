package com.agl.demo.birt.service.impl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.agl.demo.birt.model.Customer;
import com.agl.demo.birt.service.IReportService;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;

@Service
public class ReportService implements IReportService {

	@Override
	public List<Customer> getAllCustomers() throws IOException {
		
		ColumnPositionMappingStrategy<Customer> start = new ColumnPositionMappingStrategy<Customer>();
		start.setType(Customer.class);
		String[] columns = new String[] { "customerNumber", "customerName", "contactLastName","contactFirstName","phone","addressLine1","addressLine2","city","state","postalCode","country","salesRepEmployeeNumber","creditLimit" };
		start.setColumnMapping(columns);
		CsvToBean<Customer> csv = new CsvToBean<Customer>();
		CSVReader csvRdr = new CSVReader(new FileReader("D:/Customers.csv"),',');
		csvRdr.readNext();
		List<Customer> list = csv.parse(start,csvRdr);
		return list;
	}

}
