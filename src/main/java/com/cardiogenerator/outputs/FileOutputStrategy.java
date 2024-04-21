// Removed underscore and changed file name
package com.cardiogenerator.outputs;

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
    //Changed variable name to camelCase
    private String baseDirectory;
    /**
     * A concurrent hash map to store file paths corresponding to labels.
     */

    //Changed variable name to comply to criteria for constants
    public final ConcurrentHashMap<String, String> FINAL_MAP = new ConcurrentHashMap<>();
    /**
     * Constructs a FileOutputStrategy object with the specified base directory.
     *
     * @param baseDirectory the base directory where output files will be stored
     */
    public FileOutputStrategy(String baseDirectory) {

        this.baseDirectory = baseDirectory;
    }


    /**
     * Outputs data provided into the console
     *
     * @param patientId = the Id of the patient
     * @param timeStamp = the timestamp of the data
     * @param label = the label of the data
     * @param data = the data that will be shown
     */
    @Override
    //Changed timestamp to timeStamp, following the rules for lowerCamelCase
    public void output(int patientId, long timeStamp, String label, String data) {
        try {
            // Create the directory
            Files.createDirectories(Paths.get(baseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }
        // Set the FilePath variable
        //Changed variable name to camelCase
        String filePath = FINAL_MAP.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString());
        
        // Write the data to the file
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))){
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timeStamp, label, data);
        //Changed exception to reduce vagueness
        } catch (IOException e) {
            System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
        }
    }
}