public class csmasim {
  // N, number of stations
  public static final int N = 8;
  // n, number of packets per station
  public static final int n = 3;

  // Tp is the time it takes to send a data packet along the medium.
    // Tp = 111
    // 0.3*Td < Tp < 0.6Td ==> 72 < Tp < 144
  public static final int Tp = 72;

  // takes no args
  public static void main(String[] args) {
    Medium m = new Medium();
    Station[] stations = new Station[N];
    for (int i=0; i<N; i++) {
      stations[i] = new Station(i, n, 101, Tp, m);
      stations[i].start();
    }
  }
}
