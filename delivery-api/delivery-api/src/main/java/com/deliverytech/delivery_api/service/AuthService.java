package com.deliverytech.delivery_api.service;

import com.deliverytech.delivery_api.dto.req.RegisterReqDTO;
import com.deliverytech.delivery_api.entity.Usuario;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {

    boolean existsByEmail(String email);

    Usuario criarUsuario(RegisterReqDTO request);

    Usuario buscarPorId(Long id);

    Usuario buscarPorEmail(String email);
}
