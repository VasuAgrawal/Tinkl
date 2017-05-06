#include <stdint.h>

void turn_off_radio();
void turn_on_radio();
void powerUp_radio();
bool radio_send(uint8_t *data, uint8_t length);