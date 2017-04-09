#include <cstdlib>
#include <iostream>
#include <sstream>
#include <string>
#include <unistd.h>
#include <RF24/RF24.h>
#include <stdint.h>
#include "packet.h"

using namespace std;

void print_packet(const tinkl_packet &p){
  for(uint8_t i=0;i<sizeof(tinkl_packet);i++){
    printf("%d ", ((int8_t*)&p)[i]);
  }
  printf("\n");

  printf("Temp: %u\n", p.temp);
  printf("RGB: %u %u %u\n", p.r, p.g, p.b);
  printf("Turbidity: %u\n", p.turbidity);
  printf("Node: %u\n", p.node_id);
  printf("Packet: %u\n", p.packet_id);
}

// RPi generic:
RF24 radio(22,0);
const uint8_t pipes[][6] = {"tinkl","tinkl"};

/********************************/

int main(int argc, char** argv){

  // Setup and configure rf radio
  radio.begin();

  // optionally, increase the delay between retries & # of retries
  radio.setRetries(15,15);

  // Dump the configuration of the rf unit for debugging
  radio.printDetails();

  radio.openWritingPipe(pipes[0]);
  radio.openReadingPipe(1, pipes[1]);
  radio.startListening();

  while (1){
    if (radio.available()){
      tinkl_packet pkt;

      radio.read(&pkt, sizeof(tinkl_packet));

      radio.stopListening();

      // Echo the packet
      radio.write(&pkt, sizeof(tinkl_packet));

      radio.startListening();

      print_packet(pkt);

      delay(100);
    }
  }

  return 0;
}

