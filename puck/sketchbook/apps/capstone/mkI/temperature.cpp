#include "temperature.h"
#include "pins.h"

bool init_temp(){
  // Todo: check if the temperature sensor returns a sensible value
  if(analogRead(THERM_ANALOG_IN) == 0){
    return false;
  }
  return true;
}

void read_temp(uint16_t &temp){
  temp = analogRead(THERM_ANALOG_IN);
}