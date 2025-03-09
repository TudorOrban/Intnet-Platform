package com.intnet.admin.features.intnetservice.repository;

import com.intnet.admin.features.intnetservice.model.IntnetService;

import java.util.List;

public interface IntnetServiceRepository {

    List<IntnetService> findAll();
}
