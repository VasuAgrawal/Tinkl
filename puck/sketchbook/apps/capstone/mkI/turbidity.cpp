#include "turbidity.h"
#include "pins.h"

bool init_turbidity(){
  // Todo: check if the turbidity sensor returns a sensible value
  return true;
}

void read_turbidity(uint16_t &turbidity){
  turbidity = analogRead(TURB_ANALOG_IN);
}