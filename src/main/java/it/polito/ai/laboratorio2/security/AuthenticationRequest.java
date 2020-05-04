package it.polito.ai.laboratorio2.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest implements Serializable {
    //TODO: what is that??
    //private static final long serialVersionUID = -6986746375915710855L;
    private String username;
    private String password;
}