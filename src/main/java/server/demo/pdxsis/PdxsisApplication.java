package server.demo.pdxsis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import server.demo.pdxsis.utils.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class PdxsisApplication {

	public static void main(String[] args) {
		SpringApplication.run(PdxsisApplication.class, args);
	}

}

