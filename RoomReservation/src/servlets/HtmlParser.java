package servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

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
    
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			WebClient webClient =null;
		    try {  
		       webClient = new WebClient(BrowserVersion.CHROME);
		     //屏蔽日志信息
		     LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log",
		             "org.apache.commons.logging.impl.NoOpLog");
		     java.util.logging.Logger.getLogger("org.apache.http.client").setLevel(Level.OFF);  
		     java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
		     //支持JavaScript
		     webClient.getOptions().setJavaScriptEnabled(true);
		     webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		     webClient.getOptions().setCssEnabled(false);
		     webClient.getOptions().setActiveXNative(false);
		     webClient.getOptions().setCssEnabled(false);
		     webClient.getOptions().setThrowExceptionOnScriptError(false);
		     webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		     webClient.getOptions().setTimeout(10000);
		     HtmlPage rootPage = webClient.getPage("http://libcal.usc.edu/allspaces");
		     //设置一个运行JavaScript的时间
		     webClient.waitForBackgroundJavaScript(10000);
		     String html = rootPage.asXml();
		     Document doc = Jsoup.parse(html,"http://libcal.usc.edu/allspaces");
		     Elements list =doc.getElementsByClass("fc-timeline-event fc-h-event fc-event fc-start fc-end s-lc-eq-avail");
		    //显示所有空的房间
		       String[] weeks = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};  
		     Elements room =list.tagName("title");
		     	 Elements aTag = room.select("title[title]");
		     	 RoomList availRoom = new RoomList();
		     	 int initial =(aTag.get(0).attr("title").indexOf("-"))+1;
		     	 String intialLoc =aTag.get(0).attr("title").substring(initial, aTag.get(0).attr("title").length());
		     	Room obj= null;
		     	  ArrayList<String> date = null;
		     	 for(Element element :aTag){

		             String href=element.attr("title");
		             SimpleDateFormat df = new SimpleDateFormat("EEEE");//设置日期格式
		             String day =df.format(new Date());// new Date()为获取当前系统时间
		             int i=0;
		             for( i=0;i<weeks.length;i++) {
		            	 if(weeks[i].equals(day)) {
		            		 break;
		            	 }
		             }
		            if( href.indexOf(day)==-1) {
		            	if(i==weeks.length-1) {
		            		day=weeks[0];
		            	}
		            	else {
		            	day =weeks[i+1];
		            	}
		            }
		            if(href.indexOf(day)!=-1) {
		             System.out.println("===Available=== Room"+href);
		             int end = href.indexOf("-")-1;
		             int loc = href.indexOf("-")+1;
		           String time = href.substring(0,end);
		           String location =href.substring(loc,href.length());
		           if(element == aTag.get(0)){
		        	   obj= new Room();
				          date= new ArrayList<String>(); 
				          date.add(time);
				          obj.setName(location);
				           obj.setDate(date);
				           intialLoc = location;
				           availRoom.addRoom(obj);
		           }
		           else {
			           if (location.equals(intialLoc)) {
			        	   date.add(time);
			           }
			           else {
			        	    obj= new Room();
				          date= new ArrayList<String>(); 
				          obj.setName(location);
				           obj.setDate(date);
				           intialLoc = location;
				           availRoom.addRoom(obj);
			           }
		           }
		       
		           //obj.setDate(time);
		          // availRoom.addRoom(obj);
		           //System.out.println(time+" "+location);
		             continue;
		            }
		         }
		        /* for(int p =0;p<availRoom.size();p++) {
		        	System.out.println( availRoom.getRooms().get(p).getName());
		         }*/
		     	HttpSession session = request.getSession();
		     	session.setAttribute("roomAvail",availRoom);
		     	RequestDispatcher dispatch =getServletContext().getRequestDispatcher("/MainPage.jsp");
				dispatch.forward(request,response);
		    } catch (IOException e) {  
		        e.printStackTrace();  
		    }
		    finally {
		    	webClient.close();
		    }
		
	}

}
