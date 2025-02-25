package com.my.adopet_api.model;

import com.my.adopet_api.dto.AtualizarTutorDto;
import com.my.adopet_api.dto.SalvarTutorDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tutor")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Tutor {

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

    public Tutor(SalvarTutorDto dto){
        this.foto = dto.foto();
        this.nome = dto.nome();
        this.telefone = dto.telefone();
        this.cidade = dto.cidade();
        this.sobre = dto.sobre();
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
    }
}
