package be.africshop.africshopbackend.paymentModule.controllers;

import be.africshop.africshopbackend.utils.JavaUtils;
import be.africshop.africshopbackend.utils.responses.HttpResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
@RequestMapping(value = "/api/v1/payment/")
@CrossOrigin("*")
public class PaymentController {

    @Value("${stripe.apikey}")
    private String apiKey;

    @GetMapping
    public ResponseEntity<HttpResponse> checkout() throws StripeException {
        Stripe.apiKey =apiKey;

        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(500L)
                        .setCurrency("gbp")
                        .setPaymentMethod("pm_card_visa")
                        .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);

        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Payment successiffuly.", OK, paymentIntent, true));
    }




}
