package com.sparta.twotwo.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PresignedUrlResponseDto {
    private String presignedUrl; // S3에 파일 업로드할 때 사용할 URL
    private String imageKey;     // 이미지 저장 후 식별용 key
}