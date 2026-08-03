package com.hgs.backend.service;

import com.hgs.backend.dto.gate.GateResponse;
import com.hgs.backend.mapper.GateMapper;
import com.hgs.backend.exception.GateNotFoundException;
import com.hgs.backend.model.Gate;
import com.hgs.backend.repository.GateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GateService {

    private final GateRepository gateRepository;
    private final GateMapper gateMapper;

    public Gate getGateEntity(Long id) {
        return gateRepository.findById(id)
                .orElseThrow(() -> new GateNotFoundException(id + " numaralı gişe bulunamadı."));
    }

    public List<GateResponse> getAllGates() {
        return gateRepository.findAllByOrderByNameAsc()
                .stream()
                .map(gateMapper::convertToResponse)
                .toList();
    }

}
