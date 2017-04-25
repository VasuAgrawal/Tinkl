/*
 * Testing the SparkFunISL29125 color sensor library
 */

#include <Arduino.h>
#include "Wire.h"
#include "SparkFunISL29125.h"

// Declare sensor object
SFE_ISL29125 RGB_sensor;

void setup() {
    pinMode(10, OUTPUT);
    Serial.begin(115200);
}

int main(){
    init();
    setup();

    while(1){
        digitalWrite(10, HIGH);
        // Initialize the ISL29125 with simple configuration so it starts sampling
        if (RGB_sensor.init()){
            Serial.println("Sensor Init Successful");
        }
        else{
            Serial.println("Sensor Init Unsuccessful");
        }

        for(int i=0;i<5;i++){
            uint16_t red   = RGB_sensor.readRed();
            uint16_t green = RGB_sensor.readGreen();
            uint16_t blue  = RGB_sensor.readBlue();

            Serial.print(red);
            Serial.print(" ");
            Serial.print(green);
            Serial.print(" ");
            Serial.print(blue);
            Serial.println();
            delay(500);
        }

        digitalWrite(10, LOW);

        delay(100);
    }
    return 0;
}