package com.my.adopet_api.model;

import com.my.adopet_api.dto.AtualizarAbrigoDto;
import com.my.adopet_api.dto.SalvarAbrigoDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "abrigo")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Abrigo {

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

    private LocalDate localDate;

    public Abrigo(SalvarAbrigoDto dto){
        this.cnpj = dto.cnpj();
        this.nome = dto.nome();
        this.endereco = dto.endereco();
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
}
