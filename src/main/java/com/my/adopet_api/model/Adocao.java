package com.my.adopet_api.model;

import com.my.adopet_api.dto.SalvarAdocaoDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "adocao")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Adocao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne(cascade = CascadeType.PERSIST) // Cascade para salvar Pet automaticamente
    private Pet pet;

    @NotNull
    @ManyToOne(cascade = CascadeType.PERSIST) // Cascade para salvar Abrigo automaticamente
    private Abrigo abrigo;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotBlank
    private String descricao;

    public Adocao(SalvarAdocaoDto dto, Pet pet, Abrigo abrigo) {
        this.pet = pet;
        this.abrigo = abrigo;
        this.status = dto.status();
        this.descricao = dto.descricao();
    }
}
