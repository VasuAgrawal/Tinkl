#include "turbidity.h"
#include "pins.h"

bool init_turbidity(){
  return true;
}

void read_turbidity(uint16_t &turbidity){
  turbidity = analogRead(TURB_ANALOG_IN);
}

void turn_on_turbidity(){
}
void turn_off_turbidity(){
}