package com.leafbodhi.nostr;

import com.baomidou.mybatisplus.test.autoconfigure.AutoConfigureMybatisPlus;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leafbodhi.nostr.controller.HttpController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HttpController.class)
@AutoConfigureMybatisPlus
class TestWebHttpController {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testHttpForJsonAccept() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/")
                        .accept(MediaType.APPLICATION_JSON).param("http", ""))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Please use a Nostr client to connect.")));
    }

    @Test
    void testHttpForNostrAccept() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/").accept("application/nostr+json").param("nostr", "")).andExpect(status().isOk()).andDo(print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(result.getResponse().getContentAsString());
        assertThat(node, notNullValue());
        assertThat(node.get("name"), notNullValue());
        assertThat(node.get("name").asText(), equalTo("bodhi-relay"));
    }

}
