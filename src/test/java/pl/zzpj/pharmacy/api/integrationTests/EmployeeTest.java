package pl.zzpj.pharmacy.api.integrationTests;

import com.intuit.karate.junit5.Karate;

public class EmployeeTest {

    @Karate.Test
    Karate employeeTest(){
        return Karate.run("./feature/employee").relativeTo(getClass());
    }
}
