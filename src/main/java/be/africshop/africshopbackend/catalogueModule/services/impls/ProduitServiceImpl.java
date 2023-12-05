package be.africshop.africshopbackend.catalogueModule.services.impls;

import be.africshop.africshopbackend.catalogueModule.dto.ProductRequest;
import be.africshop.africshopbackend.catalogueModule.entities.*;
import be.africshop.africshopbackend.catalogueModule.repository.*;
import be.africshop.africshopbackend.catalogueModule.responses.ProductFileResponse;
import be.africshop.africshopbackend.catalogueModule.responses.ProductResponse;
import be.africshop.africshopbackend.catalogueModule.services.ProductService;
import be.africshop.africshopbackend.catalogueModule.utils.ProductConverter;
import be.africshop.africshopbackend.commandeModule.dto.CartRequest;
import be.africshop.africshopbackend.commandeModule.entities.Cart;
import be.africshop.africshopbackend.commandeModule.repository.CartRepository;
import be.africshop.africshopbackend.commandeModule.response.CartResponse;
import be.africshop.africshopbackend.commandeModule.utils.CartConverter;
import be.africshop.africshopbackend.utils.DataStatus;
import be.africshop.africshopbackend.utils.FileUpload;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProduitServiceImpl implements ProductService {

    private final ProductRepository repository;

    private final ProductConverter converter;

    private final CartConverter cartConverter;

    private final ProductCategoryRepository productCategoryRepository;

    private final UniteMesureRepository uniteMesureRepository;

    private final FileAtachementRepository atachementRepository;

    private final WishListRepository wishListRepository;

    private final CartRepository cartRepository;


    //  private final FileUpload fileUpload;

    @Override
    @Transactional
    @SneakyThrows
    public ProductResponse  addProductRequest(ProductRequest request) {
        ProductCategory category = productCategoryRepository.findProductCategoryByDataStatusIsNotAndId(DataStatus.DELETED, request.getIdProductCategory()).orElseThrow();
        UniteMeasure uniteMeasure = uniteMesureRepository.findUniteMesureByDataStatusIsNotAndId(DataStatus.DELETED, request.getIdUniteMesure()).orElseThrow();
        return Optional.of(request).stream()
                .peek(this::upload)
                .map(converter::productRequestToObject)
                .peek(requestSet -> {
                    requestSet.setCodeAuto(RandomStringUtils.randomAlphanumeric(10).toUpperCase());
                    requestSet.setProductCategory(category);
                    requestSet.setUniteMeasure(uniteMeasure);
                    requestSet.setPublish(false);
                })
                .peek(repository::save)
                .map(converter::objectToResponse)
                .findFirst()
                .orElseThrow(() -> new Exception("Error while saving Product"));
    }

    @Override
    @Transactional
    @SneakyThrows
    public ProductResponse updateProductRequest(ProductRequest request, Long id) {
        return Optional.of(request)
                .stream()
                .peek(this::upload)
                .map(requestProduct -> preparedProduct(request, id))
                .map(repository::save)
                .map(converter::objectToResponse)
                .findFirst()
                .orElseThrow(() -> new Exception("Product Not found!"));
    }

    @Override
    @Transactional
    @SneakyThrows
    public ProductResponse deleteProductRequest(Long id) {
        return repository.findProductByDataStatusIsNotAndId(DataStatus.DELETED, id)
                .stream()
                .peek(product -> product.setDataStatus(DataStatus.DELETED))
                .peek(this::deleteFile)
                .map(repository::save)
                .map(converter::objectToResponse)
                .findFirst()
                .orElseThrow(() -> new Exception("Product Not Found!"));
    }

    @Override
    @Transactional(readOnly = true)
    public ProductFileResponse getProductRequestById(Long id) {
        List<String> urls = new ArrayList<>();
        Product product = repository.findProductByDataStatusIsNotAndId(DataStatus.DELETED, id).orElseThrow();
        List<FileAttachement> fileAttachements = atachementRepository.findByProductId(id);

        ProductFileResponse responseFile = new ProductFileResponse();
        if (!fileAttachements.isEmpty()) {
            FileAttachement fileAttachement = fileAttachements.get(0);
            responseFile.setImageName(fileAttachement.getUrl());

            for (FileAttachement f : fileAttachements) {
                urls.add(f.getUrl());
            }
        } else {
            responseFile.setImageName(null);
        }
        responseFile.setId(product.getId());
        responseFile.setLibelle(product.getLibelle());
        responseFile.setDescription(product.getDescription());
        responseFile.setCurrency(product.getCurrency());
        responseFile.setPrice(product.getPrice());
        responseFile.setQuatity(product.getQuatity());
        responseFile.setTag(product.getTag());
        responseFile.setDateCreated(product.getDateCreated());
        responseFile.setDateUpdated(product.getDateUpdated());
        responseFile.setCodeAuto(product.getCodeAuto());
        responseFile.setProductCategory(product.getProductCategory());
        responseFile.setPublish(product.getPublish());
        responseFile.setCountry(product.getCountry());
        responseFile.setUrls(urls);

        return responseFile;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductFileResponse> getAllProductRequests() {
        List<ProductFileResponse> productListInput = new ArrayList<>();
        List<Product> productList = repository.getAllProductsByDataStatusIsNotAndPublishOrderByLibelleAsc(DataStatus.DELETED, true);

        if (!productList.isEmpty()) {
            for (Product product : productList) {
                List<FileAttachement> fileAttachements = atachementRepository.findByProductId(product.getId());

                ProductFileResponse response = new ProductFileResponse();

                if (!fileAttachements.isEmpty()) {
                    FileAttachement fileAttachement = fileAttachements.get(0);
                    response.setImageName(fileAttachement.getUrl());
                } else {
                    response.setImageName(null);
                }
                response.setId(product.getId());
                response.setLibelle(product.getLibelle());
                response.setDescription(product.getDescription());
                response.setCurrency(product.getCurrency());
                response.setPrice(product.getPrice());
                response.setQuatity(product.getQuatity());
                response.setTag(product.getTag());
                response.setDateCreated(product.getDateCreated());
                response.setDateUpdated(product.getDateUpdated());
                response.setCodeAuto(product.getCodeAuto());
                response.setProductCategory(product.getProductCategory());
                response.setPublish(product.getPublish());
                response.setCountry(product.getCountry());
                productListInput.add(response);
            }
        }

        return productListInput;
    }

    @Override
    public List<ProductFileResponse> getAllProductNotPublush() {
        List<ProductFileResponse> productListInput = new ArrayList<>();
        List<Product> productList = repository.getAllProductsByDataStatusIsNotOrderByLibelleAsc(DataStatus.DELETED);

        if (!productList.isEmpty()) {
            for (Product product : productList) {
                List<FileAttachement> fileAttachements = atachementRepository.findByProductId(product.getId());

                ProductFileResponse response = new ProductFileResponse();

                if (!fileAttachements.isEmpty()) {
                    FileAttachement fileAttachement = fileAttachements.get(0);
                    response.setImageName(fileAttachement.getUrl());
                } else {
                    response.setImageName(null);
                }
                response.setId(product.getId());
                response.setLibelle(product.getLibelle());
                response.setDescription(product.getDescription());
                response.setCurrency(product.getCurrency());
                response.setPrice(product.getPrice());
                response.setQuatity(product.getQuatity());
                response.setTag(product.getTag());
                response.setDateCreated(product.getDateCreated());
                response.setDateUpdated(product.getDateUpdated());
                response.setCodeAuto(product.getCodeAuto());
                response.setProductCategory(product.getProductCategory());
                response.setPublish(product.getPublish());
                response.setCountry(product.getCountry());
                productListInput.add(response);
            }
        }

        return productListInput;
    }

    @Override
    public List<ProductFileResponse> getAllProductRequestsFeaturedCategories() {
        List<ProductFileResponse> productListInput = new ArrayList<>();
        List<Product> productList = repository.findByDataStatusIsNotAndFeaturedAndPublishOrderByLibelleAsc(DataStatus.DELETED, true, true);

        return getProductFileResponses(productListInput, productList);
    }

    @Override
    public List<ProductFileResponse> getAllProductRequestsTrending() {
        List<ProductFileResponse> productListInput = new ArrayList<>();
        List<Product> productList = repository.findByDataStatusIsNotAndTrendingAndPublishOrderByLibelleAsc(DataStatus.DELETED, true, true);

        return getProductFileResponses(productListInput, productList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductFileResponse> getProductsByCategory(Long idProductCategory) {

        List<ProductFileResponse> productListInput = new ArrayList<>();
        List<Product> productList = repository.getAllProductByDataStatusIsNotAndProductCategoryIdAndPublishOrderByLibelleAsc(DataStatus.DELETED, idProductCategory, true);

        return getProductFileResponses(productListInput, productList);
    }

    @Override
    public List<ProductFileResponse> getProductsByCategoryAndCountry(Long idProductCategory, Long idCountry) {

        List<ProductFileResponse> productListInput = new ArrayList<>();

        List<Product> productList = repository.findByDataStatusIsNotAndProductCategoryIdAndCountryIdAndPublishOrderByLibelleAsc(DataStatus.DELETED, idProductCategory, idCountry, true);

        return getProductFileResponses(productListInput, productList);
    }

    @Override
    public List<ProductFileResponse> getProductsByCountry(Long idCountry) {
        List<ProductFileResponse> productListInput = new ArrayList<>();

        List<Product> productList = repository.findByDataStatusIsNotAndCountryIdAndPublishOrderByLibelleAsc(DataStatus.DELETED, idCountry, true);

        return getProductFileResponses(productListInput, productList);
    }

    private List<ProductFileResponse> getProductFileResponses(List<ProductFileResponse> productListInput, List<Product> productList) {
        if (!productList.isEmpty()) {
            for (Product product : productList) {
                List<FileAttachement> fileAttachements = atachementRepository.findByProductId(product.getId());

                ProductFileResponse response = new ProductFileResponse();

                if (!fileAttachements.isEmpty()) {
                    FileAttachement fileAttachement = fileAttachements.get(0);
                    response.setImageName(fileAttachement.getUrl());
                } else {
                    response.setImageName(null);
                }

                response.setId(product.getId());
                response.setLibelle(product.getLibelle());
                response.setDescription(product.getDescription());
                response.setCurrency(product.getCurrency());
                response.setPrice(product.getPrice());
                response.setQuatity(product.getQuatity());
                response.setTag(product.getTag());
                response.setDateCreated(product.getDateCreated());
                response.setDateUpdated(product.getDateUpdated());
                response.setCodeAuto(product.getCodeAuto());
                response.setProductCategory(product.getProductCategory());
                response.setPublish(product.getPublish());
                response.setCountry(product.getCountry());
                productListInput.add(response);
            }
        }

        return productListInput;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsByUniteMeasure(Long idUniteMeasure) {
        return repository.getAllByDataStatusIsNotAndUniteMeasureIdAndPublishOrderByLibelleAsc(DataStatus.DELETED, idUniteMeasure, true)
                .stream()
                .map(converter::objectToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsByCategoryAndUniteMeasure(Long idProductCategory, Long idUniteMeasure) {
        return repository.getAllByDataStatusIsNotAndProductCategoryIdAndUniteMeasureIdAndPublishOrderByLibelleAsc(DataStatus.DELETED, idProductCategory, idUniteMeasure, true)
                .stream()
                .map(converter::objectToResponse)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    @SneakyThrows
    public ProductResponse publishProduct(Long id) {
        return repository.findProductByDataStatusIsNotAndId(DataStatus.DELETED, id)
                .stream()
                .peek(this::publish)
                .map(converter::objectToResponse)
                .findFirst()
                .orElseThrow(() -> new Exception("Product not found!"));
    }

    @Override
    public ProductResponse addToCart(Long idProduct, CartRequest request) {
        System.out.println("******************************************************************");
        System.out.println("**************************Enter To Cart**********************************");
        System.out.println("******************************************************************");
        Product product = repository.findProductByDataStatusIsNotAndId(DataStatus.DELETED, idProduct).orElseThrow();

        System.out.println("******************************************************************");
        System.out.println("Product find : "+product.getLibelle());
        System.out.println("******************************************************************");

        product.setCart(true);
        ProductResponse response = new ProductResponse();
        BeanUtils.copyProperties(repository.save(product), response);

        Optional<Cart> optionalCart = cartRepository.findByDataStatusIsNotAndProductId(DataStatus.DELETED, product.getId());

        if (optionalCart.isPresent()) {
            System.out.println("******************************************************************");
            System.out.println("**************************Cart Find**********************************");
            System.out.println("******************************************************************");
            Cart carts = optionalCart.get();
            carts.setQuantity(request.getQuantity());
            cartRepository.save(carts);
        } else {
            System.out.println("******************************************************************");
            System.out.println("**************************Cart Not Find**********************************");
            System.out.println("******************************************************************");
            Cart cart = new Cart();
            cart.setProduct(product);
            cart.setQuantity(request.getQuantity());
            cartRepository.save(cart);
        }
        return response;
    }

    @Override
    public List<CartResponse> getAllCart() {

        List<CartResponse> cartResponses = new ArrayList<>();

        List<Cart> cartList = cartRepository.findByDataStatusIsNot(DataStatus.DELETED);


        for (Cart c : cartList) {

            List<FileAttachement> fileAttachements = atachementRepository.findByProductId(c.getProduct().getId());

            CartResponse response = new CartResponse();

            if (!fileAttachements.isEmpty()) {
                FileAttachement fileAttachement = fileAttachements.get(0);
                response.setId(c.getId());
                response.setProduct(c.getProduct());
                response.setQuantity(c.getQuantity());
                response.setUrl(fileAttachement.getUrl());
                response.setPrice(c.getProduct().getPrice());
                response.setCurrency(c.getProduct().getCurrency());
                response.setCodeAuto(c.getCodeAuto());
                response.setDateCreated(c.getDateCreated());
                response.setDateUpdated(c.getDateUpdated());
                cartResponses.add(response);
            }
        }

        return cartResponses;
    }

    @Override
    public ProductResponse removeFromCart(Long id) {
        Product product = repository.findProductByDataStatusIsNotAndId(DataStatus.DELETED, id).orElseThrow();
        product.setCart(false);
        ProductResponse response = new ProductResponse();
        BeanUtils.copyProperties(repository.save(product), response);
        return response;
    }

    @Override
    public List<ProductFileResponse> getAllProductInCart() {
        List<ProductFileResponse> productListInput = new ArrayList<>();
        List<Product> productList = repository.findByDataStatusIsNotAndCart(DataStatus.DELETED, true);

        return getProductFileResponses(productListInput, productList);
    }

    @Override
    public List<ProductFileResponse> searchProduct(String name) {
        List<ProductFileResponse> productListInput = new ArrayList<>();
        List<Product> productList = repository.findByDataStatusIsNotAndLibelle(DataStatus.DELETED, name);

        return getProductFileResponses(productListInput, productList);

    }

    @Override
    public WishList addProductWhish(Long id) {

        Product product = repository.findProductByDataStatusIsNotAndId(DataStatus.DELETED, id).orElseThrow();
        WishList wishList = new WishList();
        wishList.setProduct(product);
        WishList savedWish = wishListRepository.save(wishList);

        List<WishList> wishLists = wishListRepository.findByProductId(product.getId());

        int tags = 0;
        if (wishLists.size() == 1) {
            tags = 1;
        } else if (wishLists.size() < 5) {
            tags = wishLists.size();
        } else {
            tags = 5;
        }
        product.setTag(String.valueOf(tags));
        repository.save(product);
        return savedWish;
    }

    @Override
    public List<WishList> getAllWish() {
        return wishListRepository.findAll();
    }

    @Override
    public List<WishList> getAllWishByProduct(Long id) {
        return wishListRepository.findByProductId(id);
    }

    @Override
    public CartResponse deleteProductFromCart(Long idProduct) {

        Cart cart = cartRepository.findByDataStatusIsNotAndProductId(DataStatus.DELETED, idProduct).orElseThrow();
        cart.setDataStatus(DataStatus.DELETED);
        Cart cartSave = cartRepository.save(cart);

        CartResponse response = new CartResponse();
        response.setId(cartSave.getId());
        response.setProduct(cartSave.getProduct());
        response.setQuantity(cartSave.getQuantity());
        return response;
    }

    @Override
    public CartResponse updateProductCart(Long id, double quantity) {
        Cart cart = cartRepository.findByDataStatusIsNotAndProductId(DataStatus.DELETED, id).orElseThrow();
        cart.setQuantity(quantity);
        cartRepository.save(cart);
        CartResponse response = new CartResponse();
        response.setProduct(cart.getProduct());
        response.setQuantity(cart.getQuantity());
        return response;
    }

    private Product preparedProduct(ProductRequest request, Long id) {
        ProductCategory category = productCategoryRepository.findProductCategoryByDataStatusIsNotAndId(DataStatus.DELETED, request.getIdProductCategory()).orElseThrow();
        UniteMeasure uniteMeasure = uniteMesureRepository.findUniteMesureByDataStatusIsNotAndId(DataStatus.DELETED, request.getIdUniteMesure()).orElseThrow();
        Product product = repository.findProductByDataStatusIsNotAndId(DataStatus.DELETED, id).orElseThrow();
        product.setProductCategory(category);
        product.setUniteMeasure(uniteMeasure);
        product.setDataStatus(DataStatus.UPDATED);
        converter.productRequestToObject(request, product);
        return product;
    }

    private void publish(Product product) {
        product.setPublish(!product.getPublish());
    }


    private void upload(ProductRequest request) {
        if (request.getImages() != null && !request.getImages().isEmpty()) {
            //     String imageName = fileUpload.uploadBase64Image(request.getImages(), request.getLibelle());
            request.setImages(null);
            //  request.setImageName(imageName);
        }
    }

    private void deleteFile(Product product) {
        //  fileUpload.deleteFile(product.getImageName());
    }

}
