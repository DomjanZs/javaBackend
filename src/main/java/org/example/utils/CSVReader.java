package org.example.utils;

import org.example.interfaces.DataReader;
import org.example.services.LoggerService;
import org.example.model.Customer;
import org.example.model.Payments;
import org.example.repositories.DataRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CSVReader implements DataReader {
    private final DataRepository repository;

    public CSVReader(DataRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<?> readData(String filePath) {
        repository.setFilePath(filePath);
        try {
            return parseData(repository.readAllLines());
        } catch (IOException e) {
            LoggerService.logSevere("Failed to read data from " + filePath + ": " + e.getMessage());
            return List.of();
        }
    }

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    private List<Object> parseData(List<String> lines) {
        if (lines.isEmpty()) {
            LoggerService.logSevere("CSV file is empty");
            return List.of();
        }

        String delimiter = determineDelimiter(lines.get(0));
        Function<String[], ?> mapper = determineMapper(lines.get(0).split(delimiter).length);

        if (mapper == null) {
            LoggerService.logSevere("CSV file format is unrecognized");
            return List.of();
        }

        return lines.stream()
                .map(line -> line.split(delimiter))
                .map(data -> {
                    try {
                        return mapper.apply(data);
                    } catch (Exception e) {
                        LoggerService.logSevere("Failed to parse line: " + String.join(delimiter, data) + "; Error: " + e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private String determineDelimiter(String line) {
        return line.contains(";") ? ";" : ",";
    }

    private Function<String[], ?> determineMapper(int length) {
        if (length == 4) {
            return data -> new Customer(
                    data[0].trim(),
                    data[1].trim(),
                    data[2].trim(),
                    data[3].trim()
            );
        } else if (length == 7) {
            return data -> {
                validatePaymentData(data);
                return new Payments(
                        data[0].trim(),
                        data[1].trim(),
                        data[2].trim(),
                        Integer.parseInt(data[3].trim()),
                        data[4].trim(),
                        data[5].trim(),
                        LocalDate.parse(data[6].trim(), DATE_FORMATTER)
                );
            };
        } else {
            return null;
        }
    }

    private void validatePaymentData(String[] data) throws IllegalArgumentException {
        String paymentMethod = data[2].trim();
        String bankAccountNumber = data[4].trim();
        String cardNumber = data[5].trim();

        if ((paymentMethod.equals("card") && cardNumber.isEmpty()) ||
                (paymentMethod.equals("transfer") && bankAccountNumber.isEmpty())) {
            throw new IllegalArgumentException("Required payment information is missing for payment method: " + paymentMethod);
        }
    }
}
