package org.example.expert.interceptor;
import org.example.expert.config.utils.JwtUtil;

import org.example.expert.domain.user.enums.UserRole;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(OutputCaptureExtension.class)
class InterceptorTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtUtil jwtUtil;

    @Test
    void 인터셉터로깅withJWT토큰() throws Exception {

        //given
        String jwtToken = jwtUtil.createToken(1L, "test@test.com", UserRole.USER);
        String subJwtToken = jwtUtil.substringToken(jwtToken);

        //when && then
        mockMvc.perform(MockMvcRequestBuilders.get("/todos")
                        .header("User-Agent", "JUnit-Agent")
                        .header("Authorization", "Bearer " + subJwtToken)
                        .requestAttr("originalURI", "/test"))
                .andExpect(status().isOk());
    }
    @Test
    void 어드민인터셉터로깅withJWT토큰(CapturedOutput output) throws Exception {

        //given
        String jwtToken = jwtUtil.createToken(1L, "test@test.com", UserRole.ADMIN);
        String subJwtToken = jwtUtil.substringToken(jwtToken);
        //String log = "ADMIN METHOD : {}, URI : {} , USER-AGENT : {} , IP : {} , STATUS : {} , DURATION : {}ms";
        //when && then
        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/comments/{commentId}", 1)
                        .header("User-Agent", "JUnit-Agent")
                        .header("Authorization", "Bearer " + subJwtToken)
                        .requestAttr("originalURI", "/test"))
                .andExpect(status().isOk());

    }



}