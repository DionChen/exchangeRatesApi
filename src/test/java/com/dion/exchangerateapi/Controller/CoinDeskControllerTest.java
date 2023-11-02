package com.dion.exchangerateapi.Controller;

import com.dion.exchangerateapi.service.CurrencyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(CoinDeskController.class)
public class CoinDeskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CurrencyService currencyService;

    @Test
    public void nativeCoinDeskApiTest() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1.0/coindesk/native-api").accept(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println("Test get coinDesk Api result: " + mvcResult.getResponse().getContentAsString());

    }

    @Test
    public void newCoinDeskApiTest() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1.0/coindesk/transformed-api").accept(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println("Test get newCoinDeskApiTest Api result: " + mvcResult.getResponse().getContentAsString());
    }
}
