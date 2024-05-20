package data_management;

import org.junit.Assert;
import org.junit.Test;
import com.data_management.Patient;

public class PatientTest {
    @Test
    public void checkIfgetPatientIdStringReturnsAStringAndIfgetPatientIdIntReturnsAnInt(){
        Patient patient = new Patient(100);
        Assert.assertEquals("100", patient.getPatientIdString());
        Assert.assertEquals(100, patient.getPatientIdInt());
    }
}
