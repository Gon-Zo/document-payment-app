package com.example.documentapproval.service;

import com.example.documentapproval.service.dto.IDivision;

import java.util.List;

/** 마스터 분류 서비스 IF */
public interface DivisionService {


    List<IDivision> getDivisionList();
}
