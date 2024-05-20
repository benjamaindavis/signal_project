package data_management;

import com.alerts.AlertGenerator;
import com.data_management.DataStorage;
import com.data_management.PatientRecord;
import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;

public class AlertGeneratorTest {

    @Test
    public void consistentIncreaseOrDecreaseAcrossThreeConsecutiveReadings(){
        var mock = new AlertGenerator(new DataStorage());
        List<PatientRecord> readings = Arrays.asList(
                new PatientRecord(100, 120, "SystolicPressure",System.currentTimeMillis() - 20000),
                new PatientRecord(100, 130, "SystolicPressure",System.currentTimeMillis() - 10000),
                new PatientRecord(100, 140, "SystolicPressure",  System.currentTimeMillis())
        );
        Assert.assertTrue(mock.validTrendAlertBp(readings));
    }
    @Test
    public void checkIfSystolicAndDiastolicReadingsGoAbove180Below90(){
        var mock = new AlertGenerator(new DataStorage());
        List<PatientRecord> readings = Arrays.asList(
                new PatientRecord(100, 200, "SystolicPressure",System.currentTimeMillis()),
                new PatientRecord(100, 70, "SystolicPressure",System.currentTimeMillis()),
                new PatientRecord(100, 140, "DiastolicPressure",System.currentTimeMillis()),
                new PatientRecord(100, 50, "DiastolicPressure",System.currentTimeMillis())
        );

        Assert.assertFalse(mock.validBpThreshold(readings.get(0), readings.get(1)));
        Assert.assertFalse(mock.validBpThreshold(readings.get(2), readings.get(3)));
    }
    @Test
    public void checkIfBloodOxygenSaturationIsBelow90(){
        var mock = new AlertGenerator(new DataStorage());
        List<PatientRecord> readings = Arrays.asList(
                new PatientRecord(100, 98, "BloodOxygenSaturation",System.currentTimeMillis()),
                new PatientRecord(100, 85, "BloodOxygenSaturation",System.currentTimeMillis())
        );
        Assert.assertTrue(mock.validBloodOxygenSat(readings.get(0)));
        Assert.assertFalse(mock.validBloodOxygenSat(readings.get(1)));
    }
    @Test
    public void checkIfBloodOxygenLevelDropsFivePercentInATenMinuteInterval(){
        var mock = new AlertGenerator(new DataStorage());
        List<PatientRecord> readings = Arrays.asList(
                new PatientRecord(100, 98, "BloodOxygenSaturation",System.currentTimeMillis() - 600000),
                new PatientRecord(100, 85, "BloodOxygenSaturation",System.currentTimeMillis())
        );
        Assert.assertTrue(mock.validBloodOxygenSat(readings.get(0)));
        Assert.assertFalse(mock.validBloodOxygenSat(readings.get(1)));
    }
    @Test
    public void checkIfHypotensiveHypoxemiaWhenSystolicIsBelow90AndBloodOxygenSaturationFallsBelow92(){
        var mock = new AlertGenerator(new DataStorage());
        List<PatientRecord> readings = Arrays.asList(
                new PatientRecord(100, 85, "SystolicPressure",System.currentTimeMillis()),
                new PatientRecord(100, 91, "BloodOxygenSaturation",System.currentTimeMillis()),
                new PatientRecord(100, 95, "SystolicPressure",System.currentTimeMillis())
        );
        Assert.assertTrue(mock.hypotensiveHypoxemia(readings.get(0), readings.get(1)));
        Assert.assertFalse(mock.hypotensiveHypoxemia(readings.get(0), readings.get(2)));
    }
    @Test
    public void checkIfEcgIsAbnormal(){
        var mock = new AlertGenerator(new DataStorage());
        List<PatientRecord> readings = Arrays.asList(
                new PatientRecord(100, 85, "ECG",System.currentTimeMillis()),
                new PatientRecord(100, 91, "ECG",System.currentTimeMillis()),
                new PatientRecord(100, 95, "ECG",System.currentTimeMillis())
        );
        Assert.assertTrue(mock.ecgAlert(readings, 3, 5));
    }
}
