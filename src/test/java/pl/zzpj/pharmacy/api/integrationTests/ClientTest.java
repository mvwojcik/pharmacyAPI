package pl.zzpj.pharmacy.api.integrationTests;

import com.intuit.karate.junit5.Karate;

public class ClientTest {

    @Karate.Test
    Karate clientTest(){
        return Karate.run("./feature/client").relativeTo(getClass());
    }
}
