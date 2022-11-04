package com.solvro.solvrobackend.dto;

import com.solvro.solvrobackend.model.SummaryInfo;

import java.util.List;

public record ServiceSummaryResultDto(List<String> message, SummaryInfo summaryInfo) {
}
