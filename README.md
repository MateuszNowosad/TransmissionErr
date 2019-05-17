# TransmissionErr

This program is a simple showcase of few error detection algorithms. It can simulate bit errors during transmission and shows if the chosen algorithm finds an error in the sent message.  
  
**The following algorithms were implemented:**  
● Cyclical Redundancy Check (CRC): CRC 12, CRC 16, CRC 16 REVERSE, CRC 32, SDLC, SDLC REVERSE, CRC-ITU, ATM.  
● Hamming coding (7,4).  
● Parity bit check  

**The program has the following functions:**  
● Possibility of previewing the input data, redundant (control data),data after disruption, outgoing with corrected and detected errors (error code), output signal without control data.  
● Option to introduce transmission errors to specific or random bits.  
● Generation of an input signal (simulating data transmission).  
● Generation of disturbances.  
● Calculation of errors detected, corrected and not detected.  
● Calculating the amount of real data transmitted and redundant information.  

## Bit color legend  
**For CRC and parity:**  
BLUE: Control bits  
PURPLE: Wrong/uncertain control bits  
BLACK: data bits  
RED: Wrong/uncertain data bits  

**For Hamming coding:"**  
BLUE: Control bits  
PURPLE: Wrong control bits  
LIGHT BLUE: Detected wrong control bits  
BLACK: data bits  
RED: wrong data bits  
GREEN: detected wrong data bits  

