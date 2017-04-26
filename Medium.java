
public class Medium {
  private boolean busy;

  public synchronized boolean getBusy() {
    if (busy) {
      return true;
    } else {
      return false;
    }
  }

  public synchronized void setBusy() {
    busy = true;
  }

  public synchronized void setFree() {
    busy = false;
  }

  public Medium() {
    busy = false;
  }
}
