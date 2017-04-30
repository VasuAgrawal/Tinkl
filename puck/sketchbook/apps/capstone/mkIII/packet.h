#include <stdint.h>
#include <Arduino.h>
// This file should be common to the sender and receiver

struct tinkl_packet{
    uint16_t temp;
    uint16_t r;
    uint16_t g;
    uint16_t b;
    uint16_t turbidity;
    uint32_t node_id;
    uint32_t packet_id;
    uint16_t battery_voltage;
    uint16_t checksum;
    uint8_t last_packet;
}__attribute__((packed));

// The "packed" attribute tells GCC to omit padding between fields
// We don't want to waste space in the radio packets we send by adding padding

void print_packet(const tinkl_packet &pkt){
    Serial.print("Color: ");
    Serial.print(pkt.r);
    Serial.print(" ");
    Serial.print(pkt.g);
    Serial.print(" ");
    Serial.print(pkt.b);
    Serial.println();

    Serial.print("Temp: ");
    Serial.print(pkt.temp);
    Serial.println();

    Serial.print("Turb:");
    Serial.print(pkt.turbidity);
    Serial.println();
}
