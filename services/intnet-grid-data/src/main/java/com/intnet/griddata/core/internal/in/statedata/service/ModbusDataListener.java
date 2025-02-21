package com.intnet.griddata.core.internal.in.statedata.service;

import com.intnet.griddata.core.internal.in.statedata.dto.ModbusMessageDto;

public interface ModbusDataListener {

    void listen(ModbusMessageDto messageDto);
}
