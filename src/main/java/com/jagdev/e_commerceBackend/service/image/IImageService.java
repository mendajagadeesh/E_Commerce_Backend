package com.jagdev.e_commerceBackend.service.image;

import com.jagdev.e_commerceBackend.Dto.ImageDto;
import com.jagdev.e_commerceBackend.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImages(List<MultipartFile> file, Long productId);
    void updateImage(MultipartFile file,Long imageId);
}
