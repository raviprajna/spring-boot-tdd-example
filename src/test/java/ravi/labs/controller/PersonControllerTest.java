package ravi.labs.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ravi.labs.TestData;
import ravi.labs.TestUtil;
import ravi.labs.exception.PersonConstraintVoilationException;
import ravi.labs.exception.PersonNotFoundException;
import ravi.labs.model.Person;
import ravi.labs.service.PersonDataService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonDataService personDataService;

    @Test
    public void shouldReturnPersonFromService() throws Exception {
        when(personDataService.findPerson(any(), any())).thenReturn(new Person("Mary", "Smith"));
        this.mockMvc.perform(get("/person/smith/mary"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").exists())
            .andExpect(jsonPath("firstName").value("Mary"))
            .andExpect(jsonPath("lastName").value("Smith"));
    }

    @Test
    public void shouldReturnPersonNotFoundErrorFromService() throws Exception {
        when(personDataService.findPerson(any(), any())).thenThrow(new PersonNotFoundException("Could not find Person"));
        this.mockMvc.perform(get("/person/YYYY/XXXX"))
            .andDo(print())
            .andExpect(status().isNotFound() )
            .andExpect(jsonPath("message").value("Not Found"));
    }

    @Test
    public void shouldReturnAllPersonBySurnameFromService() throws Exception {
        List<Person> persons = TestData.getPersonsByLastNamePrajna();

        when(personDataService.findAllPersonBySurname(any())).thenReturn(persons);
        this.mockMvc.perform(get("/persons?lastName=Prajna"))
            .andDo(print())
            .andExpect(status().isOk() )
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").value(hasSize(persons.size())));
    }

    @Test
    public void shouldReturnEmptyPersonListFromService() throws Exception {
        List<Person> persons = TestData.getEmptyPersonList();
        when(personDataService.findAllPersonBySurname(any())).thenReturn(persons);
        this.mockMvc.perform(get("/persons?lastName=XXXX"))
            .andDo(print())
            .andExpect(status().isOk() )
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").value(hasSize(0)));
    }

    @Test
    public void shouldAddPersonFromService() throws Exception {
        final Person person = new Person("FN", "LN");
        when(personDataService.addPerson(any())).thenReturn(person);
        mockMvc.perform( post("/person")
                .content(TestUtil.convertObjectToJsonBytes(person))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("firstName").value("FN"))
                .andExpect(jsonPath("lastName").value("LN"));
    }

    @Test
    public void shouldThrowEmptyOrDuplicateExceptionWhileAddingPersonFromService() throws Exception {
        final Person person = new Person("FN", "LN");
        when(personDataService.addPerson(any()))
                .thenThrow(new PersonConstraintVoilationException("Empty or Duplicate names"));
        mockMvc.perform( post("/person")
                .content(TestUtil.convertObjectToJsonBytes(person))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable());

    }
}