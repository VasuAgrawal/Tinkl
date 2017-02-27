
import matplotlib.pyplot as plt
import time
import threading
import random
import serial

ser = serial.Serial("/dev/ttyUSB0", 115200)
r = []
g = []
b = []

# This just simulates reading from a socket.
def data_listener():
    while True:
        try:
            rgb = ser.readline().decode('ascii').strip().split(" ")
            rgb = list(map(int, rgb))
            if len(rgb) != 3:
                continue
            r.append(rgb[0])
            g.append(rgb[1])
            b.append(rgb[2])
                
        except:
            pass


if __name__ == '__main__':
    thread = threading.Thread(target=data_listener)
    thread.daemon = True
    thread.start()
    #
    # initialize figure
    fig = plt.figure() 
    # ln, = plt.plot([])
    plt.ion()
    plt.show()
    while True:
        data_r = (r + [])
        data_g = (g + [])
        data_b = (b + [])
        plt.pause(.0001)
        r_line = plt.plot(range(len(data_r))[-50:], data_r[-50:])
        g_line = plt.plot(range(len(data_g))[-50:], data_g[-50:])
        b_line = plt.plot(range(len(data_b))[-50:], data_b[-50:])
        plt.xlim([len(data_r) - 50, len(data_r)])
        plt.ylim([min([0] + data_r[-50:] + data_g[-50:] + data_b[-50:]), max([1]
            + 
                    data_r[-50:] + data_g[-50:] + data_b[-50:])])
        plt.setp(r_line, color='r')
        plt.setp(g_line, color='g')
        plt.setp(b_line, color='b')

        plt.draw()
        plt.show(False)

