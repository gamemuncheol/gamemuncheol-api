package com.gamemoonchul.domain.dto;

public record LectureSearchCondition (
        String employeeId
) {
    public boolean hasEmployeeId() {
        return employeeId != null && !employeeId.isEmpty();
    }
}
