package tapEx1;


public class Main {
	
	
	public static void main(String[] args) throws Exception {
				
		String url = new String("https://pt.wikipedia.org/wiki/Campina_Grande");
		Crawler c = new Crawler(url, 3);
		c.start();
		
	}

}
