package com.example.willhabenfake.Models;

import java.util.List;
import java.util.stream.Collectors;

public class ImageDto {
    private byte[] imageData;


    public ImageDto() {}

    public ImageDto(byte[] imageData) {
        this.imageData = imageData;
    }

    public static ImageDto toImageDto(Image image) {
        ImageDto imageDto = new ImageDto();
        imageDto.setImageData(image.getImageData());
        return imageDto;
    }

    public static List<ImageDto> toImageDtoList(List<Image> images) {
        return images.stream().
                map(image -> toImageDto(image)).
                collect(Collectors.toList());
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}
