#include "color.h"
#include "SparkFunISL29125.h"
#include <Arduino.h>

// Declare sensor object
static SFE_ISL29125 RGB_sensor;

bool init_color(){
  return RGB_sensor.init();
}

void turn_on_color(){
  // Return true if init was successful
  RGB_sensor.init();
  // Wait for it to warm up - otherwise, zero readings you will get
  delay(500);
}

void turn_off_color(){
}

void read_color(uint16_t &red, uint16_t &green, uint16_t &blue){
  red   = RGB_sensor.readRed();
  green = RGB_sensor.readGreen();
  blue  = RGB_sensor.readBlue();
}