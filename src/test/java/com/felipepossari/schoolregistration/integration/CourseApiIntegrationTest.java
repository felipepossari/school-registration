package com.felipepossari.schoolregistration.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.felipepossari.schoolregistration.adapter.in.web.courses.v1.request.CourseRequest;
import com.felipepossari.schoolregistration.adapter.in.web.courses.v1.response.CourseResponse;
import com.felipepossari.schoolregistration.base.request.CourseRequestTestBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class CourseApiIntegrationTest {

    @Autowired
    ObjectMapper om;

    @Autowired
    MockMvc mockMvc;

    @Test
    void postShouldCreateCourseIfRequestIsValid() throws Exception {
        // given
        CourseRequest request = CourseRequestTestBuilder.aCourseRequest().build();

        // when
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/v1/courses")
                                .contentType(APPLICATION_JSON_VALUE)
                                .content(om.writeValueAsString(request))
                ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        var location = mvcResult.getResponse().getHeader(HttpHeaders.LOCATION).split("/");
        long courseId = Long.parseLong(location[location.length - 1]);

        // then
        mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/v1/courses/{id}", courseId)
                                .contentType(APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        CourseResponse response = parseCourseResponse(mvcResult.getResponse().getContentAsString());

        assertNotNull(response);
        assertEquals(courseId, response.getId().longValue());
        assertEquals(request.getName(), response.getName());
    }

    @Test
    void putShouldUpdateCourseIfRequestIsValid() throws Exception {
        // given
        CourseRequest request = CourseRequestTestBuilder.aCourseRequest().build();

        // when
        // create course
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/v1/courses")
                                .contentType(APPLICATION_JSON_VALUE)
                                .content(om.writeValueAsString(request))
                ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        var location = mvcResult.getResponse().getHeader(HttpHeaders.LOCATION).split("/");
        long courseId = Long.parseLong(location[location.length - 1]);

        // update course
        CourseRequest updateRequest = CourseRequestTestBuilder.aCourseRequest().name("Course updated").build();

        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/v1/courses/{id}", courseId)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(om.writeValueAsString(updateRequest))
        ).andExpect(MockMvcResultMatchers.status().isNoContent());

        // then
        mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/v1/courses/{id}", courseId)
                                .contentType(APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        CourseResponse response = parseCourseResponse(mvcResult.getResponse().getContentAsString());

        assertNotNull(response);
        assertEquals(courseId, response.getId().longValue());
        assertEquals(updateRequest.getName(), response.getName());
    }


    private CourseResponse parseCourseResponse(String contentAsString) throws IOException {
        return om.readValue(contentAsString, CourseResponse.class);
    }
}
