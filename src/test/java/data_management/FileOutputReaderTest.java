package data_management;

import com.alerts.AlertGenerator;
import com.data_management.DataStorage;
import com.data_management.FileOutputReader;
import com.data_management.PatientRecord;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileOutputReaderTest {
    @Test
    public void checkIfFilesAreReadAndPassedIntoDataStorage() throws IOException {
        DataStorage storage = new DataStorage();
        FileOutputReader reader = new FileOutputReader("PATIENT_DATA.txt");
        reader.startReading(storage);
        List<PatientRecord> records = storage.getRecords(1, 0, Long.MAX_VALUE);

        Assert.assertEquals(10, records.size());

        PatientRecord record1 = records.get(0);
        Assert.assertEquals(1, record1.getPatientId());
        Assert.assertEquals(98.6, record1.getMeasurementValue(), 0.001);
        Assert.assertEquals("temperature", record1.getRecordType());
        Assert.assertEquals(1652964723000L, record1.getTimestamp());

        PatientRecord record2 = records.get(1);
        Assert.assertEquals(1, record2.getPatientId());
        Assert.assertEquals(120.0, record2.getMeasurementValue(), 0.001);
        Assert.assertEquals("blood_pressure", record2.getRecordType());
        Assert.assertEquals(1652964755000L, record2.getTimestamp());

        /*PatientRecord record3 = records.get(9);
        Assert.assertEquals(1, record2.getPatientId());
        Assert.assertEquals(6.5, record2.getMeasurementValue(), 0.001);
        Assert.assertEquals("blood_sugar", record2.getRecordType());
        Assert.assertEquals(1652965011000L, record2.getTimestamp());

         */

        //this is just to check if it was properly sent to the data storage, no need to check all users
    }
}
