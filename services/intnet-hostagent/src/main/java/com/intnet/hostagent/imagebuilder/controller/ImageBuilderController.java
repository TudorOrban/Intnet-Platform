package com.intnet.hostagent.imagebuilder.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/build-images")
public class ImageBuilderController {

    @PostMapping
    public ResponseEntity<String> buildImages(@RequestBody List<String> serviceNames) {
        for (String serviceName : serviceNames) {
            try {
                String scriptPath = "../../scripts/build_docker_image.sh";
                ProcessBuilder processBuilder = new ProcessBuilder(scriptPath, serviceName);
                Process process = processBuilder.start();
                process.waitFor();
            } catch (IOException | InterruptedException e) {
                return ResponseEntity.badRequest().body("Error building " + serviceName);
            }
        }
        return ResponseEntity.ok("Builds started");
    }
}
