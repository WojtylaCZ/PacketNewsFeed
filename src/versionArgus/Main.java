package versionArgus;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    static long lastEndOfWindow = 0L;
    static long lastFrameTime = 0L;

    static long window_sizeOfAll_frames_inbytes = 0L;
    static long window_numberOfAll_frames = 0L;

    static long window_datagramSumTCP_SYN_port22 = 0L;
    static long window_datagramSumTCP_SYN_port25 = 0L;
    static long window_datagramSumTCP_SYN_port80 = 0L;
    static long window_datagramSumTCP_SYN_port443 = 0L;
    static long window_datagramSumTCP_SYN_port587 = 0L;
    static long window_datagramSumTCP_allOther = 0L;
    static long window_datagramSumUDP_port53_SYN = 0L;
    static long window_datagramSumUDP_SYN_allOther = 0L;
    static long window_packetSum_TCP = 0L;
    static long window_packetSum_UDP = 0L;

    static long timeToAdd = 0L;
    static String rrdFileToUpdate = " ";
    static long timeWindowInMillis = 0L;
    static long timeWhichIsSubstractedBeforePrintingResult = 0L;
    
    static long window_packetSum_IPv6 =0L;

    public static void main(String[] args) {
	// program arguments :-t 2 -f data-source/Win2-test.rrd

	try {
	    if (args.length != 6) {
		System.err.println("Arguments have to be: \n\t-t X, where X is number of minutes of floating window \n\t-f file.rrd \n -s time to substract from final time or 0 in hours");
		System.exit(0);
	    }
	    timeWindowInMillis = Long.parseLong(args[1]) * 60 * 1000;
	    // Current time is created only when the app. is launched
	    timeToAdd = System.currentTimeMillis();
	    // System.out.println(timeToAdd);

	    rrdFileToUpdate = args[3];
	    if (!new File(rrdFileToUpdate).exists()) {
		System.out.println("Cannot find: " + rrdFileToUpdate);
		System.exit(0);
	    }
	    // in hours
	    timeWhichIsSubstractedBeforePrintingResult = Long.parseLong(args[5]) * 60 * 60 * 1000;

	    read();
	} catch (IOException e) {
	    e.getMessage();
	}

    }

    private static void read() throws IOException {

	// Old format:
	//
	// time, len, type, syn, tcp dport, udp dport
	//
	// Example:
	//
	// Jan 1, 1970 01:00:12.247867000-76-0x0800---53-
	//
	// New format.
	//
	// stime dur label:40 proto:10 saddr:27 sport dir daddr:27 dport state
	// stos dtos pkts bytes
	//
	// Example:
	// 1970/01/01
	// 01:00:12.247867,0.009215,flow=From-Botnet-V1-DNS,udp,10.0.2.106,62364,<->,8.8.4.4,53,CON,0,0,2,168
	//

	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	String frame[];
	String line;

	//just ignore the first line!
	input.readLine();
	
	while ((line = input.readLine()) != null) {
	    frame = line.split(",");
	    // numberOfFramesWholePCAP++;
	    window_numberOfAll_frames++;
	    for (int i = 0; i < frame.length; i++) {
		if (!frame[i].isEmpty()) {
		    // System.out.println(frame[i]);
		    parseFrame(frame, i);
		}

	    }
//	    printEndStats();
	    if (lastFrameTime > (lastEndOfWindow + timeWindowInMillis)) {
		// System.out.println(lastFrameTime);
		// System.out.println(lastEndOfWindow);
		// System.out.println(timeWindowInMillis);

		// printStatsWindow();
		// System.out.println("vypissss: "+new
		// Date(lastEndOfWindow-timeToAdd));
		updateRRDfile(lastEndOfWindow);

		// addWindowStatsToGlobal();
		setWindowStatsToZero();
		// printStatsGlobal();
		lastEndOfWindow = lastFrameTime + timeWindowInMillis;
		// System.out.println("end window: "+new
		// Date(lastEndOfWindow-timeToAdd));
	    }
	}
	// printEndStats();

    }

    private static void parseFrame(String[] frame, int i) {
	switch (i) {
	case 0:
	    parseTime(frame[0]);
	    break;
	case 13:
	    parseSize(frame[13]);
	    break;
	case 3:
	    parseTransportType(frame[3], frame[8], frame[9]);
	    break;
	default:
	    break;
	}
	// tail -f livewlan.pcap -n 100000000 | tshark -r - -T fields -e
	// frame.time -e frame.len -e eth.type -e ip.proto -e tcp.flags.syn -e
	// tcp.dstport -e udp.dstport -e header=y -E separator=- -E occurrence=f

    }

    private static void parseTime(String dateInString) {
	dateInString = dateInString.substring(0, dateInString.length() - 3);

	// Oct 4, 2013 13:57:41.242398000
	// Sep 18, 2013 13:54:47.509564000
	String pattern = "yyyy/MM/dd HH:mm:ss.SSS";
	SimpleDateFormat formatter = new SimpleDateFormat(pattern);

	Date date;
	try {
	    date = formatter.parse(dateInString);
	    // System.out.println(date);
	    // System.out.println(date.getTime());
	    lastFrameTime = date.getTime();

	    if (lastFrameTime < 15000000000L) {
		// System.out.println(new Date(lastFrameTime));
		// 15000000000 = GMT: Tue, 23 Jun 1970 14:40:00 GMT in
		// MILICSECONDS!!!!
		// timeToAdd = System.currentTimeMillis();
		lastFrameTime = lastFrameTime + timeToAdd;
		// System.out.println(new Date(lastFrameTime));
	    } else {
		// timeToAdd = 0;
		// lastFrameTime = lastFrameTime + timeToAdd;

		lastFrameTime = lastFrameTime + 0;
	    }

	    if (lastEndOfWindow == 0) {
		lastEndOfWindow = lastFrameTime;
	    }

	} catch (ParseException e) {
	    e.printStackTrace();
	}

    }

    private static void parseSize(String bytesOfFrame) {
	window_sizeOfAll_frames_inbytes = window_sizeOfAll_frames_inbytes + Long.parseLong((String) bytesOfFrame);
    }

    private static void parseTransportType(String transType, String packetDestPort, String packetState) {
	switch (transType) {
	case "tcp":
	    window_packetSum_TCP++;
	    parseTCPportDst(packetState, packetDestPort);
	    break;
	case "udp":
	    window_packetSum_UDP++;
	    parseUDPportDst(packetState, packetDestPort);
	    break;
	}
	if(transType.contains("ipv6")){
	    window_packetSum_IPv6++;
	}
	
    }

    private static void parseTCPportDst(String tcpSyn, String tcpPort) {
	if (tcpSyn.contains("S")) {
	    window_datagramSumTCP_allOther++;
	    switch (tcpPort) {
	    case "443":
		window_datagramSumTCP_SYN_port443++;
		break;
	    case "80":
		window_datagramSumTCP_SYN_port80++;
		break;
	    case "25":
		window_datagramSumTCP_SYN_port25++;
		break;
	    case "22":
		window_datagramSumTCP_SYN_port22++;
		break;
	    case "587":
		window_datagramSumTCP_SYN_port587++;
		break;
	    }
	}

    }

    private static void parseUDPportDst(String udpCon, String udpPort) {
	if (udpCon.contains("CON")) {
	    switch (udpPort) {
	    case "53":
		window_datagramSumUDP_port53_SYN++;
		break;
	    default:
		window_datagramSumUDP_SYN_allOther++;
		break;
	    }
	}

    }

    private static void setWindowStatsToZero() {
	window_sizeOfAll_frames_inbytes = 0L;
	window_numberOfAll_frames = 0L;

	window_datagramSumTCP_SYN_port22 = 0L;
	window_datagramSumTCP_SYN_port25 = 0L;
	window_datagramSumTCP_SYN_port80 = 0L;
	window_datagramSumTCP_SYN_port443 = 0L;
	window_datagramSumTCP_SYN_port587 = 0L;
	window_datagramSumTCP_allOther = 0L;
	window_datagramSumUDP_port53_SYN = 0L;
	window_datagramSumUDP_SYN_allOther = 0L;
	window_packetSum_TCP = 0L;
	window_packetSum_UDP = 0L;
	window_packetSum_IPv6 = 0L;
	// window_datagram_TCP_general = 0;
	// window_datagram_UDP_general = 0;
	// window_datagram_allOther = 0;

    }

    private static void updateRRDfile(Long lastEndOfWindow) throws IOException {
	StringBuilder s = new StringBuilder();
	s.append("update ");
	s.append(rrdFileToUpdate);
	s.append(" ");
	String x = String.valueOf(lastEndOfWindow - timeWhichIsSubstractedBeforePrintingResult);
	s.append(x.substring(0, x.length() - 3));
	s.append(":");
	s.append(Long.valueOf(window_datagramSumUDP_port53_SYN));
	s.append(":");
	s.append(Long.valueOf(window_datagramSumTCP_SYN_port25 + window_datagramSumTCP_SYN_port587));
	s.append(":");
	s.append(Long.valueOf(window_datagramSumTCP_SYN_port80));
	s.append(":");
	s.append(Long.valueOf(window_datagramSumTCP_SYN_port443));
	s.append(":");
	s.append(Long.valueOf(window_datagramSumTCP_SYN_port22));
	s.append(":");
	s.append(Long.valueOf(window_datagramSumTCP_allOther));
	s.append(":");
	s.append(Long.valueOf(window_datagramSumUDP_SYN_allOther+window_datagramSumUDP_port53_SYN));
	s.append(":");
	s.append(Long.valueOf(window_packetSum_IPv6));
	

	System.out.println(s.toString());

    }

    private static void printEndStats() {
	System.out.println("Window until:\t" + new Date(lastFrameTime).toString());
	System.out.println("Number of frames in the window:\t" + window_numberOfAll_frames);
	System.out.println("Size of all frames in the window in bytes:\t" + window_sizeOfAll_frames_inbytes);
	System.out.println("Amount of SPAM conn - TCPport 25:\t" + window_datagramSumTCP_SYN_port25);
	System.out.println("Amount of SPAM conn - TCPport 587:\t" + window_datagramSumTCP_SYN_port587);
	System.out.println("Amount of WEB conn - TCPport 80:\t" + window_datagramSumTCP_SYN_port80);
	System.out.println("Amount of SSL conn - TCPport 443:\t" + window_datagramSumTCP_SYN_port443);
	System.out.println("Amount of SSH conn - TCPport 22:\t" + window_datagramSumTCP_SYN_port22);
	System.out.println("Amount of TCP+SYN all conn:\t" + window_datagramSumTCP_allOther);
	System.out.println("Amount of DNS conn - UDPport 53:\t" + window_datagramSumUDP_port53_SYN);
	System.out.println("Amount of UDP all conn:\t" + window_datagramSumUDP_SYN_allOther);
	System.out.println("Amount of UDP packets:\t" + window_packetSum_UDP);
	System.out.println("Amount of Ipv6 packets:\t" + window_packetSum_IPv6);
    }
}
