package tapEx1;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RoboTap extends Thread {
	public static final String LINK_REGEX = "<a\\s+(?:[^>]*?\\s+)?href=([\"'])(.*?\\.html)\\1";

	private String url;
	private List<String> linksEncontrados = new ArrayList<String>();
	private Set<String> linksVisitados = new HashSet<String>();
	private int maxNivel;
	private int nivel = 0;

	public RoboTap(String url, int maxNivel) {
		this.url = url;
		this.maxNivel = maxNivel;
	}

	public void run() {
		try {
			System.out.println("Robô começou a busca.");
			for (String link : findLinks()) {
				if (nivel >= maxNivel) {
					break;
				}

				if (!linksVisitados.contains(link)) {
					linksVisitados.add(link);
					RoboTap c = new RoboTap(link, maxNivel);
					c.findLinks();
					nivel++;
				}
				System.out.println("Estamos no  link: " + link);
			}
			System.out.println("o robô terminou sua busca.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<String> findLinks() {
		try {
			URL url = new URL(this.url);

			Scanner entrada = new Scanner(url.openStream());

			StringBuilder builder = new StringBuilder();

			while (entrada.hasNextLine()) {
				builder.append(entrada.nextLine());
			}

			String html = builder.toString();
			Pattern linkPattern = Pattern.compile(LINK_REGEX, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
			Matcher linkMatcher = linkPattern.matcher(html);

			while (linkMatcher.find()) {
				String link = linkMatcher.group(2);

				if (link.endsWith(".html")) {
					System.out.println("Link encontrado: " + link);
					linksEncontrados.add(link);
				}
			}
		} catch (Exception e) {
			System.err.println();
		}

		return linksEncontrados;
	}
}}
