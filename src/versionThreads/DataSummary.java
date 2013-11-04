package versionThreads;

import java.util.concurrent.locks.ReentrantReadWriteLock;

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
public class DataSummary {
    //init of app.
    public static String rrdFileToUpdate = " ";

    public static long timeToAdd = 0L;
    public static long timeWindowInMillis = 0L;
    

    // there is a lock to lock all variables preventins collions when one thread
    // updates those variable and the second one reads them and and reset them
    // to zero.
    public static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    
    //for function parseTime
    //public static long lastEndOfWindow = 0L;
    public static long lastFrameTime = 0L;
     
    //for function parseSize
    public static long window_sizeOfAll_frames_inbytes = 0L;
    
    //for funtion parseNetworkType
    public static long window_packetSum_IPv4 = 0L;
    public static long window_packetSum_IPv6 = 0L;

    
    //for funtion parseUDPportDst
    public static long window_datagramSumUDP_port53 = 0L;
    public static long window_datagramSumUDP_all = 0L;
    
    
    //for function parseTCPportDst
    public static long window_datagramSumTCP_SYN_port22 = 0L;
    public static long window_datagramSumTCP_SYN_port25 = 0L;
    public static long window_datagramSumTCP_SYN_port80 = 0L;
    public static long window_datagramSumTCP_SYN_port443 = 0L;
    public static long window_datagramSumTCP_SYN_port587 = 0L;
    public static long window_datagramSumTCP_all = 0L;
}
