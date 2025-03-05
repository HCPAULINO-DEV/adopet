package com.my.adopet_api.model;

import com.my.adopet_api.dto.AtualizarTutorDto;
import com.my.adopet_api.dto.SalvarTutorDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "tutor")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Tutor implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String foto;

    @NotBlank(message = "Insira um nome válido")
    private String nome;

    @NotBlank(message = "Insira um número de telefone válido")
    private String telefone;

    @NotBlank(message = "Insira um nome de cidade válida")
    private String cidade;

    private String sobre;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    public Tutor(SalvarTutorDto dto, PasswordEncoder passwordEncoder){
        this.foto = dto.foto();
        this.nome = dto.nome();
        this.telefone = dto.telefone();
        this.cidade = dto.cidade();
        this.sobre = dto.sobre();
        this.email = dto.email();
        this.password = passwordEncoder.encode(dto.password()); // Criptografa a senha antes de salvar
    }

    public void atualizarTutor(AtualizarTutorDto dto){
        if (dto.foto() != null){
            this.foto = dto.foto();
        }
        if (dto.nome() != null){
            this.nome = dto.nome();
        }
        if (dto.telefone() != null){
            this.telefone = dto.telefone();
        }
        if (dto.cidade() != null){
            this.cidade = dto.cidade();
        }
        if (dto.sobre() != null){
            this.sobre = dto.sobre();
        }
        if (dto.password() != null){
            this.email = dto.password();
        }
        if (dto.password() != null){
            this.email = dto.password();
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
