import java.lang.Math;

public class Station extends Thread {
  // config params
    // one tick of time (ts) is one exec of a for loop.
    // Tdifs is 20ts,
    // Tifs is 10ts, wait for ack
    // Td is 240ts, Data ready check interval
    // debug sets verbosity level

  // local (private) vars
    // N is number of packets.
    // P is probability for data ready
    // Tp is single packet transmisison time
    // Ttot is the total time for a packet to go, resets every cycle
    // k is a multiplier for the random backoff time
    // w is the random backoff time. since it must be the same for
      // W=10
      // all wireless stations and 1 < w < 2*8
    // Tcw is k*W;

  private int Ts, Tdifs, Tifs, Td;
  private int N, P, Tp, Ttot, Tcw, k, W;
  private int ident;
  private Medium M;

  // debug controls verbosity level
  private final boolean debug = true;

  // implements wait
  public void sleep(int t) {
    for (int i=0;i<t;i++);
  }

  // implements probablilistic packet readiness
  private boolean ready() {
    Double p = Math.random()*100;
    int ph = p.intValue();
    return 0<ph && ph<P;
  }

  // sets W, can be extended for a more complex implementation
  private void genW() {
    W=10;
  }

  // gets the status of the medium
  public boolean busy() {
    return M.getBusy();
  }

  // run is called on start()
  // we countdown from N.
  // the function then implements the steps from the supplied flow chart
  // (#) refers to the corresponding step on Figure 1. of project description
  public void run() {
    while (N>0) {
      sleep(Td);                             // (1)
      Ttot=0;
      while (!ready()) {                     // (2)
        Ttot=0;
        sleep(Td);
      }

      while (busy()) {                       // (3, 4, 5)
        Ttot+=1;
        sleep(Ts);
      }

      boolean escape=false;
      sleep(Tdifs);                          // (6)
      Ttot+=Tdifs;
      if (k==-1) { k=1; } else {             // (15)
        k=2*k;
        if (k>16) k=16;
      }
      while (!escape) {                      // (16)
        genW();
        Tcw = k*W;
        while (Tcw>0) {                      // (14)
          sleep(Ts);                         // (7)
          Ttot+=Ts;
          Tcw-=1;
          if (!busy()) {                     // (8, 9)
            escape = true;
            break;
          }
          sleep(Ts);                         // (10)
          Ttot+=Ts;
          while (busy()) {                   // (12)
              sleep(Ts);
              Ttot+=Ts;
          }
          sleep(Tdifs);                      // (13)
          Ttot+=Tdifs;
        }
      }
      M.setBusy();                           // (17)
      sleep(Tp);                             // (18)
      Ttot+=Tp;
      sleep(Tifs);                           // (19)
      Ttot+=Tifs;
      M.setFree();                           // (20)
      sleep(Tdifs);                          // (21)
      Ttot+=Tdifs;
      if (debug) {                           // (23)
        System.out.println(
            "Station " + ident +
            " transmitted over " +
            Ttot + " units of time");
      } else {
        System.out.println(Ttot);
      }
      N--;
    }
    if (debug) System.out.println("Station " + ident + " Done.");
  }

  // ident = station identity number 0-7
  // n = Number of packets to send
  // p = int from 0 to 99, probability of ready packet
  // tp = time to send one packet
  // m = reference to the medium
  public Station(int i, int n, int p, int tp, Medium m) {
    Ts = 1;
    Td = 240*Ts;
    Tdifs = 20*Ts;
    Tifs = 10*Ts;
    k=-1;

    ident = i;
    N=n;
    P=p;
    Tp=tp;
    M=m;
  }
}
