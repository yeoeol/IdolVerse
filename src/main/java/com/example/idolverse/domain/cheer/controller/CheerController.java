package com.example.idolverse.domain.cheer.controller;

import com.example.idolverse.domain.cheer.dto.CheerRequestDto;
import com.example.idolverse.domain.cheer.service.CheerService;
import com.example.idolverse.global.response.ApiResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Cheer-Controller", description = "응원 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cheers")
public class CheerController {

    private final CheerService cheerService;

    @PostMapping
    public ApiResponseDto<Void> cheer(@RequestBody CheerRequestDto cheerRequestDto) {
        cheerService.cheer(cheerRequestDto);
        return ApiResponseDto.success();
    }

    @DeleteMapping
    public ApiResponseDto<Void> cancelCheer(@RequestBody CheerRequestDto cheerRequestDto) {
        cheerService.cancelCheer(cheerRequestDto);
        return ApiResponseDto.success();
    }
}
