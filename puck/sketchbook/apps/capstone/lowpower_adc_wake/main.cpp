#include <Arduino.h>
#include <LowPower.h>

#define LOW_POWER

#define BLINK_HIGH_MICROSECONDS 1

// The pin that supplies voltage to the analog sensor
#define SENSOR_VCC 5
#define SENSOR_INPUT A0

#define POWER_SYS_PIN 7


void setup(){
	pinMode(SENSOR_VCC, OUTPUT);
	digitalWrite(SENSOR_VCC, LOW);

	pinMode(POWER_SYS_PIN, OUTPUT);
	digitalWrite(POWER_SYS_PIN, LOW);

	Serial.begin(115200);
	Serial.println("Start");
	Serial.flush();
}

int main(){
	init();
	setup();

	while(1){
    LowPower.powerDown(SLEEP_2S, ADC_OFF, BOD_OFF);  
		
		digitalWrite(SENSOR_VCC, HIGH);
		uint16_t val;
		uint16_t flag = 0;
		while((val = analogRead(SENSOR_INPUT)) < 460){
			if(flag == 0){
				// Serial.println("Wakeup!");
				digitalWrite(POWER_SYS_PIN, HIGH);
				flag = 1;
			}
			// Serial.println(val);
			// Serial.flush();
			// delay(100);
		}
		digitalWrite(POWER_SYS_PIN, LOW);
		digitalWrite(SENSOR_VCC, LOW);
	}

	return 0;
}