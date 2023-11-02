package com.dion.exchangerateapi.Controller;

import com.dion.exchangerateapi.models.po.CurrencyPO;
import com.dion.exchangerateapi.service.CurrencyService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
@RequestMapping("api/v1.0/coindest")
public class CoinDestController {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value(value = "${coindesk.url}")
    private String coinDestUrl;

    final
    CurrencyService currencyService;

    public CoinDestController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping(value = "/native-api", produces = "application/json")
    public ResponseEntity<String> nativeCoinDestApi() {
        String jsonString = restTemplate.getForObject(coinDestUrl, String.class);

        return ResponseEntity.ok(jsonString);
    }

    @GetMapping(value = "/transformed-api", produces = "application/json")
    public ResponseEntity<String> newCoinDestApi() {
        String jsonString = restTemplate.getForObject(coinDestUrl, String.class);
        String convertJsonString = convertCoinData(jsonString);

        return ResponseEntity.ok(convertJsonString);
    }

    private String convertCoinData(String jsonData) {
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONObject bpi = jsonObject.getJSONObject("bpi");
        String updateTime = jsonObject.getJSONObject("time").getString("updatedISO");

        //format updateTime
        String formattedTime = formatAndConvertTime(updateTime);

        JSONObject newJson = new JSONObject();
        newJson.put("updatedTimeTaipei", formattedTime);

        for (String currency : bpi.keySet()) {
            JSONObject currencyData = bpi.getJSONObject(currency);
            BigDecimal rate = currencyData.getBigDecimal("rate_float");
            Optional<CurrencyPO> currencyPO = currencyService.getActiveCurrencyByCode(currency);

            JSONObject currencyObject = new JSONObject();
            currencyObject.put("rate", rate);
            currencyObject.put("chName", getChName(currencyPO));

            newJson.put(currency, currencyObject);
        }

        return newJson.toString();
    }

    private String formatAndConvertTime(String updateTime) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(updateTime, inputFormatter);

        ZoneId taipeiZone = ZoneId.of("Asia/Taipei");

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").withZone(taipeiZone);
        return zonedDateTime.withZoneSameInstant(taipeiZone).format(outputFormatter);
    }

    private String getChName(Optional<CurrencyPO> currencyPO) {
        return currencyPO.map(CurrencyPO::getChName).orElse("");
    }
}
