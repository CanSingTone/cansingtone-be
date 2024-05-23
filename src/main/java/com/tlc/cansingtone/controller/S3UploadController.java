package com.tlc.cansingtone.controller;

import com.tlc.cansingtone.dto.TimbreUploadDto;

import com.tlc.cansingtone.service.S3UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(produces = "application/json;charset=utf8")
public class S3UploadController {
    @Autowired
    private S3UploadService s3Uploader;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TimbreUploadDto> uploadImg(@RequestParam("file") MultipartFile file) throws IOException {
        TimbreUploadDto timbreUploadDto = new TimbreUploadDto();
        System.out.println("Upload");
        System.out.println(file);
        System.out.println("------------------------------------------------------");
        if (!file.isEmpty()) {
            String storedFileName = s3Uploader.upload(file, "timbre");
            timbreUploadDto.setUploadTimbre(storedFileName);
        }

        return ResponseEntity.status(HttpStatus.OK).body(timbreUploadDto);
    }
}
