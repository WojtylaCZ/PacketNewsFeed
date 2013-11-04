package rrd4jLib;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadsTest2 {
    
    //just a thread mngmt. test

    public static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    public static int variable = 0;

    public static void main(String[] args) {
	Thread a = new A();
	// Thread b = new B();
	Thread c = new C();

	a.start();
	// b.start();
	c.start();

    }

}

class A extends Thread {
    public ThreadsTest2 b;

    public void run() {
	while (true) {
	    update();
	    try {
		sleep(1);
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }

    public void update() {
	ThreadsTest2.lock.writeLock().lock();
	try {
	    ThreadsTest2.variable++;
	    // System.out.println(Data.variable);
	} catch (Exception ex) {
	    ex.printStackTrace();
	} finally {
	    ThreadsTest2.lock.writeLock().unlock();
	}
    }

}

// class B extends Thread {
// public Data a;
//
// public void run() {
// while (true) {
// update();
// try {
// sleep(9000);
// } catch (InterruptedException e) {
// // TODO Auto-generated catch block
// e.printStackTrace();
// }
// }
// }
//
// public void update() {
// Data.lock.readLock().lock();
// try {
// System.out.println(Data.variable);
// } finally {
// Data.lock.readLock().unlock();
// }
// }
// }

class C extends Thread {
    public ThreadsTest2 a;

    public void run() {
	while (true) {
	    update1();
	    try {
		sleep(1000);
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }

    public void update1() {
	ThreadsTest2.lock.writeLock().lock();
	try {
	    System.out.println(ThreadsTest2.variable);
	    sleep(600);
	    System.out.println(ThreadsTest2.variable);
	    ThreadsTest2.variable = 0;
	        
	} catch (Exception ex) {
	    ex.printStackTrace();
	} finally {
	    ThreadsTest2.lock.writeLock().unlock();
	}
    }
}
