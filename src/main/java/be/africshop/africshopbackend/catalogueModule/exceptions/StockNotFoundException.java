package be.africshop.africshopbackend.catalogueModule.exceptions;

public class StockNotFoundException extends Exception {
    public StockNotFoundException(String message) {
        super(message);
    }
}
