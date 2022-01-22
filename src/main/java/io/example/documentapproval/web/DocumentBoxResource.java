package io.example.documentapproval.web;

import io.example.documentapproval.service.DocumentBoxService;
import io.example.documentapproval.service.projection.IDocumentBox;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("")
public class DocumentBoxResource {

    private final DocumentBoxService service;

    @GetMapping("/archive-documents")
    public String showArchiveDocuments(Model model) {
        List<IDocumentBox> data = service.getArchiveDocumentList();
        model.addAttribute("list", data);
        model.addAttribute("type", "ARCHIVE");
        return "document-list";
    }

    @GetMapping("/inbox-documents")
    public String showInBoxDocuments(Model model) {
        List<IDocumentBox> data = service.getInBoxDocumentList();
        model.addAttribute("list", data);
        model.addAttribute("type", "INBOX");
        return "document-list";
    }

}
