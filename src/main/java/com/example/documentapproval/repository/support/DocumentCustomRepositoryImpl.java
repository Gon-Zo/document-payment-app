package com.example.documentapproval.repository.support;

import com.example.documentapproval.domain.Document;
import com.example.documentapproval.repository.support.boxaction.BoxActionFactory;
import com.example.documentapproval.service.dto.DocumentInfo;
import com.example.documentapproval.service.dto.PaymentCommentInfo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.documentapproval.domain.QDivision.division;
import static com.example.documentapproval.domain.QDocument.document;
import static com.example.documentapproval.domain.QPaymentComment.paymentComment;
import static com.example.documentapproval.domain.QUser.user;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

public class DocumentCustomRepositoryImpl extends QuerydslRepositorySupport
    implements DocumentCustomRepository {

  private final JPAQueryFactory jpaQueryFactory;

  public DocumentCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
    super(Document.class);
    this.jpaQueryFactory = jpaQueryFactory;
  }

  @Override
  public Page<DocumentInfo> findByBoxAction(Pageable pageable, BoxActionFactory factory) {

    JPAQuery<DocumentInfo> query =
        jpaQueryFactory
            .select(
                Projections.fields(
                    DocumentInfo.class,
                    document.id,
                    document.title,
                    document.content,
                    document.createdDate,
                    document.updatedDate,
                    document.user.email.as("writer"),
                    document.division.name.as("divisionName"),
                    document.state.as("state"),
                    document.step))
            .from(document)
            .innerJoin(document.user, user)
            .innerJoin(document.division, division)
            .leftJoin(document.paymentCommentSet, paymentComment)
            .where(factory.getBoxListInWhere());

    List<DocumentInfo> result =
        query.fetch().stream()
            .skip(pageable.getOffset())
            .limit(pageable.getPageSize())
            .collect(Collectors.toList());

    long total = query.fetchCount();

    return new PageImpl<>(result, pageable, total);
  }
}
