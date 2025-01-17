# Java Backend Task

This Java application manages customers and transactions for two online webshops, reading and processing data from CSV files to generate summarized reports.

## Features

- **Data Import:** The application reads from `data/customer.csv` and `data/payments.csv`, storing data in memory.
- **Error Handling:** Errors in data are logged to `src/logs/application.log` and faulty records are skipped.
- **Reporting:**
  - **Customer Report:** Generates a report (`reports/report01.csv`) listing total spending per customer.
  - **Top Spenders:** Identifies the two highest spenders and saves their details to `reports/top.csv`.
  - **Webshop Revenue Report:** Summarizes card and transfer payments per webshop in `reports/report02.csv`.

## Setup and Execution

1. Ensure Java 17 SDK is installed on your system.
2. Ensure Maven is installed on your system.
3. Compile and run the application using the command line:
   - Using Maven, navigate to the app directory(`/javaBackend`) and execute:
     ```bash
     mvn compile
     mvn package
     java -jar target/otp_task-1.0-SNAPSHOT-jar-with-dependencies.jar
     ```
4. Or use IDE
5. Ensure Java 17 SDK is installed on your system.
2. Ensure Maven is installed on your system.
6. Reload the Maven projects by navigating to (IntelliJ): `View -> Tool Windows -> Maven -> Reload All Maven Projects / Download Sources`.
7. Run 'Main.java'

## Data Files

- `customer.csv`: Contains customer data (Webshop ID, Customer ID, Name, Address).
- `payments.csv`: Contains payment data (Webshop ID, Customer ID, Payment Type, Amount, Account/Card Number, Payment Date).

The application processes these files to generate financial reports tailored for webshop analysis.

