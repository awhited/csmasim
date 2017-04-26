# csmasim
Programming project for CS1699
## How to compile and run
Compile this program by opening a command shell and using  
* `javac csmasim.java`  

Run this program by then typing
* `java csmasim`

## File Structure
### csmasim.java
contains enough code to initalize a medium and start station threads. modify this program for most execution parameters.
### Medium.java
Medium is implemented using the object oriented approach.  
Synchronized Methods  
  * getBusy()   
    * returns true when busy  
    * returns false when not busy.  
  * setBusy()  
    * sets status to busy  
  * setFree()  
    * sets status to not busy.  

### Station.java
  * Station emulates a wireless station which performs csma-cd on a Medium.
  * Detailed description in the source code.
  * you may flip debug to true or false for alternative output

## Result Analysis
I found that with a modern system, collisions are very very rare, even with probability set to 100, many nodes on the network, and many packets to send, collisions seem to be random and maybe occur once every fifty or so transmissions. This was a great program to trace through the logic of csma-cd and learn it better.

## Source Code
My self-documenting Source Code may be found in the file tree above.
