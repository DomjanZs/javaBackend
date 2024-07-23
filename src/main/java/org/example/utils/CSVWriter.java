package org.example.utils;

import org.example.interfaces.DataWriter;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CSVWriter implements DataWriter {

    @Override
    public void writeCustomerPurchaseReport(Map<String, Integer> customerPurchases, String filePath) throws IOException {
        writeReport(new ArrayList<>(customerPurchases.entrySet()), filePath, Integer.MAX_VALUE);
    }

    @Override
    public void writeTopSpendersReport(List<Map.Entry<String, Integer>> customerPurchasesList, String filePath) throws IOException {
        writeReport(customerPurchasesList, filePath, 2);
    }

    @Override
    public void writeWebshopRevenueReport(Map<String, int[]> revenues, String filePath) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("WEBSHOP,CardPayments,TransferPayments");
            revenues.forEach((key, values) -> writer.printf("%s,%d,%d\n", key, values[0], values[1]));
        }
    }

    private void writeReport(List<Map.Entry<String, Integer>> entries, String filePath, int maxEntries) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("NAME,ADDRESS,Vásárlás összesen");
            entries.stream()
                    .limit(maxEntries)
                    .forEach(entry -> {
                        String[] parts = entry.getKey().split(", ", 2);
                        writer.printf("%s,%s,%d\n", parts[0], parts[1], entry.getValue());
                    });
        }
    }
}
