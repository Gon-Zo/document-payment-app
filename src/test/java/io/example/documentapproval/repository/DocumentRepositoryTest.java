package io.example.documentapproval.repository;

import io.example.documentapproval.domain.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@DisplayName("문서 레파지토리 테스트")
class DocumentRepositoryTest {

    @Autowired
    private DocumentRepository documentRepository;

    @Test
    @Transactional(rollbackFor = Exception.class)
    @DisplayName("기본 저장 BaseEntity 저장일 Null TestCase")
    void saveByNull() {

        Document document = Document.builder()
                .title("기본 문서")
                .content("content ...")
                .build();

        documentRepository.save(document);

        Assertions.assertNull(document.getCreateDate());

    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @DisplayName("기본 저장 BaseEntity 저장일 Not Null TestCase")
    void saveByNotNull() {

        Document document = Document.builder()
                .title("기본 문서")
                .content("content ...")
                .build();

        documentRepository.save(document);

        Assertions.assertNotEquals(document.getCreateDate(), null);

    }

}
