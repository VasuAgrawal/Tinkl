#include <SPI.h>
#include "RF24.h"
#include "Wire.h"
#include <Arduino.h>

#define RXNAME "tinkl"
#define TXNAME "tinkl"
#define MAX_PAYLOAD_LEN 32

static RF24 radio(9,10); //(ce,csn)

void turn_on_radio(){
  // Initialize radio
  radio.begin();
  radio.setPALevel(RF24_PA_LOW);
  radio.openWritingPipe((byte *) TXNAME);
  radio.openReadingPipe(1, (byte *) RXNAME);
}

void turn_off_radio(){
  radio.flush_tx();
  radio.powerDown();
}


void powerUp_radio(){
  radio.powerUp();
}

bool radio_send(uint8_t *data, uint8_t length){
    if(length > MAX_PAYLOAD_LEN) return 1;

    Serial.print("Sending... ");

    // radio.stopListening();
    uint8_t ret_code = radio.write(data, length);
    // radio.startListening();

    // Did sending succeed?
    Serial.println(ret_code == 0 ? "failed :(" : "success :)");

    return (ret_code == 0); // if ret_code is 0, return 1 to signal an error
}
