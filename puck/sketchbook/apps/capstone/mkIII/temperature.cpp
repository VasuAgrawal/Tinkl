#include "temperature.h"
#include "pins.h"

bool init_temp(){
  bool ret;
  pinMode(POWER_THERM_PIN, OUTPUT);

  turn_on_temp();
  // Check if the temperature sensor returns a sensible value
  uint16_t val;
  read_temp(val);
  
  ret = (val == 0 || val == 1023);

  turn_off_temp();
  return ret;
}

void turn_on_temp(){
  digitalWrite(POWER_THERM_PIN, HIGH);
  delay(10);
}

void turn_off_temp(){
  digitalWrite(POWER_THERM_PIN, LOW);
}

void read_temp(uint16_t &temp){
  temp = analogRead(THERM_ANALOG_IN);
}