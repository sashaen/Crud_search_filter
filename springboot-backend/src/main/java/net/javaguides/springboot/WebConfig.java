package net.javaguides.springboot;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import net.javaguides.springboot.controller.EmployeeController;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(getXmlHttpMessageConverter());
    }

    private HttpMessageConverter<Object> getXmlHttpMessageConverter() {
        MarshallingHttpMessageConverter xmlConverter = new MarshallingHttpMessageConverter();

        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(EmployeeController.class);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        xmlConverter.setMarshaller(new Jaxb2Marshaller());
        xmlConverter.setUnmarshaller(new Jaxb2Marshaller());
        xmlConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_XML, MediaType.TEXT_XML));
        return xmlConverter;
    }
}
