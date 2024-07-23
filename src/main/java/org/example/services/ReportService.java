package org.example.services;

import org.example.interfaces.DataWriter;
import org.example.model.Customer;
import org.example.model.Payments;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ReportService {
    private final DataWriter dataWriter;

    public ReportService(DataWriter dataWriter) {
        this.dataWriter = dataWriter;
    }

    public void generateCustomerPurchaseReport(List<?> genericCustomers, List<?> genericPayments, String filePath) throws ClassCastException {
        List<Customer> customers = (List<Customer>) genericCustomers;
        List<Payments> payments = (List<Payments>) genericPayments;

        Map<String, Integer> customerPurchases = calculateCustomerPurchases(customers, payments);

        try {
            this.dataWriter.writeCustomerPurchaseReport(customerPurchases, filePath);
        } catch (IOException e) {
            LoggerService.logSevere("Error writing to CSV file: " + e.getMessage());
        }
    }

    public void generateTopSpendersReport(List<?> genericCustomers, List<?> genericPayments, String filePath) {
        List<Customer> customers = (List<Customer>) (List<?>) genericCustomers;
        List<Payments> payments = (List<Payments>) (List<?>) genericPayments;

        Map<String, Integer> customerPurchases = calculateCustomerPurchases(customers, payments);

        List<Map.Entry<String, Integer>> sortedEntries = customerPurchases.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toList());

        try {
            this.dataWriter.writeTopSpendersReport(sortedEntries, filePath);
        } catch (IOException e) {
            LoggerService.logSevere("Error writing the output file: " + e.getMessage());
        }
    }

    public void generateWebshopRevenueReport(List<?> genericPayments, String filePath) {
        List<Payments> payments = (List<Payments>) (List<?>) genericPayments;

        Map<String, int[]> revenues = payments.stream()
                .collect(Collectors.toMap(
                        Payments::getWebShopId,
                        payment -> new int[] {
                                "card".equals(payment.getPaymentMethod()) ? payment.getAmount() : 0,
                                "transfer".equals(payment.getPaymentMethod()) ? payment.getAmount() : 0
                        },
                        (a, b) -> new int[] {a[0] + b[0], a[1] + b[1]}
                ));

        try {
            this.dataWriter.writeWebshopRevenueReport(revenues, filePath);
        } catch (IOException e) {
            LoggerService.logSevere("Error writing to CSV file: " + e.getMessage());
        }
    }

    private Map<String, Integer> calculateCustomerPurchases(List<Customer> customers, List<Payments> payments) {
        return payments.stream()
                .flatMap(payment -> customers.stream()
                        .filter(customer -> customer.getCustomerId().equals(payment.getCustomerId()) && customer.getWebShopId().equals(payment.getWebShopId()))
                        .map(customer -> new AbstractMap.SimpleEntry<>(
                                customer.getName() + ", " + customer.getAddress(),
                                payment.getAmount()
                        ))
                )
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        Integer::sum
                ));
    }
}
