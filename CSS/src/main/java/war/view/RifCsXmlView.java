package war.view;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;

import com.lowagie.text.Document;

 
public class RifCsXmlView implements View {

	@Override
	public String getContentType() {
		return "text/xml";
	}

	@Override
	public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

			// Retrieve data from model
			Document doc = (Document) model.get("xml");
			doc.addTitle("lol");
			
			// Write the XML document to the reponse output stream
			/*OutputStream out = response.getOutputStream();
		    try {
				Serializer serializer = new Serializer(out, "utf-8");
				serializer.setIndent(4);
				serializer.setMaxLength(64);
				serializer.write(doc);
			} catch (IOException ex) {
				log.error(ex);
			}*/
	}
	
}