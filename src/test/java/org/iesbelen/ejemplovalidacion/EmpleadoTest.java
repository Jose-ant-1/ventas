package org.iesbelen.ejemplovalidacion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;

import com.gargoylesoftware.htmlunit.WebClient;
import org.htmlunit.html.HtmlForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.web.context.WebApplicationContext;

import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlPage;
import org.htmlunit.html.HtmlSubmitInput;

@SpringBootTest
class EmpleadoTest {

    @Autowired
    private WebApplicationContext context;

    private WebClient webClient;

    @BeforeEach
    void setup() {
// Construimos el cliente de Web para simular el navegador
        webClient = MockMvcWebClientBuilder
                .webAppContextSetup(context)
                .build();
    }


    @Test
    void validationForm() throws IOException {

        HtmlPage validationFormPage = webClient.getPage("http://localhost/validation");
                HtmlForm validationForm = validationFormPage.getHtmlElementById("validationForm");

        validationForm.getInputByName("nombre").setValue("Juan");
        validationForm.getInputByName("salario").setValue("1000");
        validationForm.getInputByName("email").setValue("juan@gmail.com");

        HtmlSubmitInput submit = validationForm.getOneHtmlElementByAttribute("input", "type", "submit");
        HtmlPage newValidationPage = submit.click();

        assertThat(newValidationPage.getUrl().toString()).endsWith("/validation");

        String nombreNew = newValidationPage.getHtmlElementById("res_nombre").getTextContent();
        assertThat(nombreNew).isEqualTo("Juan");

        String salarioNew = newValidationPage.getHtmlElementById("res_salario").getTextContent();
        assertThat(salarioNew).contains("1000");

        String emailNew = newValidationPage.getHtmlElementById("res_email").getTextContent();
        assertThat(emailNew).isEqualTo("juan@gmail.com");
    }
}