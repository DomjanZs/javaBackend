package org.example.config;

import java.io.File;

public class PathConfig {
    public static final String BASE_PATH = new File(".").getAbsolutePath();
    public static final String LOGS_PATH = BASE_PATH + "/src/logs";
    public static final String DATA_PATH = BASE_PATH + "/data";
    public static final String REPORTS_PATH = BASE_PATH + "/reports";

    public static final String APPLICATION_LOG_PATH = LOGS_PATH + "/application.log";
    public static final String CUSTOMER_FILE_PATH = "data/customer.csv";
    public static final String PAYMENTS_FILE_PATH = "data/payments.csv";
    public static final String CUSTOMER_PURCHASE_REPORT = "reports/report01.csv";
    public static final String TOP_SPENDERS_REPORT_PATH = "reports/top.csv";
    public static final String WEB_SHOP_REVENUE_REPORT_PATH = "reports/report02.csv";
}
