package br.com.senior.cursomc.resources;

import br.com.senior.cursomc.dto.EmailDto;
import br.com.senior.cursomc.security.JwtUtil;
import br.com.senior.cursomc.security.UserSS;
import br.com.senior.cursomc.services.AuthService;
import br.com.senior.cursomc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value="/auth")
public class AuthResource {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthService authService;

    //Usado para renovar o token de acesso do usuário quando o existente estiver próximo de expirar

    //É protegido por altenticação, o usuártio deve estar logado para acessar
    @RequestMapping(value="/refresh_token", method= RequestMethod.POST)
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        //pega o usuário logado
        UserSS user = UserService.authenticated();
        //É gerado um novo Token
        String token = jwtUtil.generateToken(user.getUsername());
        //Adiciona o novo token no response da requisição
        response.addHeader("Authorization", "Bearer " + token);
        //Libera o acesso do CORS, pois por padrão ele é mais 'rígido' para responces com headers personalizados
        response.addHeader("access-control-expose-headers", "Authorization");
        return ResponseEntity.noContent().build();
    }

    //gera uma nova senha
    @RequestMapping(value="/forgot", method= RequestMethod.POST)
    public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDto objDto) {
        authService.sendNewPassord(objDto.getEmail());
        return ResponseEntity.noContent().build();
    }
}
