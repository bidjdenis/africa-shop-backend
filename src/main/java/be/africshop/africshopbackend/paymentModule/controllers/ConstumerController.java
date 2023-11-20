package be.africshop.africshopbackend.paymentModule.controllers;

import be.africshop.africshopbackend.paymentModule.dto.CustomerRequest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.CustomerCollection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/payment/")
public class ConstumerController {

    @Value("${stripe.apikey}")
    private String apiKey;

    @PostMapping("/create_customer")
    public CustomerRequest index(@RequestBody CustomerRequest request) throws StripeException {

        Stripe.apiKey =apiKey;

        Map<String, Object> params = new HashMap<>();
        params.put("name", request.getName());
        params.put("email", request.getEmail());

        Customer customer = Customer.create(params);
        request.setCoustomerId(customer.getId());
        return request;
    }

    @GetMapping("all")
    public List<CustomerRequest> getAll() throws StripeException {
        List<CustomerRequest> requestList = new ArrayList<>();
        Stripe.apiKey =apiKey;

        Map<String, Object> params = new HashMap<>();
        params.put("limit", 3);
        CustomerCollection customers =Customer.list(params);
        for (int i = 0; i < customers.getData().size(); i++) {
            CustomerRequest request = new CustomerRequest();
            request.setCoustomerId(customers.getData().get(i).getId());
            request.setName(customers.getData().get(i).getName());
            request.setEmail(customers.getData().get(i).getEmail());
            requestList.add(request);
        }
        return requestList;
    }

    @GetMapping("{id}")
    public CustomerRequest getOne(@PathVariable String id) throws StripeException {

        Stripe.apiKey =apiKey;

        Customer customer =
                Customer.retrieve(id);
        CustomerRequest request = new CustomerRequest();
        request.setCoustomerId(customer.getId());
        request.setName(customer.getName());
        request.setEmail(customer.getEmail());
        return request;
    }

    @PutMapping("update/{id}")
    public CustomerRequest update(@RequestBody CustomerRequest request, @PathVariable String id) throws StripeException {
        Stripe.apiKey =apiKey;
        Customer customer =
                Customer.retrieve(id);

      //  Map<String, Object> metadata = new HashMap<>();
       // metadata.put("name", request.getName());
        Map<String, Object> params = new HashMap<>();
        params.put("name", request.getName());
        params.put("email", request.getEmail());
        Customer updatedCustomer = customer.update(params);
        request.setCoustomerId(updatedCustomer.getId());
        return request;
    }

    @DeleteMapping("delete/{id}")
    public CustomerRequest delete(@PathVariable String id) throws StripeException {
        Stripe.apiKey =apiKey;
        Customer customer =
                Customer.retrieve(id);
        CustomerRequest request = new CustomerRequest();
        Customer deletedCustomer = customer.delete();
        request.setCoustomerId(deletedCustomer.getId());
        request.setName(deletedCustomer.getName());
        request.setEmail(deletedCustomer.getEmail());

        return request;
    }

}
