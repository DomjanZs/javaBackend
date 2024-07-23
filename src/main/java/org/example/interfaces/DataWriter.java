package org.example.interfaces;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface DataWriter {
    void writeCustomerPurchaseReport(Map<String, Integer> customerPurchases, String filePath) throws IOException;
    void writeTopSpendersReport(List<Map.Entry<String, Integer>> customerPurchasesList, String filePath) throws IOException;
    void writeWebshopRevenueReport(Map<String, int[]> revenues, String filePath) throws IOException;
}
