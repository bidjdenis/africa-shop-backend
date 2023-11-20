package be.africshop.africshopbackend.utils.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse implements Serializable {
    private String access_token;
    private String refresh_token;
}
