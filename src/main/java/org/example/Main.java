package org.example;

import org.example.config.AppConfig;
import org.example.config.PathConfig;
import org.example.interfaces.DataReader;
import org.example.interfaces.DataWriter;
import org.example.repositories.DataRepository;
import org.example.utils.CSVReader;
import org.example.services.ReportService;
import org.example.utils.CSVWriter;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        AppConfig.initializeResources();

        DataReader dataReader = new CSVReader(new DataRepository());
        DataWriter csvWriter = new CSVWriter();
        ReportService reportService = new ReportService(csvWriter);

        List<?> customers = dataReader.readData(PathConfig.CUSTOMER_FILE_PATH);
        List<?> payments = dataReader.readData(PathConfig.PAYMENTS_FILE_PATH);

        reportService.generateCustomerPurchaseReport(customers, payments, PathConfig.CUSTOMER_PURCHASE_REPORT);
        reportService.generateTopSpendersReport(customers, payments, PathConfig.TOP_SPENDERS_REPORT_PATH);
        reportService.generateWebshopRevenueReport(payments, PathConfig.WEB_SHOP_REVENUE_REPORT_PATH);
    }
}