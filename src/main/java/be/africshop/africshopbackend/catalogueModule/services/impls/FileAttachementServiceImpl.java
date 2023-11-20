package be.africshop.africshopbackend.catalogueModule.services.impls;

import be.africshop.africshopbackend.catalogueModule.entities.FileAttachement;
import be.africshop.africshopbackend.catalogueModule.repository.FileAtachementRepository;
import be.africshop.africshopbackend.catalogueModule.services.FileAttachementService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileAttachementServiceImpl implements FileAttachementService {

    private final FileAtachementRepository repository;

    public FileAttachementServiceImpl(FileAtachementRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<FileAttachement> getAllByProduct(Long productId) {
        return repository.findByProductId(productId);
    }
}
