package com.gamemoonchul.infrastructure.web.common.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;

@Getter
@Setter
public class ApiPageRequest {
    @Min(value = 0)
    private int page;

    @Min(value = 1)
    @Max(value = 1000)
    private int size;

    public PageRequest convert() {
        return PageRequest.of(getPage(), getSize());
    }
}
