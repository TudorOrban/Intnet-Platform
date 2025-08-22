package com.intnet.hostagent.imagebuilder.controller;

import com.intnet.hostagent.shared.types.IntnetService;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/*
 * Controller responsible for building Docker images for Intnet services
 */
@RestController
@RequestMapping("api/v1/build-images")
public class ImageBuilderController {


    @PostMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<Flux<DataBuffer>> buildImages(@RequestBody List<IntnetService> services) {
        System.out.println(services);
        Flux<DataBuffer> logStream = Flux.fromIterable(services)
                .flatMap(service -> Flux.create(sink -> {
                    System.out.println(service.getName());
                    try {
                        String scriptPath = "scripts/build_docker_image.sh";
                        ProcessBuilder processBuilder = new ProcessBuilder(scriptPath, service.getName(), service.getDockerfilePath(), service.getServiceBuildType().toString(), service.getImageName());
                        Process process = processBuilder.start();

                        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                        String line;
                        while ((line = reader.readLine()) != null) {
                            sink.next(line);
                        }
                        while ((line = errorReader.readLine()) != null) {
                            sink.next(line);
                        }

                        process.waitFor();
                        sink.next("serviceName: " + service.getName() + ", Build process finished");
                        sink.complete();
                    } catch (IOException | InterruptedException e) {
                        sink.next("serviceName: " + service.getName() + ", Error building " + service.getName() + ", Error: " + e.getMessage());
                        sink.complete();
                    }
                }))
                .subscribeOn(Schedulers.boundedElastic())
                .map(logLine -> {
                    String sseFormattedLog = String.format("data: %s\n\n", logLine);
                    return DefaultDataBufferFactory.sharedInstance.wrap(sseFormattedLog.getBytes());
                });

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(logStream);
    }
}
