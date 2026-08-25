package com.bortolanza.fleet.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "jwt.secret=test-secret-key-123456789012345678901234567890")
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturn401WhenAccessingVehiclesWithoutToken() throws Exception {
        mockMvc.perform(get("/vehicles"))
               .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturn401WhenAccessingCompaniesWithoutToken() throws Exception {
        mockMvc.perform(get("/companies"))
               .andExpect(status().isUnauthorized());
    }
}
