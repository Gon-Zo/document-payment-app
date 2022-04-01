package com.example.documentapproval;

import com.example.documentapproval.config.InitConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@SpringBootApplication
public class DocumentApprovalApplication {

  public static void main(String[] args) {
    SpringApplication.run(DocumentApprovalApplication.class, args);
  }

  private final InitConfiguration initConfiguration;

  @PostConstruct
  public void init() {
    initConfiguration.init();
  }
}
