package thread;


public class ThreadA extends Thread  {
	public void release() {
		synchronized (this) {
			notify();
		}
	}
}
