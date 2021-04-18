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

import static org.openhab.core.io.transport.modbus.ModbusConstants.ValueType.INT16;

import java.math.BigDecimal;

import javax.measure.Unit;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.core.io.transport.modbus.ModbusConstants;
import org.openhab.core.io.transport.modbus.ModbusConstants.ValueType;
import org.openhab.core.library.unit.Units;

/**
 * The {@link Froeling3200Registers} is responsible for defining Modbus registers and their units.
 *
 * @author Frédéric Tobias Christ - Initial contribution
 */
@NonNullByDefault
public enum Froeling3200Registers {
    // the following register numbers are 1-based. They need to be converted before sending them on the wire.
    BOILER_TEMPERATURE(0.5f, 1, INT16, Units.KELVIN),
    EXHAUST_GAS_TEMPERATURE(1, 2, INT16, Units.KELVIN), // only unidirectional meters
    BOARD_TEMPERATURE(1, 3, INT16, Units.KELVIN), // only bidirectional meters
    RESIDUAL_OXYGEN_CONTENT(1, 4, INT16, Units.PERCENT);

    private BigDecimal multiplier;
    private int registerNumber;
    private ModbusConstants.ValueType type;
    private Unit<?> unit;

    private Froeling3200Registers(float multiplier, int registerNumber, ValueType type, Unit<?> unit) {
        this.multiplier = new BigDecimal(multiplier);
        this.registerNumber = registerNumber;
        this.type = type;
        this.unit = unit;
    }

    public Unit<?> getUnit() {
        return unit;
    }

    public BigDecimal getMultiplier() {
        return multiplier;
    }

    public int getRegisterNumber() {
        return registerNumber;
    }

    public ModbusConstants.ValueType getType() {
        return type;
    }
}
