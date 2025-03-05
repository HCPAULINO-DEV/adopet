package com.my.adopet_api.model;

import com.my.adopet_api.dto.AtualizarAbrigoDto;
import com.my.adopet_api.dto.AuthenticationDto;
import com.my.adopet_api.dto.SalvarAbrigoDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "abrigo")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Abrigo implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "CNPJ é obrigatório!")
    private String   cnpj;

    @NotBlank(message = "Nome do abrigo é obrigatório!")
    private String nome;

    @Embedded
    private Endereco endereco;

    @OneToMany(mappedBy = "abrigo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Pet> pets;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.ADMIN;

    public Abrigo(SalvarAbrigoDto dto, PasswordEncoder passwordEncoder) {
        this.cnpj = dto.cnpj();
        this.nome = dto.nome();
        this.endereco = dto.endereco();
        this.email = dto.email();
        this.password = passwordEncoder.encode(dto.password()); // Criptografa a senha antes de salvar
    }


    public void atualizarAbrigo(AtualizarAbrigoDto dto){
        if (dto.cnpj() != null){
            this.cnpj = dto.cnpj();
        }
        if (dto.nome() != null){
            this.nome = dto.nome();
        }
        if (dto.endereco() != null){
            this.endereco = dto.endereco();
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
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
