package be.africshop.africshopbackend.catalogueModule.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class FileRequest implements Serializable {

    private Long idProduct;
    MultipartFile[] files;

}
