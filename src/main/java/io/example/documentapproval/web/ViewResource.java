package io.example.documentapproval.web;

import io.example.documentapproval.service.dto.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewResource {

  @GetMapping("/login")
  public String showLoginView(Model model) {
    model.addAttribute("dto", new UserDTO());
    return "login";
  }
}
