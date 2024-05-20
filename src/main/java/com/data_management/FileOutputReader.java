package com.data_management;

//package com.cardiogenerator.outputs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Represents a reader that reads data from a file and stores it in a DataStorage object.
 * This class implements the DataReader interface and overrides the readData method.
 * The file from which data is read is specified by the outputDir variable.
 */
public class FileOutputReader implements DataReader {

    private String outputDir;

    /**
     * Constructs a new FileOutputReader with a specified output directory.
     *
     * @param outputDir the directory from which the data file will be read
     */
    public FileOutputReader(String outputDir) {
        this.outputDir = outputDir;
    }

    /**
     * Reads data from a file and stores it in a DataStorage object.
     * The data is read line by line, and each line is split into parts based on the comma separator.
     * Each part is then parsed into the appropriate data type and added to the DataStorage object.
     * If a line does not contain exactly 4 parts, it is considered to be in an invalid format and is skipped.
     *
     * @param dataStorage the DataStorage object in which the data will be stored
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        String filePath = outputDir;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    int patientId = Integer.parseInt(parts[0]);
                    double measurementValue = Double.parseDouble(parts[1]);
                    String recordType = parts[2];
                    long timestamp = Long.parseLong(parts[3]);
                    dataStorage.addPatientData(patientId, measurementValue, recordType, timestamp);
                } else {
                    System.err.println("Invalid data format: " + line);
                }
            }
        }
    }
}
