package com.company.controller;

import com.company.dto.attach.AttachDTO;
import com.company.service.AttachService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Slf4j
@Api(tags = "Attach Controller")
@RestController
@RequestMapping("/attach")
public class AttachController {

    @Autowired
    private AttachService attachService;

    @ApiOperation(value = "Attach Upload For Article", notes = "Attach upload")
    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {

        AttachDTO dto = attachService.saveToSystem(file);
        log.info("Request for attach upload for article");
        return ResponseEntity.ok().body(dto);
    }

    @ApiOperation(value = "Attach Upload For Profile", notes = "Attach upload")
    @PostMapping("/upload/profile")
    public ResponseEntity<?> uploadForProfile(@RequestParam("file") MultipartFile file) {

        AttachDTO dto = attachService.saveToSystemForProfile(file);
        log.info("Request for attach upload for profile ");
        return ResponseEntity.ok().body(dto);
    }

    @ApiOperation(value = "Attach Open ", notes = "Attach open by attachId")
    @GetMapping(value = "/open", produces = MediaType.ALL_VALUE)
    public byte[] open_general(@RequestParam("fileId") String fileUUID) {
        log.info("Request open attach attachId: {}", fileUUID);
        return attachService.openGeneral(fileUUID);
    }

    @ApiOperation(value = "Attach Download", notes = "Attach download by attachId")
    @GetMapping("/download")
    public ResponseEntity<Resource> download(@RequestParam("fileId") String fileId) {
        Resource file = attachService.download(fileId);
        log.info("Request download attach attachId: {}", fileId);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @ApiOperation(value = "Attach Deleted ", notes = "Attach deleted DB and Files by attachId")
    @DeleteMapping("/deleted")
    public ResponseEntity<?> deleted(@RequestParam("fileId") String fileId) {
        log.info("Request deleted attach attachId: {}", fileId);
        String response = attachService.deleted(fileId);

        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Attach Pagination By page and size", notes = "Attach pagination by attachId")
    @GetMapping("/pagination")
    public ResponseEntity<?> pagination(@RequestParam("page") Integer page,
                                        @RequestParam("size") Integer size) {
        PageImpl pagination = attachService.pagination(page, size);
        log.info("Request pagination attach page: {}, size: {}", page, size);
        return ResponseEntity.ok(pagination);
    }
}
