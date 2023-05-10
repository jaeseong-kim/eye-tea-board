package com.eyeteaboard.eyeteaboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class EyeTeaBoardApplication {

  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(EyeTeaBoardApplication.class);
    application.addListeners(new ApplicationPidFileWriter());
    application.run(args);

  }

}
