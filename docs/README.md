![Tinkl Logo](/images/tinkl_logo.png)
![Glamour Shot of Puck](/images/puck_glamour.jpg)

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

## Functional

* Urinal puck is capable of collecting urine and analyzing its
color, clarity and temperature
* Collection mechanism capable of preventing
cross-contamination of urine samples across different individuals 
* System is
capable of predicting basic health markers like hydration level and basal body
temperature from collected data 
* System provides means for individuals to log
when they are using a urinal and access health data on demand via a smartphone
app 

## Nonfunctional 

* Urinal puck should be small and waterproofed (up to IP65 rating) 
* Urinal puck should be unobtrusive, preventing user discomfort 
* Urinal puck should last ~2 weeks - 1 month on a battery

# Use Cases 

Our systems are designed to allow people to increase awareness of
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

# Competitive Analysis

## [UnderworldsMIT's Senseable City Lab](http://underworlds.mit.edu/)

Research project by MIT's Senseable City Lab analyzing wastewater and sewage for
biomarkers of human health, with the goal of massively collecting data and using
ananalytics to allow the health of the city to be monitored, from the scale of a
neighborhood to the aggregate health of the entire city.

While this project aims to enable city-wide health monitoring through
large-scale, city-wide data collection and analytics just like the Underworlds
project, we also aim to provide individuals with the ability to have their
health assessed on demand, by providing an interface through which they can log
their lavatory usage and access their collected health data.

## [Toto Intelligence Toilet](https://singularityhub.com/2009/05/12/smart-toilets-doctors-in-your-bathroom/)

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

## [Hospital in a Toilet - Hackaday project](https://hackaday.io/project/2387-hospital-in-a-toilet)

Project to create a compact device that can be embedded in an individual's
toilet and which can perform basic urinalysis (particularly analyzing color and
specific gravity of urine) to ease monitoring of both general health as well as
chronic medical conditions.

Our project essentially scales the "Hospital in a Toilet" idea up, aiming to
create a cost-effective distributed network of such "hospitals" across the city
to allow individuals the convenience of checking on their health at any
geographical location at any time, while also facilitating city-wide health
monitoring.

# Tech Specs

## Urinal Puck

* RGB light sensor breakout board ISL29125 - Sparkfun, $7.95 
* NTC thermistor - Digikey, $2.26
* Analog turbidity sensor SEN0189 - DFRobot, $9.90
* nRF24L01+ Enhanced Wireless Module - GearBest, $0.99
* ATMega328P microcontroller - Mouser, $2.26
* MCP1700 low quiescent current regulator - Mouser, $0.40
* 1.5V Alkaline AA batteries (3x per puck) - Amazon, 
* $2.30 Custom-designed PCB combining sensing, comms and MCU
* Custom-designed, 3D-printed plastic and lasercut acrylic puck
* Efficient custom data transfer protocol

![Custom data protocol](/images/radio_protocol.png)

## Hub

* Raspberry Pi 3 Model B (ARMv8, 1GB RAM) - Adafruit, $39.95
* nRF24L01+ Enhanced Wireless Module - GearBest, $0.99
* Custom Protocol Buffer (protobuf) to upload data to server

## Server

* Python and Tornado server to listen to hub data
* Protobuf to decode data stream from hub
* Google Remote Procedure Call (gRPC) server for Android app

## Smartphone App

* Android Operating System
* Google Remote Procedure Call (gRPC) client to fetch urination data from server
* Protobuf to decode data stream from server
* Google Play Computer Vision library for QR code detection
* MPAndroidChart for data visualization

## Demonstration Urinal

* Sponsored Urinal
* 2x4 Lumber for frame
* Copper tubing
* 2x 5 gallon drums for water storage
* Water pump to pressurize system
* QR Code to identify puck and hub

# Architecture

Based on our design requirements, our lavatory health monitoring system has two
primary users and end-goals in mind: to help individuals gain easy access to
health monitoring city-wide, and to enable monitoring of the city's collective
health through distributed urinalysis. System-wide, our architecture will adopt
a hub-and-spoke model, with multiple sensing nodes in each bathroom (urinal
pucks) connecting to a local bathroom-level server/gateway which performs
analysis and interacts with individuals requesting personal health data, and
which also funnels data to a central city server.

![Architecture diagram 1](/images/arch1.png)

In order to gather data, the user will urinate into the urinal. The puck will
collect this urine passively and will be woken up by the presence of urine.
Once woken up, it will begin sensing and data collection and stream its results
to the hub located in the bathroom. The hub will aggregate data from multiple
pucks and periodically send it to the city-wide server for further processing.
A user can also connect to the local hub to gather basic analytics and data
after indicating their desire to receive such data.

![Architecture diagram 2](/images/arch2.png)

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

![Interaction diagram 1](/images/interaction.png)

# Final Product

After a semester's work, we produced a puck capable of measuring turbidity,
temperature, and color from urine samples. The puck has a watertight enclosure
to protect from the harsh conditions to which it is exposed. It uses an
algorithm optimized for low power usage in order to obtain the projected battery
life of approximately 1 year. The samples gathered by the puck are aggregated
into a urination event, which is then sent to the hub inside the bathroom. The
hub forwards data to the server. This data can be accessed from the user's
smartphone when they scan the QR code next to the urinal; the Android
application will then request the data for the most recent urination event for
that urinal from the server. The server will respond with the data if it is less
than 5 minutes old, as a security measure. After this process, another person
may use the urinal, and the cycle will begin again.

For more detail on the development process, as well as specific numbers and
charts, please see our final report (linked below).

## Sensing Algorithm

```
while True:
    temp = read_from_thermistor()
    if temp > threshold: // Take detailed sensor readings
        samples = []
        for sample_number = 1 ... 30:
            samples[i] = take_sample()
            short_sleep()
        send_data_to_hub()
    else: // There's no urine, wait for some time.
        long_sleep()
```

## Puck Design

The puck design consists of a 3d printed enclosure, sealed with epoxy,
surrounding a custom PCB which houses the sensors, radio, and MCU. Inside, above
the PCB is a space for the batteries, as well as the breakout board for the
turbidity sensor. The individual components are labeled below, as well as a fun
picture of the bottom of the puck.

![Labeled Puck Design](/images/puck_detail.jpg)
![Puck Bottom](/images/puck_bottom.jpg)

## Overall Changes

Our original plan was to design a system to be useful at both a personal and
institutional setting. However, privacy concerns arose over the course of the
semester which suggested that we target only a personal use case, so we adapted
our model to focus primarily on individual users. It is for this reason that
data is inaccessible on the server after 5 minutes. Switching the system to
allow an institutional viewer to look at the data would not be technically
complex, but would require exploring privacy problems which proved to be beyond
the scope of our project.

For the majority of the semester, the puck design revolved around restricting
the urine stream through the drain, allowing it to pool up, and then analyzing
the pooled liquid as it slowly drained. Shortly before the demo, however, we
realized that this design prevented multiple uses of the urinal in quick
succession; each flush would take upwards of 5 minutes to drain, which is not
acceptable for an industrial setting. Instead, we decided to remove the flow
restrictor and elevate the puck above the surface of the water. We then oriented
the turbidity sensor such that it was always below the resting water line of the
urinal, and found this to be a much better design overall for both portablity
and usefulness; the puck could now be transferred to almost any similar urinal,
and did not limit flushes at all.

Apart from these minor changes, we are happy to say that the rest of the
system was implemented exactly as designed. Our architecture and communication
protocols at every level proved sufficient and robust enough for our
application, indicative of the thought and care we put into the designs
beforehand.

# Future Work

A conference call with one of our sponsors, Kohler, revelead a number of areas
for possible future work. One in particular stands out as potentially
game-changing for urinals. Today's automated flush mechanisms rely on cheap IR
distance sensors which are often inaccurate and error prone. Many flushes happen
prematurely, or can simply be triggered by a passerby. IR sensors may also
become blocked a result of smoking or chewing gum usage. Such an error prone
system necessitates the inclusion of a manual flush valve. Due to the direct
contact thermistor in our system, we detect the presence of urine much more
accurately than existing systems. It is possible to integrate this detection
into the flush mechanism as a trigger. The high precision would all but remove
the need for a flush valve, permiting the cost savings to be passed into the
development of the puck. Clearly, there is enormous potential here simply
waiting to be explored.

# Team Members

* [Matthew Lee](https://github.com/mattlkf)
* [Joel Loo](https://github.com/joelloo)
* [Vasu Agrawal](https://github.com/VasuAgrawal)
* [Eric Fang](https://github.com/eric1221bday)

# Acknowledgements

* Anthony Rowe, our professor, for being as excited about our project as we were
* Craig Hesling, our TA, for guiding us through this journey
* [Kohler](http://www.kohler.com/corporate/index.html), for the donation of a 
  urinal and flush valve, as well as the advice
  and guidance of their engineers
* [Sloan](https://www.sloan.com), for the donation of our first  urinal and 
  flush valve, used in our final demonstration.
* [American Standard](https://www.americanstandard-us.com/), for the donation of
  a urinal and flush valve.

# Links

* [Final Report
  (PDF)](https://drive.google.com/file/d/0BzCB5gz-bnecOVdhTEpMcVZ5VXc/view?usp=sharing)
* [Final
  Poster (PDF)](https://drive.google.com/file/d/0BwdI0A5s_UwvUFBrWlU4NEZXdkE/view?usp=sharing)
* [Midsemester
  Presentation (PPT)](https://docs.google.com/presentation/d/1eH9g_bitqXYJriN3yyhiHkPiWwhJeO1CGgPf4aCRGDc/edit?usp=sharing)
* [Pictures and
  Videos](https://drive.google.com/drive/folders/0B31KBJSOYo9LNTVUSnRzS2QxSmM?usp=sharing)

