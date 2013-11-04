package rrd4jLib;
import java.util.concurrent.atomic.AtomicLong;

public class ThreadsTest1 {
    static AtomicLong i = new AtomicLong(0);

    public static void main(String args[]) {
	Thread prvni = new HelloThread();
	prvni.start();
	Thread druhy = new HelloThread2();
	druhy.start();

    }

}

class HelloThread extends Thread {

    public void run() {

	while (true) {
	    System.out.println("a" + ThreadsTest1.i.addAndGet(10));
	    try {
		sleep(10);
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }

}

class HelloThread2 extends Thread {

    public void run() {
	while (true) {
	    System.out.println("b" + ThreadsTest1.i.addAndGet(5));
	    try {
		sleep(10);
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}

    }

}

class HelloThread3 extends Thread {

    public void run() {
	while (true) {
	    System.out.println("c" + ThreadsTest1.i.addAndGet(5));
	    try {
		sleep(1000);
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}

    }

}
