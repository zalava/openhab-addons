/**
 * Copyright (c) 2010-2021 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.modbus.froeling3200.internal;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.modbus.handler.BaseModbusThingHandler;
import org.openhab.core.io.transport.modbus.AsyncModbusFailure;
import org.openhab.core.io.transport.modbus.AsyncModbusReadResult;
import org.openhab.core.io.transport.modbus.ModbusBitUtilities;
import org.openhab.core.io.transport.modbus.ModbusReadFunctionCode;
import org.openhab.core.io.transport.modbus.ModbusReadRequestBlueprint;
import org.openhab.core.library.types.QuantityType;
import org.openhab.core.thing.ChannelUID;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingStatus;
import org.openhab.core.thing.ThingStatusDetail;
import org.openhab.core.types.Command;

/**
 * The {@link Froeling3200Handler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Frédéric Tobias Christ - Initial contribution
 *
 */
@NonNullByDefault
public class Froeling3200Handler extends BaseModbusThingHandler {
    Froeling3200Configuration config = new Froeling3200Configuration();
    private static final int FIRST_READ_REGISTER = 1;
    private static final int READ_LENGTH = 4;
    private static final int TRIES = 3;
    private @Nullable ModbusReadRequestBlueprint blueprint;

    public Froeling3200Handler(Thing thing) {
        super(thing);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        // TODO Auto-generated method stub
    }

    @Override
    public void modbusInitialize() {
        config = getConfigAs(Froeling3200Configuration.class);
        if (config.pollInterval <= 0) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
                    "Invalid poll interval: " + config.pollInterval);
            return;
        }
        ModbusReadRequestBlueprint localBlueprint = blueprint = new ModbusReadRequestBlueprint(getSlaveId(),
                ModbusReadFunctionCode.READ_INPUT_REGISTERS, FIRST_READ_REGISTER - 1, READ_LENGTH, TRIES);

        updateStatus(ThingStatus.UNKNOWN);

        registerRegularPoll(localBlueprint, config.pollInterval, 0, this::readSuccessful, this::readError);
    }

    private void readSuccessful(AsyncModbusReadResult result) {
        result.getRegisters().ifPresent(registers -> {
            if (getThing().getStatus() != ThingStatus.ONLINE) {
                updateStatus(ThingStatus.ONLINE);
            }

            for (Froeling3200Registers channel : Froeling3200Registers.values()) {
                int index = channel.getRegisterNumber() - FIRST_READ_REGISTER;

                ModbusBitUtilities.extractStateFromRegisters(registers, index, channel.getType())
                        .map(d -> d.toBigDecimal().multiply(channel.getMultiplier()))
                        .map(bigDecimal -> new QuantityType<>(bigDecimal, channel.getUnit()))
                        .ifPresent(v -> updateState(createChannelUid(channel), v));

            }

        });
    }

    private void readError(AsyncModbusFailure<ModbusReadRequestBlueprint> error) {
        updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
                "Failed to retrieve data: " + error.getCause().getMessage());
    }

    private ChannelUID createChannelUid(Froeling3200Registers channel) {
        return new ChannelUID(thing.getUID(), channel.toString().toLowerCase());
    }
}
