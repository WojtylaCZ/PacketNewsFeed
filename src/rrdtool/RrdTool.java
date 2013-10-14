package rrdtool;

import java.io.File;
import java.io.IOException;

import org.rrd4j.ConsolFun;
import org.rrd4j.DsType;
import org.rrd4j.core.FetchData;
import org.rrd4j.core.FetchRequest;
import org.rrd4j.core.RrdDb;
import org.rrd4j.core.RrdDef;
import org.rrd4j.core.Sample;

public class RrdTool {

    // rrdtool create test.rrd --start 920804400 DS:speed:COUNTER:600:U:U
    // RRA:AVERAGE:0.5:1:24 RRA:AVERAGE:0.5:1:24

    public static void main(String[] args) {
	// createFile();
	// addValues();
	 //output();

	try {
	    origin();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    private static void origin() throws IOException {
	RrdDef rrdDef = new RrdDef("./rrd/targetori.rrd");
	rrdDef.setStartTime(920804400L);
	rrdDef.addDatasource("speed", DsType.COUNTER, 600, Double.NaN, Double.NaN);
	rrdDef.addArchive(ConsolFun.AVERAGE, 0.5, 1, 24);
	rrdDef.addArchive(ConsolFun.AVERAGE, 0.5, 6, 10);
	RrdDb rrdDb = new RrdDb(rrdDef);
	rrdDb.close();

	RrdDb rrdDb1 = new RrdDb("./rrd/targetori.rrd");
	Sample sample = rrdDb1.createSample();
	sample.setAndUpdate("920804700:12345");
	sample.setAndUpdate("920805000:12357");
	sample.setAndUpdate("920805300:12363");
	sample.setAndUpdate("920805600:12363");
	sample.setAndUpdate("920805900:12363");
	sample.setAndUpdate("920806200:12373");
	sample.setAndUpdate("920806500:12383");
	sample.setAndUpdate("920806800:12393");
	sample.setAndUpdate("920807100:12399");
	sample.setAndUpdate("920807400:12405");
	sample.setAndUpdate("920807700:12411");
	sample.setAndUpdate("920808000:12415");
	sample.setAndUpdate("920808300:12420");
	sample.setAndUpdate("920808600:12422");
	sample.setAndUpdate("920808900:12423");
	rrdDb1.close();

	RrdDb rrdDb2 = new RrdDb("./rrd/targetori.rrd");
	FetchRequest fetchRequest = rrdDb2.createFetchRequest(ConsolFun.AVERAGE, 920804400L, 920809200L);
	FetchData fetchData = fetchRequest.fetchData();
	System.out.println(fetchData.dump());
	rrdDb2.close();

    }

    private static void output() {
	RrdDb rrdDb;
	try {
	    rrdDb = new RrdDb("./rrd/target2.rrd");
	    FetchRequest fetchRequest = rrdDb.createFetchRequest(ConsolFun.AVERAGE, 920804400L, 920809200L);
	    FetchData fetchData = fetchRequest.fetchData();
	    System.out.println(fetchData.dump());
	    rrdDb.close();

	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    private static void addValues() {
	RrdDb rrdDb;
	try {
	    rrdDb = new RrdDb("./rrd/target2.rrd");
	    Sample sample = rrdDb.createSample();
	    //sample.setAndUpdate("920805000:12357");
	    sample.setAndUpdate("920805600:16000");
	    rrdDb.close();

	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    private static void createFile() {
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
	    rrdDef.setStartTime(920804400L);
	    rrdDef.addDatasource("DNS", DsType.GAUGE, 600, 0, 100000);
	    // rrdDef.addDatasource("SPAM", DsType.GAUGE, 600, 0, 100000);
	    // rrdDef.addDatasource("WEB", DsType.GAUGE, 600, 0, 100000);
	    // rrdDef.addDatasource("SSL", DsType.GAUGE, 600, 0, 100000);
	    // rrdDef.addDatasource("SSH", DsType.GAUGE, 600, 0, 100000);
	    // rrdDef.addDatasource("TCP", DsType.GAUGE, 600, 0, 100000);
	    // rrdDef.addDatasource("UDP", DsType.GAUGE, 600, 0, 100000);
	    // rrdDef.addDatasource("IPV6", DsType.GAUGE, 600, 0, 100000);

	    // rrdDef.addArchive(ConsolFun.AVERAGE, 0.5, 12, 24);
	    // rrdDef.addArchive(ConsolFun.AVERAGE, 0.5, 288, 31);
	    rrdDef.addArchive(ConsolFun.AVERAGE, 0.5, 1, 24);

	    RrdDb rrdDb = new RrdDb(rrdDef);
	    rrdDb.close();

	} catch (IOException e) {
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
	    e.printStackTrace();
	}
    }

}
