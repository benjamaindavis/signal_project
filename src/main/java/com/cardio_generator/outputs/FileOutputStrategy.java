package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

public class FileOutputStrategy implements OutputStrategy {
    /**
     * The base directory where output files will be stored.
     */
    private String BaseDirectory;

    /**
     * A concurrent hash map to store file paths corresponding to labels.
     */
    public final ConcurrentHashMap<String, String> file_map = new ConcurrentHashMap<>();

    /**
     * Constructs a FileOutputStrategy object with the specified base directory.
     *
     * @param baseDirectory the base directory where output files will be stored
     */
    public FileOutputStrategy(String baseDirectory) {

        this.BaseDirectory = baseDirectory;
    }
    /**
     * Outputs data provided into the console
     *
     * @param patientId = the Id of the patient
     * @param timestamp = the timestamp of the data
     * @param label = the label of the data
     * @param data = the data that will be shown
     */

    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        try {
            // Create the directory
            Files.createDirectories(Paths.get(BaseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }
        // Set the FilePath variable
        String FilePath = file_map.computeIfAbsent(label, k -> Paths.get(BaseDirectory, label + ".txt").toString());

        // Write the data to the file
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(FilePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
        } catch (Exception e) {
            System.err.println("Error writing to file " + FilePath + ": " + e.getMessage());
        }
    }
}