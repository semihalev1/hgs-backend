package com.hgs.backend.service;

import com.hgs.backend.dto.GateResponse;
import com.hgs.backend.exception.GateNotFoundException;
import com.hgs.backend.model.Gate;
import com.hgs.backend.repository.GateRepository;
import com.hgs.backend.util.GateHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GateService {

    private final GateRepository gateRepository;
    private final GateHelper gateHelper;

    public Gate getGateEntity(Long id) {
        return gateRepository.findById(id)
                .orElseThrow(() -> new GateNotFoundException(id + " numaralı gişe bulunamadı."));
    }

    public List<GateResponse> getAllGates() {
        return gateRepository.findAllByOrderByNameAsc()
                .stream()
                .map(gateHelper::convertToResponse)
                .toList();
    }

}
