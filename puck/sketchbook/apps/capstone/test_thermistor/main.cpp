#include <Arduino.h>
#include <Wire.h>
#include  <MCP342X.h>


// ADC object
MCP342X MCP_ADC;

void setup(){
  pinMode(6, OUTPUT);
  digitalWrite(6, HIGH);

  delay(500);

  Wire.begin();  // join I2C bus
  TWBR = 12;  // 400 kHz (maximum)

  Serial.begin(115200);

  // Serial.println("Starting up");
  // Serial.println("Testing device connection...");
  //   Serial.println(MCP_ADC.testConnection() ? "MCP342X connection successful" : "MCP342X connection failed");
    
  // MCP_ADC.configure(MCP342X_MODE_CONTINUOUS |
  //                   MCP342X_CHANNEL_1 |
  //                   MCP342X_SIZE_16BIT |
  //                   MCP342X_GAIN_1X
  //                );

  // Serial.println(MCP_ADC.getConfigRegShdw(), HEX);

}

float raw_to_voltage(int16_t adc_reading){
  // Assumes we are in 16-bit mode
  // return (float)adc_reading * 2.048 / (float)(0x7FFF);
  return (float)adc_reading * 2.048 / (float)(0x8000);
  // return (float)adc_reading * 0.0000625;
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
      // Serial.print(millis());

      // Serial.print(", ");

      static int16_t result;

      int16_t inbuilt_adc;
      inbuilt_adc = analogRead(A0);

      Serial.print(inbuilt_adc);

      // Serial.print(", ");
      // Serial.print((float)inbuilt_adc * 3.3875 / 1023.0);
      
      // MCP_ADC.startConversion();
      // MCP_ADC.getResult(&result);

      // Serial.print(result);    


      Serial.println();
    }

  }

  return 0;
}