package io.example.documentapproval.web;

import io.example.documentapproval.service.DocumentBoxService;
import io.example.documentapproval.service.dto.UserDTO;
import io.example.documentapproval.service.projection.IDocumentBox;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ViewResource {

    private final DocumentBoxService service;

    @GetMapping("/login")
    public String showLoginView(Model model) {
        model.addAttribute("dto", new UserDTO());
        return "login";
    }

    @GetMapping("/home")
    public String showOutBoxDocuments(Model model) {
        List<IDocumentBox> data = service.getOutBoxDocumentList();
        model.addAttribute("list", data);
        model.addAttribute("type", "OUTBOX");
        return "home";
    }

    @GetMapping("/archive-documents")
    public String showArchiveDocuments(Model model) {
        List<IDocumentBox> data = service.getArchiveDocumentList();
        model.addAttribute("list", data);
        model.addAttribute("type", "ARCHIVE");
        return "home";
    }

    @GetMapping("/inbox-documents")
    public String showInBoxDocuments(Model model) {
        List<IDocumentBox> data = service.getInBoxDocumentList();
        model.addAttribute("list", data);
        model.addAttribute("type", "INBOX");
        return "home";
    }

}
