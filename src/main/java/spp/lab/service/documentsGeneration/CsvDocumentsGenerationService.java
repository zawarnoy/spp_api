package spp.lab.service.documentsGeneration;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.core.io.ClassPathResource;
import spp.lab.models.Payment;

import java.io.File;
import java.io.IOException;

public class CsvDocumentsGenerationService extends BaseDocumentsGenerationService {

    private final String repositoryPath = "\\docs\\csv\\";

    private String generateCsvName(String pathToFolder, String caption) {
        return this.generateFilename(pathToFolder, this.repositoryPath, caption) + ".csv";
    }

    public String generatePaymentCsv(String pathToFolder, Payment payment) throws IOException {
        CsvMapper mapper = new CsvMapper();

        File file = null;

        CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
        file = new ClassPathResource(pathToFolder + this.repositoryPath + "123.csv").getFile();

        mapper.writer().with(bootstrapSchema).writeValue(file, payment);
        return pathToFolder + this.repositoryPath + "123.csv";

    }

}
