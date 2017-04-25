#include <Arduino.h>

#define TURB_ANALOG_IN A0
#define THERM_ANALOG_IN A1

#define THERM_PWR 6
#define TURB_PWR 3

void setup(){
  pinMode(THERM_PWR, OUTPUT);
  digitalWrite(THERM_PWR, HIGH);

  pinMode(TURB_PWR, OUTPUT);
  digitalWrite(TURB_PWR, HIGH);

  Serial.begin(115200);
}

uint8_t time_to_sense(){
    static uint32_t last_time = 0;
    const uint32_t delay = 500; // the period
    if(millis() - last_time > delay){
        // last_time = millis();
        last_time += delay;
        return true;
    }
    return false;
}

int main(){
  init();

  setup();

  while(1){
    if(time_to_sense()){
      int16_t therm, turb;
      therm = analogRead(THERM_ANALOG_IN);
      Serial.print(therm);
      Serial.print("  ");
      turb = analogRead(TURB_ANALOG_IN);
      Serial.print(turb);
      Serial.println();
    }

  }

  return 0;
}