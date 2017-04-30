#include "battery.h"
#include "pins.h"

void read_battery(uint16_t &voltage){
  voltage = analogRead(BATTERY_ANALOG_IN);
}
