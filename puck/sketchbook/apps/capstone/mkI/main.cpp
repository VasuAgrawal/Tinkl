// This program reads the sensors on the puck and transmits a packet to the hub.

// This version makes no attempt to enter low-power mode

/**********************************************************/

// Radio - nRF24L01+
#include <SPI.h>
#include "RF24.h"
#include "Wire.h"
#include "packet.h"

#include "pins.h"

// Sensors
#include "color.h"
#include "temperature.h"
#include "turbidity.h"

/**********************************************************/

#define WAKE_UP_DELTA_THRESHOLD 10
#define WAKE_UP_ABSOLUTE_THRESHOLD 520

#define MS_BETWEEN_URINATIONS 10000 // milliseconds

/**********************************************************/

#define MAX_SAMPLES 10

/**********************************************************/

// Set NODE_ID to a default value if it was not specified with a compiler flag
#ifndef NODE_ID
    #define NODE_ID 1
#endif

/**********************************************************/

#define RXNAME "tinkl"
#define TXNAME "tinkl"
#define MAX_PAYLOAD_LEN 32

RF24 radio(9,10); //(ce,csn)

/**********************************************************/

#define SAMPLE_PERIOD 500 // milliseconds

/**********************************************************/
void setup() {
    pinMode(POWER_THERM_PIN, OUTPUT);
    pinMode(POWER_TURB_PIN, OUTPUT);
    pinMode(POWER_COLOR_PIN, OUTPUT);
    pinMode(POWER_RADIO_PIN, OUTPUT);
    digitalWrite(POWER_THERM_PIN, HIGH);
    digitalWrite(POWER_TURB_PIN, HIGH);
    digitalWrite(POWER_COLOR_PIN, HIGH);
    digitalWrite(POWER_RADIO_PIN, HIGH);

    Serial.begin(115200);
    Serial.println("Starting!");

    delay(100);

    // Initialize sensors
    if(init_color() == false) Serial.println("Failed to init color sensor");   
    if(init_temp() == false) Serial.println("Thermistor not present");   
    if(init_turbidity() == false) Serial.println("Failed to init turbidity sensor");

    Serial.println("All sensors initialized");

    // Initialize radio
    radio.begin();
    radio.setPALevel(RF24_PA_LOW);
    radio.openWritingPipe((byte *) TXNAME);
    radio.openReadingPipe(1, (byte *) RXNAME);
    radio.startListening();

    Serial.println("Radio initialized");
}

void read_battery(uint16_t &voltage){
    voltage = 0; // mkI does not have battery reading ability
}

// Todo
void write_checksum(tinkl_packet &pkt){
    pkt.checksum = 0;
}

tinkl_packet* make_packet(){
    static tinkl_packet pkt;
    
    // Identify the source with our node ID
    pkt.node_id = NODE_ID;

    // Assign a new packet ID
    static uint32_t packet_id = 0;
    pkt.packet_id = packet_id++;

    // Populate the sensor-data fields with RAW sensor readings
    // Although these are raw values, we can convert them to physical units
    // at the back-end. This lets us avoid storing calibration constants
    // on the puck.
    read_temp(pkt.temp);
    read_color(pkt.r, pkt.g, pkt.b);
    read_turbidity(pkt.turbidity);
    read_battery(pkt.battery_voltage);

    write_checksum(pkt);
    return &pkt;
}

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

uint8_t send(uint8_t *data, uint8_t length){
    if(length > MAX_PAYLOAD_LEN) return 1;
    radio.stopListening();
    uint8_t ret_code = radio.write(data, length);
    radio.startListening();
    return (ret_code == 0); // if ret_code is 0, return 1 to signal an error
}

uint8_t pressed(){
    uint8_t pflag = 0;
    while(Serial.available()){ // empty the serial buffer
        pflag = 1;
        Serial.read();
    }
    return pflag;
}

void wake_up(){
    int sc;
    for(sc=0;sc<MAX_SAMPLES;sc++){
        // Make a packet!
        tinkl_packet *pkt = make_packet();
        print_packet(*pkt);

        // Set the "last sample" flag
        pkt->last_packet = (sc + 1 == MAX_SAMPLES);

        // Send it!
        Serial.print("Sending... ");
        uint8_t result = send((uint8_t*) pkt, sizeof(tinkl_packet));

        // Did sending succeed?
        Serial.println(result ? "failed :(" : "success :)");
        delay(500);    
    }

    Serial.println("DONE");
}

int main(){
    init();
    setup();

    int16_t prev_temp = analogRead(THERM_ANALOG_IN);

    while(1){
        delay(250);

        int16_t cur_temp = analogRead(THERM_ANALOG_IN);
 
        Serial.print("Cur temp: ");
        Serial.println(cur_temp);
        Serial.print("Prev temp: ");
        Serial.println(prev_temp);

        // Check if it's time to wake up
        if(cur_temp - prev_temp > WAKE_UP_DELTA_THRESHOLD 
        || prev_temp - cur_temp > WAKE_UP_DELTA_THRESHOLD 
        || cur_temp > WAKE_UP_ABSOLUTE_THRESHOLD
        || pressed()
        ){
            wake_up();
            delay(MS_BETWEEN_URINATIONS);
        }
        prev_temp = analogRead(THERM_ANALOG_IN);
    }
    return 0;
}