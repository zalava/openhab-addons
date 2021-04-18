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
import org.openhab.binding.modbus.ModbusBindingConstants;
import org.openhab.core.thing.ThingTypeUID;

/**
 * The {@link Froeling3200BindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Frédéric Tobias Christ - Initial contribution
 */
@NonNullByDefault
public class Froeling3200BindingConstants {
    public static final ThingTypeUID THING_TYPE_LAMBDATRONIC_H3200 = new ThingTypeUID(ModbusBindingConstants.BINDING_ID,
            "lambdatronicH3200");
    public static final ThingTypeUID THING_TYPE_LAMBDATRONIC_P3200 = new ThingTypeUID(ModbusBindingConstants.BINDING_ID,
            "lambdatronicP3200");
    public static final ThingTypeUID THING_TYPE_LAMBDATRONIC_S3200 = new ThingTypeUID(ModbusBindingConstants.BINDING_ID,
            "lambdatronicS3200");
    public static final ThingTypeUID THING_TYPE_LAMBDATRONIC_SP3200 = new ThingTypeUID(
            ModbusBindingConstants.BINDING_ID, "lambdatronicSP3200");
}
