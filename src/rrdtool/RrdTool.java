package rrdtool;

import java.io.File;
import java.io.IOException;

import org.rrd4j.ConsolFun;
import org.rrd4j.DsType;
import org.rrd4j.core.RrdDb;
import org.rrd4j.core.RrdDef;
import org.rrd4j.core.Sample;

public class RrdTool {

    // rrdtool create test.rrd --start 920804400 DS:speed:COUNTER:600:U:U
    // RRA:AVERAGE:0.5:1:24 RRA:AVERAGE:0.5:1:24

    public static void main(String[] args) {
	tryupdate();

    }

    private static void tryupdate() {
	try {
	    // rrdtool create target.rrd --step 300
	    // DS:DNS:GAUGE:600:0:100000
	    // DS:SPAM:GAUGE:600:0:100000 DS:WEB:GAUGE:600:0:100000
	    // DS:SSL:GAUGE:600:0:100000 DS:SSH:GAUGE:600:0:100000
	    // DS:TCP:GAUGE:600:0:100000 DS:UDP:GAUGE:600:0:100000
	    // DS:IPV6:GAUGE:600:0:100000
	    // RRA:AVERAGE:0.5:12:24
	    // RRA:AVERAGE:0.5:288:7 RRA:AVERAGE:0.5:288:31

	    RrdDef rrdDef = new RrdDef("./rrd/target2.rrd");
	    rrdDef.setStep(300);
	    rrdDef.addDatasource("DNS", DsType.GAUGE, 600, 0, 100000);
	    rrdDef.addDatasource("SPAM", DsType.GAUGE, 600, 0, 100000);
	    rrdDef.addDatasource("WEB", DsType.GAUGE, 600, 0, 100000);
	    rrdDef.addDatasource("SSL", DsType.GAUGE, 600, 0, 100000);
	    rrdDef.addDatasource("SSH", DsType.GAUGE, 600, 0, 100000);
	    rrdDef.addDatasource("TCP", DsType.GAUGE, 600, 0, 100000);
	    rrdDef.addDatasource("UDP", DsType.GAUGE, 600, 0, 100000);
	    rrdDef.addDatasource("IPV6", DsType.GAUGE, 600, 0, 100000);

	    rrdDef.addArchive(ConsolFun.AVERAGE, 0.5, 12, 24);
	   // rrdDef.addArchive(ConsolFun.AVERAGE, 0.5, 288, 7);
	    rrdDef.addArchive(ConsolFun.AVERAGE, 0.5, 288, 31);

	    RrdDb rrdDb = new RrdDb(rrdDef);
	    rrdDb.close();

	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    public static void justTry() {
	try {
	    File f = new File("./rrd/test.rrd");
	    System.out.println(f.length());

	    RrdDef rrdDef = new RrdDef("./rrd/test.rrd");
	    rrdDef.setStartTime(920804400L);
	    rrdDef.addDatasource("speed", DsType.COUNTER, 600, Double.NaN, Double.NaN);
	    rrdDef.addArchive(ConsolFun.AVERAGE, 0.5, 1, 24);
	    rrdDef.addArchive(ConsolFun.AVERAGE, 0.5, 6, 10);
	    RrdDb rrdDb;
	    rrdDb = new RrdDb(rrdDef);
	    rrdDb.close();

	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

}
