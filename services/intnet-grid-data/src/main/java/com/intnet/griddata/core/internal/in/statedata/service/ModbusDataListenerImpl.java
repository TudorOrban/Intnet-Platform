package com.intnet.griddata.core.internal.in.statedata.service;

import com.intnet.griddata.core.internal.in.statedata.dto.ModbusMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ModbusDataListenerImpl implements ModbusDataListener {

    private static final Logger logger = LoggerFactory.getLogger(ModbusDataListenerImpl.class);

    @KafkaListener(topics = "modbus_data", groupId="grid-data-consumer-group")
    public void listen(ModbusMessageDto messageDto) {
        logger.info("Received message: {}", messageDto);
    }
}
