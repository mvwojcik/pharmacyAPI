package pl.zzpj.pharmacy.api.integrationTests;

import com.intuit.karate.junit5.Karate;

public class OrderTest {

    @Karate.Test
    Karate run(){
        return Karate.run("./feature/orders").relativeTo(getClass());
    }
}
