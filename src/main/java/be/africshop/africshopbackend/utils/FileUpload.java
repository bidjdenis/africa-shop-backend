package be.africshop.africshopbackend.utils;


import be.africshop.africshopbackend.catalogueModule.entities.FileAttachement;
import be.africshop.africshopbackend.catalogueModule.entities.Product;
import be.africshop.africshopbackend.catalogueModule.repository.FileAtachementRepository;
import be.africshop.africshopbackend.catalogueModule.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FileUpload {


    private final ProductRepository repository;
    private final FileAtachementRepository atachementRepository;


    @Value("${upload.directory}")
    private String uploadDir;

    public FileUpload(ProductRepository repository, FileAtachementRepository atachementRepository) {
        this.repository = repository;
        this.atachementRepository = atachementRepository;

    }

    public String uploadFile(MultipartFile[] files, Long productId) throws IOException {

       Product product = repository.findProductByDataStatusIsNotAndId(DataStatus.DELETED, productId).orElseThrow();

        System.out.println("********************************************");
        System.out.println("Product Id : "+product.getId());
        System.out.println("********************************************");

       if (files == null || files.length == 0) {
                return  "Veuillez selectionner un fichier !";
            }

       File storeFile = new File(uploadDir);

       if (!storeFile.exists()) {
           storeFile.mkdirs();
       }

       for (MultipartFile file: files) {
            File uploadedFile = new File(storeFile, Objects.requireNonNull(file.getOriginalFilename()));
            file.transferTo(uploadedFile);
            String imageName = file.getOriginalFilename();
           String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/product/images/").path(imageName).toUriString();
           Path path = Paths.get(uploadDir, imageName);

           Optional<FileAttachement> fileAttachementOptional = atachementRepository.findByProductIdAndImageName(productId, imageName);

           if (fileAttachementOptional.isPresent()) {
               FileAttachement fileAttachement = fileAttachementOptional.get();
               atachementRepository.deleteById(fileAttachement.getId());
           }



           FileAttachement fileAttachement = new FileAttachement();
           fileAttachement.setProduct(product);
           fileAttachement.setImageName(imageName);
           fileAttachement.setPath(path.toString());
           fileAttachement.setUrl(url);
           atachementRepository.save(fileAttachement);
           System.out.println("********************************************");
           System.out.println("File  : "+fileAttachement);
           System.out.println("********************************************");
           List<FileAttachement> fileAttachements = atachementRepository.findByProductId(productId);
           FileAttachement productFile = fileAttachements.get(0);
           product.setImageName(productFile.getImageName());
           repository.save(product);

       }

       return "File Stored with success!";
   }

   public String uploadBase64Image(String imageBase64, String libelle)  {
       String imageName = "";
       try {

           File uplaodDirectory = new File(uploadDir);
           if (!uplaodDirectory.exists()) {
               uplaodDirectory.mkdirs();
           }

           // Décoder l'image en base64 et la stocker dans un dossier
           byte[] imageBytes = Base64.getDecoder().decode(imageBase64);

           imageName = libelle + System.currentTimeMillis() + ".jpg"; // Générer un nom d'image unique
           String imageFilePath = uplaodDirectory+"/"+ imageName;

           // Écrire les octets dans un fichier sur le serveur
           try (FileOutputStream fos = new FileOutputStream(new File(imageFilePath))) {
               fos.write(imageBytes);
           }

           return imageName;
          // ImageIO.write(bufferedImage, "jpg", imageFile);

       } catch (IOException e) {
           return "Erreur server"+e.getMessage();
       }

   }

    public Resource getImage(String fileName) throws IOException {
        Path cheminImage = Paths.get(uploadDir).resolve(fileName);
        Resource ressource = new UrlResource(cheminImage.toUri());

        if (ressource.exists() && ressource.isReadable()) {
            return ressource;
        } else {
            throw new RuntimeException("Impossible de charger l'image : " + fileName);
        }
    }

    public void deleteFile(String fileName) {
        String imageFilePath = uploadDir+"/"+ fileName;
        Path fichierPath = Paths.get(imageFilePath);

        try {
            // Supprimer le fichier
            Files.delete(fichierPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
