package com.dion.exchangerateapi.Controller;

import com.dion.exchangerateapi.models.dto.CurrencyUpdateRequestDTO;
import com.dion.exchangerateapi.models.po.CurrencyPO;
import com.dion.exchangerateapi.service.CurrencyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebMvcTest(CurrencyController.class)
public class CurrencyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CurrencyService currencyService;

    @Test
    public void getCurrencyByCodeTest() throws Exception {
        CurrencyPO currency = CurrencyPO.builder()
                .code("USD")
                .chName("美元")
                .rate(new BigDecimal(34459.8798))
                .description("United States Dollar")
                .active(true)
                .build();
        Mockito.when(currencyService.getActiveCurrencyByCode("USD")).thenReturn(Optional.of(currency));

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1.0/currency/{code}", "USD").accept(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(convertObjectToJsonString(currency)))
                .andReturn();
        System.out.println("Test get currency result: " + mvcResult.getResponse().getContentAsString());

    }

    @Test
    public void createCurrencyTest() throws Exception {
        CurrencyPO currency = CurrencyPO.builder()
                .code("USD")
                .chName("美元")
                .rate(new BigDecimal(34459.8798))
                .description("United States Dollar")
                .active(true)
                .build();

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/v1.0/currency")
                .accept(APPLICATION_JSON)
                .content(convertObjectToJsonString(currency))
                .contentType(APPLICATION_JSON);

        MvcResult mvcResult = this.mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void updateCurrencyTest() throws Exception {
        CurrencyPO currency = CurrencyPO.builder()
                .code("GBP")
                .chName("英鎊")
                .rate(new BigDecimal(34456.4356))
                .description("British Pound Sterling")
                .active(true)
                .build();

        CurrencyUpdateRequestDTO updateRequestDTO = new CurrencyUpdateRequestDTO();
        updateRequestDTO.setChName("英鎊");
        updateRequestDTO.setRate(new BigDecimal(34456.4356));
        updateRequestDTO.setDescription("British Pound Sterling");

        Mockito.when(currencyService.updateCurrency("GBP", updateRequestDTO)).thenReturn(currency);

        RequestBuilder request = MockMvcRequestBuilders
                .put("/api/v1.0/currency/{code}", "GBP")
                .accept(APPLICATION_JSON)
                .content(convertObjectToJsonString(updateRequestDTO))
                .contentType(APPLICATION_JSON);

        MvcResult mvcResult = this.mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(convertObjectToJsonString(currency)))
                .andReturn();

        System.out.println("update currency result: " + mvcResult.getResponse().getContentAsString());


    }

    @Test
    public void deleteCurrencyTest() throws Exception {
        CurrencyPO currency = CurrencyPO.builder()
                .code("GBP")
                .chName("英鎊")
                .rate(new BigDecimal(34456.4356))
                .description("British Pound Sterling")
                .active(false)
                .build();

        Mockito.when(currencyService.softDeleteCurrency("GBP")).thenReturn(currency);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1.0/currency/{code}", "GBP").accept(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andReturn();

    }


    //Converts Object to Json String
    private String convertObjectToJsonString(Object data) throws JsonProcessingException {
        ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return writer.writeValueAsString(data);
    }
}
