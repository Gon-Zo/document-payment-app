package com.example.documentapproval.web;

import com.example.documentapproval.service.dto.LoginDTO;
import com.example.documentapproval.web.dosc.ViewResourceDocs;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ViewResource implements ViewResourceDocs {

  @Override
  @PostMapping("/login")
  public void login(@RequestBody LoginDTO dto) {}
}
