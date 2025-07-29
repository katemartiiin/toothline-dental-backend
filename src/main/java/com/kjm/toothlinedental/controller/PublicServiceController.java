package com.kjm.toothlinedental.controller;

import com.kjm.toothlinedental.common.ApiResponse;
import com.kjm.toothlinedental.dto.service.ServiceResponseDto;
import com.kjm.toothlinedental.service.ProcedureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class PublicServiceController {

    private final ProcedureService procedureService;

    public PublicServiceController (ProcedureService procedureService) {
        this.procedureService = procedureService;
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<ServiceResponseDto>>> getAllServicesPublic() {
        String name = "";
        var data = procedureService.getAllServices(name);
        return ResponseEntity.ok(new ApiResponse<>("Services fetched successfully", data));
    }
}
