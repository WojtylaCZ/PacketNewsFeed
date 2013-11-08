

/**
 * Malware Capture Facility CVUT University, Prague, Czech Republic
 * 
 * These files were generated as part of a research project in the CVUT
 * University, Prague, Czech Republic. The goal is of this packetnewsfeeder is to read and parse data provided by tshark and count statistics from network traffic and output that for rrd format. All project is in Java language.
 * Any question feel free
 * to contact us: Sebastian Garcia, sebastian.garcia@agents.fel.cvut.cz Vojtech
 * Uhlir <vojtech.uhlir@agents.fel.cvut.cz
 * 
 */


We use tshark now for reading any pcap or any other network file and outputing a packet information.
Command for tshark is following:
tail -f test.pcap -n 10000000000 | tshark -r - -T fields -e frame.time -e frame.len -e eth.type -e tcp.flags.syn -e tcp.dstport -e udp.dstport -e header=y -E separator=- -E occurrence=f 

This outputs packet information which are read by this packetnewsfeeder program.
All lines are parsed and statistic information about them are stored.

In parallel, depending on window time t (sec), these statistics are read, outputed and reseted to zero.

The output is in format that allows to use rrdtool and save it in rrd format.
Input arguments are:

| java -jar packetnewsfeed.jar -t 60 -f test.rrd 

A *.rrd file has to be created before the run the program.

RRD structure:
rrdtool create target.rrd --step 60 DS:DNS:GAUGE:120:0:100000 DS:SPAM:GAUGE:120:0:100000 DS:WEB:GAUGE:120:0:100000 DS:SSL:GAUGE:120:0:100000 DS:SSH:GAUGE:120:0:100000 DS:TCP:GAUGE:120:0:100000 DS:UDP:GAUGE:120:0:100000 DS:IPV6:GAUGE:120:0:100000 RRA:MAX:0.5:1:1440 RRA:MAX:0.5:5:288 RRA:MAX:0.5:30:336 RRA:MAX:0.5:120:372 RRA:MAX:0.5:1440:372


An output might be like this:
update test.rrd 1383844028:111:0:121:1:0:221:247:0
update test.rrd 1383844038:76:0:98:0:0:173:167:0

this is read by rrdtool:
| rrdtool -

the whole command:
tail -n LINES -f PCAPFILE| tshark -r - -T fields -e frame.time -e frame.len -e eth.type -e tcp.flags.syn -e tcp.dstport -e udp.dstport -e header=y -E separator=- -E occurrence=f | java -jar packetnewsfeed.jar -t 60 -f FOLDERNAME.rrd | rrdtool -



