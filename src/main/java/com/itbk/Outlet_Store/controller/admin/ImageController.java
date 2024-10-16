package com.itbk.Outlet_Store.controller.admin;

import com.itbk.Outlet_Store.service.ImageService;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("display")
public class ImageController {

  private final ImageService imageService;

  public ImageController(ImageService imageService) {
    this.imageService = imageService;
  }


  @GetMapping("/files/{fileName:.+}")
  public ResponseEntity<Resource> serveFile(@PathVariable String fileName) {

    Resource file = this.imageService.loadAsResource(fileName);

    return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(file);

  }


}
