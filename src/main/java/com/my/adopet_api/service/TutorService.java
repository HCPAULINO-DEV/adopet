package com.my.adopet_api.service;

import com.my.adopet_api.dto.InformarTutorDto;
import com.my.adopet_api.model.Tutor;
import com.my.adopet_api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TutorService {

    @Autowired
    private TutorRepository tutorRepository;

    public Tutor buscarTutorPorId(Long id){
        return tutorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tutor não encontrado!"));
    }

    public Page<InformarTutorDto> buscarTutores(Pageable pageable){
        Page<Tutor> page = tutorRepository.findAll(pageable);
        Page<InformarTutorDto> dto = page.map(InformarTutorDto::new);

        if (page.isEmpty()){
            throw new RuntimeException("Nenhum tutor foi encontrado!");
        }

        return dto;
    }
}
