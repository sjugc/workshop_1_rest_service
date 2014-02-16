package com.sjugc.workshop.w1.rest.millionaire;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import com.sjugc.workshop.w1.rest.millionaire.core.util.QuestionReader;

/**
 * Main class with maim method.
 * Additionally contains definition of Spring Beans required for proper application work.
 * 
 * @author mjovanovic
 */
@ComponentScan
@EnableAutoConfiguration
@Configuration
public class Application {

    /**
     * Main method for initialization of Spring application context.
     * 
     * @param args {@link String[]} Array of input parameters.
     */
    public static void main( String[] args ) {
        SpringApplication.run( Application.class, args );
    }

    /**
     * JAXB Marshaller object reading XML files.
     * 
     * @return {@link Jaxb2Marshaller} singleton instance of object.
     */
    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan( new String[] { "com.sjugc.workshop.w1.rest.millionaire.core.model.generated" } );
        return marshaller;
    }

    /**
     * {@link QuestionReader} object for reading XML files with game questions.
     * 
     * @return {@link QuestionReader} singleton instance of object.
     */
    @Bean
    public QuestionReader questionReader() {
        return new QuestionReader( jaxb2Marshaller() );
    }

}
