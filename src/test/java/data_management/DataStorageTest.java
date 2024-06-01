package data_management;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import com.data_management.DataReader;
import com.data_management.FileOutputReader;
import org.junit.jupiter.api.Test;

import com.data_management.DataStorage;
import com.data_management.PatientRecord;

import java.io.IOException;
import java.util.List;

class DataStorageTest {

    @Test
    void testAddAndGetRecords() throws IOException {
        DataReader reader = new FileOutputReader("PATIENT_DATA.txt");
        DataStorage storage = new DataStorage();

        storage.addPatientData(1, 100.0, "WhiteBloodCells", 1714376789050L);
        storage.addPatientData(1, 200.0, "WhiteBloodCells", 1714376789051L);
        reader.startReading(storage);

        List<PatientRecord> records = storage.getRecords(1, 1714376789050L, 1714376789051L);
        assertEquals(2, records.size(),0.001); // Check if two records are retrieved
        assertEquals(100.0, records.get(0).getMeasurementValue(),0.001); // Validate first record
    }
}
