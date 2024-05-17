package com.hong.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// import org.springframework.context.annotation.Bean;

// import java.util.Locale;
// import org.springframework.context.MessageSource;
// import org.springframework.context.support.ReloadableResourceBundleMessageSource;

// import com.hong.demo.repository.BookRepository;
// import com.hong.demo.repository.ReviewRepository;
// import com.hong.demo.service.BookService;

@SpringBootApplication
public class MvcJdbcRestApiHApplication {
	public static void main(String[] args) {
		SpringApplication.run(MvcJdbcRestApiHApplication.class, args);
	}

	// @Bean
    // public CommandLineRunner run(UserRepository userRepository) throws Exception {
    //     return (String[] args) -> {
    //         User user1 = new User("Bob", "bob@domain.com");
    //         User user2 = new User("Jenny", "jenny@domain.com");
    //         userRepository.save(user1);
    //         userRepository.save(user2);
    //         userRepository.findAll().forEach(System.out::println);
    //     };
    // }

    // @Bean
    // public MessageSource messageSource() {
    //     Locale.setDefault(Locale.ENGLISH);
    //     ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    //     messageSource.addBasenames("classpath:org/springframework/security/messages");
    //     return messageSource;
    // }
}
