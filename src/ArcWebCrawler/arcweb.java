
package ArcWebCrawler;
import java.io.IOException;  
import java.io.*;

import org.jsoup.Jsoup;  
import org.jsoup.nodes.Document;  
import org.jsoup.nodes.Element;  
import org.jsoup.select.Elements;  
 
public class arcweb {
    public static void main( String[] args ) throws IOException{
		//selected letter is G
		Document doc;
		//creating a text file for output
		PrintWriter writer = new PrintWriter("product-G(new).txt", "UTF-8");
		writer.println("Product details of all products stating with H ");
		try{
			String baseurl ="http://www.cvedetails.com";
			String url ="http://www.cvedetails.com/product-list/firstchar-G/vendor_id-0/products.html";
			doc = Jsoup.connect(url).get();
			//System.out.print(doc);
			Element pageslist = doc.getElementById("pagingb");  
	        Elements pagelinks = pageslist.select("a[href]");
	        for (Element link : pagelinks) {  
	            System.out.println("\nlink : " + link.attr("href"));  
	            System.out.println("text : " + link.text());  
	            String links = link.attr("href");
	            try{
	            	//opening pages that contains product table
	            	Document page = Jsoup.connect(baseurl+links).get();
	            	Element producttable = page.select("table[class=listtable]").first();
	            	
	            	//System.out.println(producttable);
	            	//selecting rows from table
	            	Elements rows = producttable.select("tr");
	            	//skip first 2 rows as they are not useful
	            	for(int i=2;i<rows.size();i++){
	            		Element row  = rows.get(i);
	            		Element tabledata = row.select("td").first();
	            		
	            		Element productlink = tabledata.select("a[href]").first();
	            		String producturl = "http:"+productlink.attr("href");
	            		
	            		try{
	            			//opening product page
	            			Document productpage = Jsoup.connect(producturl).get();
	            			System.out.println("\n\n"+productpage.title());
	            			writer.println("\n\n"+productpage.title());
	            			Element statstable = productpage.select("table[class=stats]").first();
	            			
	            			//select table containing stastics
	            			Elements statrows = statstable.select("tr");
	            			
	            			Element headrow = statrows.get(0);
	            			Element valuerow = statrows.get(statrows.size()-1);
	            			
	            			Elements tableheading = headrow.select("th");
	            			Elements values = valuerow.select("td");
	            			
	            			for(int i1=1;i1<tableheading.size();i1++){
	            				//printing heading of table
	            				Element heading = tableheading.get(i1);
	            				//printing value of last row
	            				Element value = values.get(i1-1);
	            				System.out.println("\n"+heading.text()+" ---> "+value.text());
	            				writer.println("\n"+heading.text()+" ---> "+value.text());

	            				
	            				
	            			}
	            					
	            			
	            		}catch(Exception e){
	    	    			System.out.println(e); 
	    	    			e.printStackTrace();
	    	    		}
	            				
	            	}
	            	
	            	
	            }
	            catch(Exception e){
	    			System.out.println(e); 
	    			e.printStackTrace();
	    		}
	        }  
		}catch(Exception e){
			System.out.println(e); 
			e.printStackTrace();
		}
        
        writer.close();
    
}
}
