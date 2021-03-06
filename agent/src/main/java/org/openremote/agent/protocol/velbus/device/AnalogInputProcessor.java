/*
 * Copyright 2017, OpenRemote Inc.
 *
 * See the CONTRIBUTORS.txt file in the distribution for a
 * full listing of individual contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.openremote.agent.protocol.velbus.device;

import org.openremote.agent.protocol.velbus.VelbusPacket;
import org.openremote.model.attribute.AttributeValueType;
import org.openremote.model.util.EnumUtil;
import org.openremote.model.value.Value;
import org.openremote.model.value.ValueType;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AnalogInputProcessor extends FeatureProcessor {

    public enum SensorType implements DevicePropertyValue<SensorType> {
        VOLTAGE(0x00, 0.00025),
        CURRENT(0x01, 0.000005),
        RESISTANCE(0x02, 0.25),
        PERIOD(0x03, 0.0000005);

        private int code;
        private double resolution;

        SensorType(int code, double resolution) {
            this.code = code;
            this.resolution = resolution;
        }

        public double getResolution() {
            return resolution;
        }

        public int getCode() {
            return this.code;
        }

        public static SensorType fromCode(int code) {
            for (SensorType type : SensorType.values()) {
                if (type.getCode() == code) {
                    return type;
                }
            }

            return VOLTAGE;
        }

        @Override
        public Value toValue(ValueType valueType) {
            return EnumUtil.enumToValue(this, valueType);
        }

        @Override
        public SensorType getPropertyValue() {
            return this;
        }
    }

    public enum SensorMode implements DevicePropertyValue<SensorMode> {
        SAFE(0x00),
        NIGHT(0x10),
        DAY(0x20),
        COMFORT(0x30);

        private int code;

        SensorMode(int code) {
            this.code = code;
        }

        public int getCode() {
            return this.code;
        }

        public static SensorMode fromCode(int code) {
            for (SensorMode type : SensorMode.values()) {
                if (type.getCode() == code) {
                    return type;
                }
            }

            return SAFE;
        }

        @Override
        public Value toValue(ValueType valueType) {
            return EnumUtil.enumToValue(this, valueType);
        }

        @Override
        public SensorMode getPropertyValue() {
            return this;
        }
    }

    protected static final String EmptySensorText = "               ";

    protected static final List<PropertyDescriptor> METEO_PROPERTIES = Arrays.asList(
        new PropertyDescriptor("rainfall", "Rainfall", "RAINFALL", AttributeValueType.RAINFALL, true),
        new PropertyDescriptor("lightLevel", "Light Level", "LIGHT", AttributeValueType.BRIGHTNESS, true),
        new PropertyDescriptor("windSpeed", "Wind Speed", "WIND", AttributeValueType.SPEED, true)
    );

    protected static final List<PropertyDescriptor> IO_PROPERTIES = Arrays.asList(
        new PropertyDescriptor("sensor1", "Sensor 1", "SENSOR1", AttributeValueType.NUMBER, true),
        new PropertyDescriptor("sensor2", "Sensor 2", "SENSOR2", AttributeValueType.NUMBER, true),
        new PropertyDescriptor("sensor3", "Sensor 3", "SENSOR3", AttributeValueType.NUMBER, true),
        new PropertyDescriptor("sensor4", "Sensor 4", "SENSOR4", AttributeValueType.NUMBER, true),
        new PropertyDescriptor("sensor1Text", "Sensor 1 Text", "SENSOR1_TEXT", AttributeValueType.STRING, true),
        new PropertyDescriptor("sensor2Text", "Sensor 2 Text", "SENSOR2_TEXT", AttributeValueType.STRING, true),
        new PropertyDescriptor("sensor3Text", "Sensor 3 Text", "SENSOR3_TEXT", AttributeValueType.STRING, true),
        new PropertyDescriptor("sensor4Text", "Sensor 4 Text", "SENSOR4_TEXT", AttributeValueType.STRING, true),
        new PropertyDescriptor("sensor1Type", "Sensor 1 Type", "SENSOR1_TYPE", AttributeValueType.STRING, true),
        new PropertyDescriptor("sensor2Type", "Sensor 2 Type", "SENSOR2_TYPE", AttributeValueType.STRING, true),
        new PropertyDescriptor("sensor3Type", "Sensor 3 Type", "SENSOR3_TYPE", AttributeValueType.STRING, true),
        new PropertyDescriptor("sensor4Type", "Sensor 4 Type", "SENSOR4_TYPE", AttributeValueType.STRING, true),
        new PropertyDescriptor("sensor1Mode", "Sensor 1 Mode", "SENSOR1_MODE", AttributeValueType.STRING, true),
        new PropertyDescriptor("sensor2Mode", "Sensor 2 Mode", "SENSOR2_MODE", AttributeValueType.STRING, true),
        new PropertyDescriptor("sensor3Mode", "Sensor 3 Mode", "SENSOR3_MODE", AttributeValueType.STRING, true),
        new PropertyDescriptor("sensor4Mode", "Sensor 4 Mode", "SENSOR4_MODE", AttributeValueType.STRING, true)
    );

    @Override
    public List<PropertyDescriptor> getPropertyDescriptors(VelbusDeviceType deviceType) {
        return deviceType == VelbusDeviceType.VMBMETEO ? METEO_PROPERTIES : IO_PROPERTIES;
    }

    @Override
    public List<VelbusPacket> getStatusRequestPackets(VelbusDevice device) {

        if (device.getDeviceType() == VelbusDeviceType.VMBMETEO) {
            return Collections.singletonList(
                new VelbusPacket(device.getBaseAddress(), VelbusPacket.OutboundCommand.SENSOR_READOUT.getCode(), (byte) 0x0F, (byte) 0x00)
            );
        }

        if (device.getDeviceType() == VelbusDeviceType.VMB4AN) {

            // Initialise the text char arrays for sensors
            device.setProperty("SENSOR1_TEXT", new StringDevicePropertyValue(EmptySensorText));
            device.setProperty("SENSOR2_TEXT", new StringDevicePropertyValue(EmptySensorText));
            device.setProperty("SENSOR3_TEXT", new StringDevicePropertyValue(EmptySensorText));
            device.setProperty("SENSOR4_TEXT", new StringDevicePropertyValue(EmptySensorText));

            return Arrays.asList(
                new VelbusPacket(device.getBaseAddress(), VelbusPacket.OutboundCommand.SENSOR_READOUT.getCode(), (byte) 0x08, (byte) 0x00),
                new VelbusPacket(device.getBaseAddress(), VelbusPacket.OutboundCommand.SENSOR_READOUT.getCode(), (byte) 0x09, (byte) 0x00),
                new VelbusPacket(device.getBaseAddress(), VelbusPacket.OutboundCommand.SENSOR_READOUT.getCode(), (byte) 0x0A, (byte) 0x00),
                new VelbusPacket(device.getBaseAddress(), VelbusPacket.OutboundCommand.SENSOR_READOUT.getCode(), (byte) 0x0B, (byte) 0x00),
                new VelbusPacket(device.getBaseAddress(), VelbusPacket.OutboundCommand.SENSOR_SETTINGS.getCode(), (byte) 0x08),
                new VelbusPacket(device.getBaseAddress(), VelbusPacket.OutboundCommand.SENSOR_SETTINGS.getCode(), (byte) 0x09),
                new VelbusPacket(device.getBaseAddress(), VelbusPacket.OutboundCommand.SENSOR_SETTINGS.getCode(), (byte) 0x0A),
                new VelbusPacket(device.getBaseAddress(), VelbusPacket.OutboundCommand.SENSOR_SETTINGS.getCode(), (byte) 0x0B)
            );
        }

        return null;
    }

    @Override
    public List<VelbusPacket> getPropertyWritePackets(VelbusDevice device, String property, Value value) {
        return null;
    }

    @Override
    public boolean processReceivedPacket(VelbusDevice device, VelbusPacket packet) {
        VelbusPacket.InboundCommand packetCommand = VelbusPacket.InboundCommand.fromCode(packet.getCommand());

        switch (packetCommand) {
            case SENSOR_STATUS:
                if (device.getDeviceType() == VelbusDeviceType.VMB4AN) {
                    int channel = (packet.getByte(1) & 0xFF) - 7;
                    SensorType type = SensorType.fromCode((packet.getByte(2) & 0x03));
                    SensorMode mode = SensorMode.fromCode((packet.getByte(2) & 0x30));

                    String sensorName = "SENSOR" + channel;

                    device.setProperty(sensorName + "_TYPE", type);
                    device.setProperty(sensorName + "_MODE", mode);
                    return true;
                }
                break;
            case RAW_SENSOR_STATUS:
                if (device.getDeviceType() == VelbusDeviceType.VMBMETEO) {
                    int rainValue = Math.abs((packet.getByte(1) << 8) + packet.getByte(2));
                    int light = Math.abs((packet.getByte(3) << 8) + packet.getByte(4));
                    int windValue = Math.abs((packet.getByte(5) << 8) + packet.getByte(6));
                    double wind = 0.1 * windValue;
                    double rain = 0.1 * rainValue;
                    device.setProperty("RAINFALL", new DoubleDevicePropertyValue(rain));
                    device.setProperty("LIGHT", new DoubleDevicePropertyValue(light));
                    device.setProperty("WIND", new DoubleDevicePropertyValue(wind));
                    return true;
                }

                if (device.getDeviceType() == VelbusDeviceType.VMB4AN) {
                    int channel = (packet.getByte(1) & 0xFF) - 7;
                    SensorType type = SensorType.fromCode((packet.getByte(2) & 0xFF));
                    double value = ((packet.getByte(3) & 0xFF) << 16) + ((packet.getByte(4) & 0xFF) << 8) + ((packet.getByte(5) & 0xFF));
                    value = type.getResolution() * value;
                    String sensorName = "SENSOR" + channel;

                    device.setProperty(sensorName, new DoubleDevicePropertyValue(value));
                    device.setProperty(sensorName + "_TYPE", type);
                    return true;
                }
                break;
            case RAW_SENSOR_TEXT_STATUS:
                if (device.getDeviceType() == VelbusDeviceType.VMB4AN) {
                    int channel = (packet.getByte(1) & 0xFF) - 7;
                    String sensorText = (String)device.getPropertyValue("SENSOR" + channel + "_TEXT").getPropertyValue();
                    try {
                        byte[] textBytes = sensorText.getBytes(StandardCharsets. ISO_8859_1);
                        int pos = packet.getByte(2) & 0xFF;

                        for (int i=0; i<5; i++) {
                            int c = packet.getByte(3+i) & 0xFF;
                            if (c == 0) {
                                // NULL char indicates end
                                for (int j=pos+i; j<15; j++) {
                                    textBytes[j] = 0x20; // WHITESPACE
                                }
                                break;
                            }
                            textBytes[pos+i] = (byte)c;
                        }
                        sensorText = new String(textBytes, StandardCharsets.ISO_8859_1);
                    } catch (Exception e) {
                        sensorText = EmptySensorText;
                    }

                    device.setProperty("SENSOR" + channel + "_TEXT", new StringDevicePropertyValue(sensorText));
                    return true;
                }
        }

        return false;
    }
}
