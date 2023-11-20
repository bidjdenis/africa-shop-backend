package be.africshop.africshopbackend.catalogueModule.services;

import be.africshop.africshopbackend.catalogueModule.entities.FileAttachement;

import java.util.List;

public interface FileAttachementService {
    List<FileAttachement> getAllByProduct(Long productId);

}
