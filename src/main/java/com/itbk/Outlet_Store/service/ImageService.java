package com.itbk.Outlet_Store.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;




@Service
public class ImageService {

  private final Path storageFolder = Paths.get("uploads");

  public ImageService() {
    try {
      Files.createDirectories(storageFolder);
    } catch (Exception e) {
    }
  }

  private boolean isImageFile(MultipartFile file) {
    String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
    return Arrays.asList(new String[]{"png", "jpg", "jfif"}).contains(fileExtension.trim().toLowerCase());
  }


  public String storageFile(MultipartFile file) {
    try {

      if (file.isEmpty()) {
        throw new RuntimeException("File empty");
      }

      if (!isImageFile(file)) {
        throw new RuntimeException("Bạn chỉ úp load file png , jpg");
      }
      float fileSize = file.getSize() / 1_000_000.0f;
      if (fileSize > 5.0f) {
        throw new RuntimeException("File size <= 5MB");
      }
      String fileExtention = FilenameUtils.getExtension(file.getOriginalFilename());

      String generatedFilename = UUID.randomUUID().toString().replace("-", "");

      generatedFilename = generatedFilename + "." + fileExtention;

      Path destinationFilePath = this.storageFolder.resolve(Paths.get(generatedFilename)).normalize()
              .toAbsolutePath();

      if (!destinationFilePath.getParent().equals(this.storageFolder.toAbsolutePath())) {
        throw new RuntimeException("cannot store file outside current derectory");
      }

      try (InputStream inputStream = file.getInputStream()) {
        Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
      }

      return generatedFilename;
    } catch (Exception e) {
      throw new RuntimeException("Faile to store file", e);
    }
  }


  public Stream<Path> loadAll() {
    try {
      return Files.walk(this.storageFolder, 1)
              .filter(path -> !path.equals(this.storageFolder) && !path.toString().contains("._"))
              .map(this.storageFolder::relativize);
    } catch (IOException e) {
      throw new RuntimeException("Failed to load stored files", e);
    }
  }


  public Resource loadAsResource(String filename) {
    try {
      Path file = storageFolder.resolve(filename);
      Resource resource = new UrlResource(file.toUri());

      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new RuntimeException("Could not read file: " + filename);
      }
    } catch (MalformedURLException e) {
      throw new RuntimeException("Could not read file: " + filename, e);
    }
  }


  public byte[] readFileContent(String filenanme) {
    try {
      Path file = storageFolder.resolve(filenanme);
      Resource resource = new UrlResource(file.toUri());

      if (resource.exists() || resource.isReadable()) {
        byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
        return bytes;
      } else {
        throw new RuntimeException(
                "Could not read file: " + filenanme);
      }
    } catch (IOException exception) {
      throw new RuntimeException("Could not read file: " + filenanme, exception);
    }
  }


  public void deleteImage(String filename) {
    try {
      Path imagePath = storageFolder.resolve(filename);
      Files.deleteIfExists(imagePath);
    } catch (IOException e) {
      throw new RuntimeException("Failed to delete image: " + filename, e);
    }
  }

}

