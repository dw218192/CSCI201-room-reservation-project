package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import webDataClasses.Room;
import webDataClasses.RoomList;

/*
 * this servlet does the following tasks:
 * 1. pull the html source from the library website
 * 2. parse and extract the info about room availability from the HTML source pulled
 * 3. forward info to a jsp, which would redisplay the info sents
 */

@WebServlet("/HtmlParser")
public class HtmlParser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public HtmlParser() {
        super();
    }
    /*
    public StringBuffer puller(String URLsource) throws IOException {
		
    	URL url = new URL(URLsource);

		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		String inputLine;
		StringBuffer resp = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
		  resp.append(inputLine + "\n");
		}
		
		System.out.println(resp);

		in.close();
		return resp;
    }
    */
    
    public void parser(StringBuffer webpage) {
    	RoomList resultList = new RoomList();
    	int index = webpage.indexOf("fc-event-container", 0);
    	do {
    		int avaiIndex = webpage.indexOf("s-lc-eq-");

    		if(webpage.charAt(avaiIndex+8) == 'a') {
    			Room new_Room = new Room(false,"","");
    			resultList.addRoom(new_Room);
    		}
    		
    	}while(webpage.indexOf("fc-event-container", index+20) != -1);
    	System.out.println(resultList.size());
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Document roomReservationPage = Jsoup.connect("http://libcal.usc.edu/reserve/dml").get();
		Elements list= roomReservationPage.getElementsByClass("fc-timeline-event fc-h-event fc-event fc-start fc-end s-lc-eq-avail");
		System.out.println(list.toString());
	}

}
