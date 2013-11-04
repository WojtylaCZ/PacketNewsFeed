/**
 * 
 */
package versionThreads;

import java.util.Date;

import rrd4jLib.ThreadsTest2;

/**
 * Malware Capture Facility CVUT University, Prague, Czech Republic
 * 
 * These files were generated as part of a research project in the CVUT
 * University, Prague, Czech Republic. The goal is to store long-lived real
 * botnet traffic and to generate labeled netflows files. Any question feel free
 * to contact us: Sebastian Garcia, sebastian.garcia@agents.fel.cvut.cz Vojtech
 * Uhlir <vojtech.uhlir@agents.fel.cvut.cz
 * 
 */
public class OutputAndResetThread extends Thread {

    @Override
    public void run() {

	while (true) {
	    try {
		sleep(DataSummary.timeWindowInMillis);
		//sleep(5000);
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }

	    ThreadsTest2.lock.writeLock().lock();
	    try {
		printStatsForUpdateRRDfile();
		setWindowStatsToZero();

	    } catch (Exception ex) {
		ex.printStackTrace();
	    } finally {
		ThreadsTest2.lock.writeLock().unlock();
	    }
	}
    }

    private void printStatsForUpdateRRDfile() {
	StringBuilder s = new StringBuilder();
	s.append("update ");
	s.append(DataSummary.rrdFileToUpdate);
	s.append(" ");
	// String x = String.valueOf(DataSummary.lastEndOfWindow);
	// s.append(x.substring(0, x.length() - 3));
	s.append(new Date().getTime() / 1000);
	s.append(":");
	s.append(Long.valueOf(DataSummary.window_datagramSumUDP_port53));
	s.append(":");
	s.append(Long.valueOf(DataSummary.window_datagramSumTCP_SYN_port25
		+ DataSummary.window_datagramSumTCP_SYN_port587));
	s.append(":");
	s.append(Long.valueOf(DataSummary.window_datagramSumTCP_SYN_port80));
	s.append(":");
	s.append(Long.valueOf(DataSummary.window_datagramSumTCP_SYN_port443));
	s.append(":");
	s.append(Long.valueOf(DataSummary.window_datagramSumTCP_SYN_port22));
	s.append(":");
	s.append(Long.valueOf(DataSummary.window_datagramSumTCP_all));
	s.append(":");
	s.append(Long.valueOf(DataSummary.window_datagramSumUDP_all));
	s.append(":");
	s.append(Long.valueOf(DataSummary.window_packetSum_IPv6));

	System.out.println(s.toString());

    }

    private void setWindowStatsToZero() {
	DataSummary.window_sizeOfAll_frames_inbytes = 0L;

	DataSummary.window_datagramSumTCP_SYN_port22 = 0L;
	DataSummary.window_datagramSumTCP_SYN_port25 = 0L;
	DataSummary.window_datagramSumTCP_SYN_port80 = 0L;
	DataSummary.window_datagramSumTCP_SYN_port443 = 0L;
	DataSummary.window_datagramSumTCP_SYN_port587 = 0L;
	DataSummary.window_datagramSumTCP_all = 0L;
	DataSummary.window_datagramSumUDP_port53 = 0L;
	DataSummary.window_datagramSumUDP_all = 0L;
	DataSummary.window_packetSum_IPv4 = 0L;
	DataSummary.window_packetSum_IPv6 = 0L;
    }
}
