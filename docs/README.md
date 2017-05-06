# Tinkl

[Insert tinkl logo here]

# Concept
Tinkl is a health monitoring system spread out across the city that
uses information from lavatory usage to allow individuals to keep tabs on their
health on the go, and to allow the city to be apprised of the collective health
of the city's population.

Our device integrates color, temperature, and turbidity sensors into an
unobtrusive package that can operate in the messy environment of a urinal,
sensing key properties of urine and transmitting an analysis of the data to
individuals on demand, as well as to city authorities for centralized
monitoring of a city's collective health.

# Motivation 

Currently, individuals do not have easy access to basic measures of their
health such as temperature on the go; at the present moment, cities do not also
have an effective means to track with frequency, the collective health of the
city's population.

Our project aims to remedy this, by creating a small and cost-effective package
that can be unobtrusively distributed around a city's many lavatories, and
which can sense a person's health markers from a trip to the lavatory, making
it easy for a person to keep tabs on his health anywhere and anytime, and
allowing for the city's collective health to be monitored in real time.

# Requirements

## Functional * Urinal puck is capable of collecting urine and analyzing its
color, clarity and temperature * Collection mechanism capable of preventing
cross-contamination of urine samples across different individuals * System is
capable of predicting basic health markers like hydration level and basal body
temperature from collected data * System provides means for individuals to log
when they are using a urinal and access health data on demand via a smartphone
app * System stores anonymized collected data on central server, visible only
to city authorities for city-wide health monitoring

## Nonfunctional * Urinal puck should be small and waterproofed (up to IP65
rating) * Urinal puck should be unobtrusive, preventing user discomfort *
Urinal puck should last ~2 weeks - 1 month on a battery

# Use Cases Our systems are designed to allow people to increase awareness of
their health, by enhancing the accessibility of key health markers available in
urine. We envision several possible use cases for this system: Similar to
Hydralert's concept, our system can enhance workplace safety. In industries
necessitating outdoor activity, this can allow workers to be aware of markers
such as their body temperature and hydration levels, and adjust their water
intake accordingly to ensure that they do not suffer the adverse effects of
heat stroke or other heat and hydration-related ailments. Employers will be
able to monitor their employees overall hydration and health markers, and
respond accordingly to encourage better employee health and safety while on the
job.  Our system also provides a means for individuals to be more cognizant of
their health. In urban areas where our system is ubiquitous, an individual's
health information can be easily analyzed, and will be collated and made
available in an easily understandable format at the touch of a button.


# Tech Specs

## Hardware

Raspberry Pi 3 Model B (ARMv8, 1GB RAM) - Adafruit, $39.95 RGB light sensor
breakout board ISL29125 - Sparkfun, $7.95 Infrared thermopile sensor breakout
TMP006 - Adafruit, $9.95 NTC thermistor - Digikey, $2.26 Analog turbidity
sensor SEN0189 - DFRobot, $9.90 nRF24L01+ Enhanced Wireless Module - GearBest,
$0.99 ATMega328P microcontroller - Mouser, $2.26 MCP1700 low quiescent current
regulator - Mouser, $0.40 MCP3426 16-bit ADC - Mouser, $2.84 MIC5219 500mA-peak
LDO regulator - Mouser, $1.26 1.5V Alkaline AA batteries (4x per puck) -
Amazon, $2.30 Custom-designed PCB combining sensing, comms and MCU
Custom-designed, 3D-printed plastic puck

## Software

Open-EMR - open source electronic health/medical records-keeping system. Can be
connected to a MySQL server that stores collected health data.  MySQL - open
source database technology that can be used to store collected health data.

## Protocols

Data format we have developed, to be used by puck to communicate with Raspberry
Pi hub

# Competitive Analysis

## Underworlds - project by MIT's Senseable City Lab

Research project analyzing wastewater and sewage for biomarkers of human
health, with the goal of massively collecting data and using ananalytics to
allow the health of the city to be monitored, from the scale of a neighborhood
to the aggregate health of the entire city.

While this project aims to enable city-wide health monitoring through
large-scale, city-wide data collection and analytics just like the Underworlds
project, we also aim to provide individuals with the ability to have their
health assessed on demand, by providing an interface through which they can log
their lavatory usage and access their collected health data.

## Toto Intelligence Toilet

A smart toilet designed by Japanese toilet-maker Toto, that features a "sample
catcher" capable of obtaining urine samples, and which can track your urine
temperature, analyze glucose levels and transmit the information to your
computer over WiFi, for closer inspection by yourself or a trained physician.

This project is targeted at high-end personal health analytics (it features
expensive analysis techniques such as glucose analysis and limited
communication); in contrast, we aim to create a low-cost highly networked array
of smart urine analyzers that can be used by individuals over a broad swath of
geographic locations at any point in time, and which can allow for aggregate
health of large areas to be tracked.

## Hospital in a Toilet - Hackaday project

Project to create a compact device that can be embedded in an individual's
toilet and which can perform basic urinalysis (particularly analyzing color and
specific gravity of urine) to ease monitoring of both general health as well as
chronic medical conditions.

Our project essentially scales the "Hospital in a Toilet" idea up, aiming to
create a cost-effective distributed network of such "hospitals" across the city
to allow individuals the convenience of checking on their health at any
geographical location at any time, while also facilitating city-wide health
monitoring.

# Architecture

Based on our design requirements, our lavatory health monitoring system has two
primary users and end-goals in mind: to help individuals gain easy access to
health monitoring city-wide, and to enable monitoring of the city's collective
health through distributed urinalysis. System-wide, our architecture will adopt
a hub-and-spoke model, with multiple sensing nodes in each bathroom (urinal
pucks) connecting to a local bathroom-level server/gateway which performs
analysis and interacts with individuals requesting personal health data, and
which also funnels data to a central city server.

In order to gather data, the user will urinate into the urinal. The puck will
collect this urine passively and will be woken up by the presence of urine.
Once woken up, it will begin sensing and data collection and stream its results
to the hub located in the bathroom. The hub will aggregate data from multiple
pucks and periodically send it to the city-wide server for further processing.
A user can also connect to the local hub to gather basic analytics and data
after indicating their desire to receive such data.

# System Interaction

Our system uses a number of different technologies to facilitate all of the
communication between different components. Below we've detailed the methods by
which each component will talk to each other component. For simplicity, only
one puck and hub are drawn, though the communication technologies between the
pucks and hub, as well as those between hubs and central server, trivially
scale to multiple connected devices. In short, the puck communicates with all
of its various sensors through either an I2C ADC or directly over I2C. The puck
transmits its data to the hub over an I2C radio, which then forwards the
information to the server via WiFi.

# Team

* Matthew Lee * Joel Loo * Vasu Agrawal * Eric Fang
