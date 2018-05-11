package spp.lab.service.documentsGeneration;

import spp.lab.models.Payment;
import spp.lab.models.User;
import spp.lab.service.documentsGeneration.utils.CSVUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class CsvDocumentsGenerationService extends BaseDocumentsGenerationService {

    private final String repositoryPath = "\\docs\\csv\\";

    private final CSVUtils utils = new CSVUtils();

    private String generateCsvName(String pathToFolder, String caption) {
        return this.generateFilename(pathToFolder, this.repositoryPath, caption) + ".csv";
    }

    public String generatePaymentsCsv(String pathToFolder, Iterable<Payment> payments) throws IOException {

        String fileName = generateCsvName(pathToFolder, "Payments");

        FileWriter writer = new FileWriter(fileName);

        CSVUtils.writeLine(writer, Arrays.asList("user_id", "subscription_id", "price", "created_at", "state"));

        payments.forEach(payment -> {
            try {
                CSVUtils.writeLine(writer, Arrays.asList(
                        payment.getUser().getId().toString(),
                        payment.getSubscription().getId().toString(),
                        payment.getPrice().toString(),
                        payment.getCreated_at().toString(),
                        payment.getState().toString()
                ));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        writer.flush();
        writer.close();


        return fileName;
    }

    public String generatePaymentCsv(String pathToFolder, Payment payment) throws IOException {

        String fileName = generateCsvName(pathToFolder, "Payments");

        FileWriter writer = new FileWriter(fileName);

        CSVUtils.writeLine(writer, Arrays.asList("user_id", "subscription_id", "price", "created_at", "state"));

        CSVUtils.writeLine(writer, Arrays.asList(
                payment.getUser().getId().toString(),
                payment.getSubscription().getId().toString(),
                payment.getPrice().toString(),
                payment.getCreated_at().toString(),
                payment.getState().toString()
        ));

        return fileName;
    }


    public String generateUsersCsv(String pathToFolder, Iterable<User> users) throws IOException {

        String fileName = generateCsvName(pathToFolder, "Users");

        FileWriter writer = new FileWriter(fileName);

        CSVUtils.writeLine(writer, Arrays.asList("username", "login", "password", "apiKey", "role", "trainer_id"));

        users.forEach(user -> {
            try {
                CSVUtils.writeLine(writer, Arrays.asList(
                        user.getUsername(),
                        user.getLogin(),
                        user.getPassword(),
                        user.getApiKey(),
                        user.getRole().toString(),
                        user.getTrainer().getId().toString()
                ));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        writer.flush();
        writer.close();


        return fileName;
    }
}
