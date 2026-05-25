package com.deliverytech.delivery_api.controller;

import com.deliverytech.delivery_api.dto.req.LoginReqDTO;
import com.deliverytech.delivery_api.dto.res.LoginResDTO;
import com.deliverytech.delivery_api.dto.req.RegisterReqDTO;
import com.deliverytech.delivery_api.dto.res.UserResDTO;
import com.deliverytech.delivery_api.entity.Usuario;
import com.deliverytech.delivery_api.security.JwtUtil;
import com.deliverytech.delivery_api.security.SecurityUtils;
import com.deliverytech.delivery_api.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginReqDTO loginReq) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginReq.getEmail(),
                    loginReq.getSenha()
                )
            );

            UserDetails userDetails = authService.loadUserByUsername(loginReq.getEmail());
            String token = jwtUtil.generateToken(userDetails);

            Usuario usuario = (Usuario) userDetails;
            UserResDTO UserResDTO = new UserResDTO(usuario);
            LoginResDTO loginResponse = new LoginResDTO(token, jwtExpiration, UserResDTO);

            return ResponseEntity.ok(loginResponse);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Credenciais inválidas");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno do servidor: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterReqDTO registerReq) {
        try {
            if (authService.existsByEmail(registerReq.getEmail())) {
                return ResponseEntity.badRequest().body("Email já está em uso");
            }

            Usuario novoUsuario = authService.criarUsuario(registerReq);
            UserResDTO UserResDTO = new UserResDTO(novoUsuario);
            return ResponseEntity.status(201).body(UserResDTO);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao criar usuário: " + e.getMessage());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        try {
            Usuario usuarioLogado = SecurityUtils.getCurrentUser();
            UserResDTO UserResDTO = new UserResDTO(usuarioLogado);
            return ResponseEntity.ok(UserResDTO);

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Usuário não autenticado");
        }
    }
}
