package io.example.documentapproval.web;

import io.example.documentapproval.service.DocumentService;
import io.example.documentapproval.service.dto.DocumentDTO;
import io.example.documentapproval.service.dto.PaymentCommentDTO;
import io.example.documentapproval.service.projection.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class DocumentResource {

    private final DocumentService service;

    @GetMapping("/document")
    public String showDocumentFormView(Model model) {

        List<IClassification> classificationList = service.getClassificationList();

        List<IUserIdEmail> userList = service.getUserList();

        model.addAttribute("userList", userList);

        model.addAttribute("classificationList", classificationList);

        model.addAttribute("dto", new DocumentDTO());

        model.addAttribute("type", "INSERT");

        return "document-form";
    }

    @GetMapping("/document/{id}")
    public String updateDocument(@PathVariable Long id, Model model) {

        List<IClassification> classificationList = service.getClassificationList();

        List<IUserIdEmail> userList = service.getUserList();

        IUpdateDocument document = service.getUpdateDocument(id);

        model.addAttribute("userList", userList);

        model.addAttribute("classificationList", classificationList);

        model.addAttribute("dto", DocumentDTO.convertOf(document));

        model.addAttribute("id", id);

        model.addAttribute("type", "UPDATE");

        return "document-form";
    }

    @GetMapping("/detail-document/{id}")
    public String showDetailDocument(@PathVariable Long id, Model model) {
        IDocument document = service.getDocumentDetail(id);
        model.addAttribute("document", document);
        model.addAttribute("type", "DETAIL");
        return "detail-document";
    }

    @GetMapping("/payment-document/{id}")
    public String showPaymentDocument(@PathVariable Long id, Model model) {
        IDocumentComment document = service.getDocumentPaymentComment(id);
        model.addAttribute("document", document);
        model.addAttribute("type", "PAYMENT");
        return "detail-document";
    }

    @GetMapping("/payment/{id}")
    public String showPaymentView(@PathVariable Long id, Model model) {
        IDocument document = service.getDocumentDetail(id);
        model.addAttribute("document", document);
        model.addAttribute("dto", new PaymentCommentDTO());
        return "payment";
    }

    @DeleteMapping("/api/document/{id}")
    public String removeDocument(@PathVariable Long id) {
        service.deleteDocument(id);
        return "redirect:/outbox-documents";
    }

    @PutMapping("/api/payment-document/{id}")
    public String signDocument(@PathVariable Long id, PaymentCommentDTO dto) {
        service.signDocument(id, dto);
        return "redirect:/inbox-documents";
    }

    @PostMapping("/api/document")
    public String createDocument(DocumentDTO dto) {
        service.createdDocument(dto);
        return "redirect:/outbox-documents";
    }

    @PutMapping("/api/document/{id}")
    public String modifyDocument(@PathVariable Long id, DocumentDTO dto) {
        service.updateDocument(id, dto);
        return "redirect:/detail-document/" + id;
    }

}
