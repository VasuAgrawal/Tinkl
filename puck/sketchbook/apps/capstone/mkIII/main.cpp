// This is the firmware for the mkIII puck

// Among other things it features power-gating

/**********************************************************/


// Pin definitions
#include "pins.h"

// Radio - nRF24L01+
#include "radio.h"
#include "packet.h"

// Sensors
#include "color.h"
#include "temperature.h"
#include "turbidity.h"
#include "battery.h"

// Sleep
#include <LowPower.h>

/**********************************************************/

#define WAKE_UP_ABSOLUTE_THRESHOLD 490

/**********************************************************/

#define MAX_SAMPLES 30

/**********************************************************/

// Set NODE_ID to a default value if it was not specified with a compiler flag
#ifndef NODE_ID
    #define NODE_ID 1
#endif

/**********************************************************/

/**********************************************************/

#define SAMPLE_PERIOD 500 // milliseconds

/**********************************************************/

void turn_on_everything(){
    digitalWrite(POWER_SYS_PIN, HIGH);

    turn_on_radio();
    turn_on_temp();
    turn_on_turbidity();
    turn_on_color();
}

void turn_off_everything(){
    turn_off_color();
    turn_off_radio();
    turn_off_temp();
    turn_off_turbidity();

    digitalWrite(POWER_SYS_PIN, LOW);
}

void setup() {
    Serial.begin(115200);
    Serial.println("Starting!");

    pinMode(POWER_SYS_PIN, OUTPUT);
    digitalWrite(POWER_SYS_PIN, HIGH);

    Serial.println("0");


    // Initialize sensors
    if(init_color() == false) Serial.println("Failed to init color sensor"); 

    Serial.println("1");

    if(init_temp() == false) Serial.println("Thermistor not present");   

    Serial.println("2");


    if(init_turbidity() == false) Serial.println("Failed to init turbidity sensor");


    Serial.println("All sensors initialized");

    // Initialize radio
    turn_on_radio();
    Serial.println("Radio initialized");

    turn_off_everything();    
}

tinkl_packet* make_packet(){
    static uint32_t packet_id = 0;
    static tinkl_packet pkt;
    pkt.packet_id = packet_id++;
    pkt.node_id = NODE_ID;
    read_temp(pkt.temp);
    read_color(pkt.r, pkt.g, pkt.b);
    read_turbidity(pkt.turbidity);
    read_battery(pkt.battery_voltage);
    return &pkt;
}

void wake_up(){
    turn_on_everything();
    for(int sc=0;sc<MAX_SAMPLES;sc++){
        // Make a packet!
        tinkl_packet *pkt = make_packet();
        print_packet(*pkt);
        // Set the "last sample" flag
        pkt->last_packet = (sc + 1 == MAX_SAMPLES);
        radio_send((uint8_t*) pkt, sizeof(tinkl_packet));
        delay(SAMPLE_PERIOD);    
    }
    Serial.println("DONE");
    turn_off_everything();
}

uint8_t pressed(){
    uint8_t pflag = 0;
    while(Serial.available()){ // empty the serial buffer
        pflag = 1;
        Serial.read();
    }
    return pflag;
}

int main(){
    init();
    setup();

    int i=0;
    while(1){
        LowPower.powerDown(SLEEP_250MS, ADC_OFF, BOD_OFF);  
 
        // Serial.begin(115200);

        // Serial.flush();

        i++;
        // Serial.println("Hi");
        // Serial.println(i);

        // delay(250);

        uint16_t cur_temp;
        turn_on_temp();
        read_temp(cur_temp);
        turn_off_temp();
 
        Serial.println(cur_temp);
        Serial.flush();

        // Check if it's time to wake up
        if(
        cur_temp < WAKE_UP_ABSOLUTE_THRESHOLD
        || pressed()
        ){
            wake_up();

            // Impose a 20-second timeout between separate urinations
        //     LowPower.powerDown(SLEEP_8S, ADC_OFF, BOD_OFF);  
        //     LowPower.powerDown(SLEEP_4S, ADC_OFF, BOD_OFF);  
        //     LowPower.powerDown(SLEEP_8S, ADC_OFF, BOD_OFF);  
        }
    }
    return 0;
}