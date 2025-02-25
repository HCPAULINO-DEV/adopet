package com.my.adopet_api.model;

import com.my.adopet_api.dto.AtualizarPetDto;
import com.my.adopet_api.dto.SalvarPetDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Entity
@Table(name = "pet")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Raça é obrigatório")
    private String raca;

    @NotBlank(message = "Cor é obrigatório")
    private String cor;

    @NotNull(message = "Idade é obrigatório")
    private int idade;

    @NotNull(message = "Peso é obrigatório")
    private Float peso;

    @ManyToOne(fetch = FetchType.LAZY)
    private Abrigo abrigo;

    @NotNull
    private Boolean adotado;

    public Pet(SalvarPetDto dto, Abrigo abrigo){
        this.nome = dto.nome();
        this.raca = dto.raca();
        this.cor = dto.cor();
        this.idade = dto.idade();
        this.peso = dto.peso();
        this.abrigo = abrigo;
        this.adotado = dto.adotado();
    }

    public void atualizarPet(AtualizarPetDto dto, Abrigo abrigo) {
        if (dto.nome() != null) {
            this.nome = dto.nome();
        }
        if (dto.raca() != null) {
            this.raca = dto.raca();
        }
        if (dto.cor() != null) {
            this.cor = dto.cor();
        }
        if (dto.idade() != 0) {
            this.idade = dto.idade();
        }
        if (dto.peso() != null) {
            this.peso = dto.peso();
        }
        if (dto.abrigo() != null) {
            this.abrigo = abrigo;
        }
        if (dto.adotado() != null){
            this.adotado = dto.adotado();
        }
    }

    public void setAdotadoTrue(){
        setAdotado(true);

    }

}
