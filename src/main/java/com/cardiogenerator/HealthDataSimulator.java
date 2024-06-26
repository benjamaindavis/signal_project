package com.cardiogenerator;

import java.net.URISyntaxException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.alerts.AlertGenerator;
import com.cardiogenerator.generators.BloodPressureDataGenerator;
import com.cardiogenerator.generators.BloodSaturationDataGenerator;
import com.cardiogenerator.generators.BloodLevelsDataGenerator;
import com.cardiogenerator.generators.ECGDataGenerator;
import com.cardiogenerator.outputs.ConsoleOutputStrategy;
import com.cardiogenerator.outputs.FileOutputStrategy;
import com.cardiogenerator.outputs.OutputStrategy;
import com.cardiogenerator.outputs.TcpOutputStrategy;
import com.cardiogenerator.outputs.WebSocketOutputStrategy;
import com.data_management.ContinuousDataReader;
import com.data_management.DataStorage;
import com.data_management.Patient;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * This class HealthDataSimulator is used to simulated health data for more than one patient
 * and this data is shown through multiple types of outputs, such as the console, file, WebSocket
 * or even a TCP socket.
 */
public class HealthDataSimulator {
    static DataStorage storage = new DataStorage();//fixed an error by adding the static keyword
    static Patient patient;

    private static int patientCount = 50; // Default number of patients
    private static ScheduledExecutorService scheduler;
    private static OutputStrategy outputStrategy = new WebSocketOutputStrategy(8080); // Default output strategy
    private static final Random random = new Random();
    private static HealthDataSimulator singleton = null;//created a singleton instance
    /**
     * Main Method to start HealthData Simulation
     *
     * @throws IOException throws an IOException
     */

    public static void main(String[] args) throws IOException, URISyntaxException {

        parseArguments(args);

        scheduler = Executors.newScheduledThreadPool(patientCount * 4);

        List<Integer> patientIds = initializePatientIds(patientCount);
        Collections.shuffle(patientIds); // Randomize the order of patient IDs

        scheduleTasksForPatients(patientIds);
        ContinuousDataReader reader = new ContinuousDataReader("ws://localhost:8080");
        reader.startReading(storage);
    }
    private HealthDataSimulator(){// created a private constructor for the singleton pattern
        this.patientCount = 50;
        this.outputStrategy = new ConsoleOutputStrategy();
    }

    public static HealthDataSimulator getInstance(){// created the getInstance method for the singleton pattern
        if (singleton==null){
            singleton = new HealthDataSimulator();
        }
        return singleton;
    }
    /**
     * Parses command line comments
     *
     * @throws IOException is throwing an IOException
     */
    private static void parseArguments(String[] args) throws IOException {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-h":
                    printHelp();
                    System.exit(0);
                    break;
                case "--patient-count":
                    if (i + 1 < args.length) {
                        try {
                            patientCount = Integer.parseInt(args[++i]);
                        } catch (NumberFormatException e) {
                            System.err
                                    .println("Error: Invalid number of patients. Using default value: " + patientCount);
                        }
                    }
                    break;
                case "--output":
                    if (i + 1 < args.length) {
                        String outputArg = args[++i];
                        if (outputArg.equals("console")) {
                            outputStrategy = new ConsoleOutputStrategy();
                        } else if (outputArg.startsWith("file:")) {
                            String baseDirectory = outputArg.substring(5);
                            Path outputPath = Paths.get(baseDirectory);
                            if (!Files.exists(outputPath)) {
                                Files.createDirectories(outputPath);
                            }
                            outputStrategy = new FileOutputStrategy(baseDirectory);
                        } else if (outputArg.startsWith("websocket:")) {
                            try {
                                int port = Integer.parseInt(outputArg.substring(10));
                                // Initialize your WebSocket output strategy here
                                outputStrategy = new WebSocketOutputStrategy(port);
                                System.out.println("WebSocket output will be on port: " + port);
                            } catch (NumberFormatException e) {
                                System.err.println(
                                        "Invalid port for WebSocket output. Please specify a valid port number.");
                            }
                        } else if (outputArg.startsWith("tcp:")) {
                            try {
                                int port = Integer.parseInt(outputArg.substring(4));
                                // Initialize your TCP socket output strategy here
                                outputStrategy = new TcpOutputStrategy(port);
                                System.out.println("TCP socket output will be on port: " + port);
                            } catch (NumberFormatException e) {
                                System.err.println("Invalid port for TCP output. Please specify a valid port number.");
                            }
                        } else {
                            System.err.println("Unknown output type. Using default (console).");
                        }
                    }
                    break;
                default:
                    System.err.println("Unknown option '" + args[i] + "'");
                    printHelp();
                    System.exit(1);
            }
        }
    }
    /**
     * Prints help through all the different outputs
     */
    private static void printHelp() {
        System.out.println("Usage: java HealthDataSimulator [options]");
        System.out.println("Options:");
        System.out.println("  -h                       Show help and exit.");
        System.out.println(
                "  --patient-count <count>  Specify the number of patients to simulate data for (default: 50).");
        System.out.println("  --output <type>          Define the output method. Options are:");
        System.out.println("                             'console' for console output,");
        System.out.println("                             'file:<directory>' for file output,");
        System.out.println("                             'websocket:<port>' for WebSocket output,");
        System.out.println("                             'tcp:<port>' for TCP socket output.");
        System.out.println("Example:");
        System.out.println("  java HealthDataSimulator --patient-count 100 --output websocket:8080");
        System.out.println(
                "  This command simulates data for 100 patients and sends the output to WebSocket clients connected to port 8080.");
    }
//
    /**
     * Initializes the Patient IDs
     *
     * @param patientCount the ID of the patient
     * @return the method returns an Integer list of the patient IDs
     */
    private static List<Integer> initializePatientIds(int patientCount) {
        List<Integer> patientIds = new ArrayList<>();
        for (int i = 1; i <= patientCount; i++) {
            patientIds.add(i);
        }
        return patientIds;
    }

    /**
     * This class outputs a schedule for the Patients for their activities, and takes in
     * a list of the patient's Ids to correctly inform them of their tasks
     *
     * @param patientIds this is the Integer list of PatientIds to identify patients
     */
    private static void scheduleTasksForPatients(List<Integer> patientIds) {
        ECGDataGenerator ecgDataGenerator = new ECGDataGenerator(patientCount);
        BloodSaturationDataGenerator bloodSaturationDataGenerator = new BloodSaturationDataGenerator(patientCount);
        BloodPressureDataGenerator bloodPressureDataGenerator = new BloodPressureDataGenerator(patientCount);
        BloodLevelsDataGenerator bloodLevelsDataGenerator = new BloodLevelsDataGenerator(patientCount);
        AlertGenerator alertGenerator = new com.alerts.AlertGenerator(storage);

        for (int patientId : patientIds) {
            scheduleTask(() -> ecgDataGenerator.generate(patientId, outputStrategy), 1, TimeUnit.SECONDS);
            scheduleTask(() -> bloodSaturationDataGenerator.generate(patientId, outputStrategy), 1, TimeUnit.SECONDS);
            scheduleTask(() -> bloodPressureDataGenerator.generate(patientId, outputStrategy), 1, TimeUnit.MINUTES);
            scheduleTask(() -> bloodLevelsDataGenerator.generate(patientId, outputStrategy), 2, TimeUnit.MINUTES);
            scheduleTask(() -> alertGenerator.evaluateData(storage.getPatient(patientId)), 20, TimeUnit.SECONDS);
        }
    }
    /**
     * This class schedules each patient with a task
     *
     * @param task this specifies the task
     * @param period this is the duration of the task
     * @param timeUnit this is what is used to measure the time
     */
    private static void scheduleTask(Runnable task, long period, TimeUnit timeUnit) {
        scheduler.scheduleAtFixedRate(task, random.nextInt(5), period, timeUnit);
    }
}
