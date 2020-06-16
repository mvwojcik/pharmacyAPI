package pl.zzpj.pharmacy.api.symptom;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class Security {
    private final RestTemplate restTemplate;

    @Autowired
    public Security(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void jadaom() {

        String uri = "https://authservice.priaid.ch/login";
        String api_key = "";
        String username = "Jj2r4_WP_PL_AUT";
        String secretKey = "g2JDq4n8YTz57Kks9";
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "HmacMD5");
        try {
            Mac mac = Mac.getInstance("HmacMD5");
            mac.init(keySpec);
            byte[] bytes = mac.doFinal(uri.getBytes());

            String req = new String(Base64.encodeBase64(bytes));
            HttpHeaders httpHeaders = new HttpHeaders();
            System.out.println(req);
            httpHeaders.set("Authorization","Bearer "+username+":"+req);
            HttpEntity<String> entity = new HttpEntity<>("body",httpHeaders);
            try {
                ResponseEntity<ResponseEntity> responseEntityResponseEntity = restTemplate.postForEntity(uri, entity
                        , ResponseEntity.class);
                System.out.println(responseEntityResponseEntity.getBody());
                System.out.println(responseEntityResponseEntity.getStatusCode());

            }
            catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }
}
