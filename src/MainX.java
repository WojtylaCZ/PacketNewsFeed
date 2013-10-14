import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class MainX {

    public static void main(String[] args) {

	// tail -f test2.pcap -n 100000000 | tshark -r - -T fields -e
	// frame.number -e frame.time -e eth.src -e eth.dst -e eth.type -e
	// ip.src -e ipv6.src -e ip.dst -e ip.proto -e tcp.flags.syn -e
	// tcp.flags.ack -E header=y -E separator=- -E quote=d -E occurrence=f

//	String xx = "\"1\",\"Sep 18, 2013 13:50:27.690439000\",\"1c:6f:65:c0:43:92\",\"ff:ff:ff:ff:ff:ff\",\"0x0800\",\"147.32.83.216\",,\"255.255.255.255\",\"17\",,";
//	System.out.println(xx);
//	
//	String frames[] = xx.split("\",\"");
//	
//	System.out.println(frames[0]);
//	System.out.println(frames[1]);
//	for (String string : frames) {
//	    System.out.println(string);
//	}
	
	parseInput();

    }

    /**
     * 
     */
    private static void parseInput() {
	
	
	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	String frames[];
	
	String x = null;
	int i = 0;
	try {
	    while (i < 20 && ((x = input.readLine()) != null)) {
		frames = x.split("\",\"") ;
//		for (String string : frames) {
//		    System.out.println(string);
//		}
		System.out.println(frames);
		toString(frames);
		i++;
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public static void toString(String frame[]) {
	String divider = " | ";
	for (int i = 0; i < frame.length; i++) {
	    
	}
	
	try {
	    System.out.println("frame.number" + frame[0] + divider + "frame.time" + frame[1] + divider + "eth.src"
		    + frame[2] + divider + "eth.dst" + frame[3] + divider + "eth.type" + frame[4] + divider + "ip.src"
		   // );
		    + frame[5] + divider + "ipv6.src" + frame[6] + divider + "ip.dst" + frame[7] + divider + "ip.proto"
		    + frame[8] + divider + "tcp.flags.syn" + frame[9] + divider + "tcp.flags.acks" + frame[10]);
	} catch (ArrayIndexOutOfBoundsException e) {
	    e.printStackTrace();
	}

    }
}
