package war.view;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import war.model.CsiroData;
import war.model.DataElement;
import war.model.DataElementCsiro;
import war.model.User;
import war.model.UserStory;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
 
public class UserStoryPdfView extends AbstractPdfView {
	
	private static Font h2 = new Font(Font.UNDEFINED, 20, Font.BOLD);
	private static Font h4 = new Font(Font.UNDEFINED, 16, Font.BOLD);
	
	//private static Font tableHeader = new Font(Font.UNDEFINED, 12, Font.BOLD);
	
	@Override
	protected void buildPdfDocument(@SuppressWarnings("rawtypes")Map model, Document document,PdfWriter writer, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
 
		User user = (User)(model.get("user"));
		UserStory userstory = (UserStory)(model.get("userstory"));
		
		document.addAuthor(user.getFirstname() + " " + user.getLastname());
		document.addCreationDate();
		document.addTitle(userstory.getName());
		response.addHeader("title", "Report");
		
		// Page Title
		document.add(new Paragraph(userstory.getName(), h2));
		//document.add(new Paragraph(userstory.getRegion().getName(), h4));
		document.add(Chunk.NEWLINE);
		
		// Statement about author
		document.add(new Paragraph("This report has been created by " + user.getFirstname() + " " + user.getLastname() + " (" + user.getEmail() + ") using the Climate Smart Seaports toolkit available at http://www.DOMAIN.com.au"));
		document.add(Chunk.NEWLINE);
		
		// Data Elements
		for (DataElement de : userstory.getDataElements()) {
			if (de.getIncluded()) {
				if (de.getClass().getSimpleName().equals("DataElementCsiro")) {
					DataElementCsiro deCsiro = (DataElementCsiro)de;
					displayCsiroDataElement(document, deCsiro);
				}
				document.add(Chunk.NEWLINE);
			}
		}
		
		
		
		
	}
	
	private void displayCsiroDataElement(Document document, DataElementCsiro de) throws DocumentException, IOException {
		// Data Element title
		document.add(new Paragraph(de.getName(), h4));
		document.add(Chunk.NEWLINE);
		
		// Number of columns
		int columnNumber = 4;
		/*if (!(de.getPicturesIncluded()))
			columnNumber--;*/
		
		// Table creation
		PdfPTable table = new PdfPTable(columnNumber);
		table.addCell("Variable");
		table.addCell("Baseline");
		table.addCell("Change for the " + de.getCsiroDataList().get(0).getYear() + "'s");
		/*if (de.getPicturesIncluded())
			table.addCell("Map");*/
		
		table.setWidthPercentage(100f);
        table.getDefaultCell().setPadding(3);
        table.getDefaultCell().setUseAscender(true);
        table.getDefaultCell().setUseDescender(true);
        //table.getDefaultCell().setBackgroundColor(Color.WHITE);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
			
		for (CsiroData data : de.getCsiroDataList()) {
			table.addCell(data.getVariable().getName());
			table.addCell(data.getBaseline().getValue().toString() + data.getVariable().getUom());
			table.addCell(data.getValue().toString() + data.getVariable().getUomVariation());
			/*if (de.getPicturesIncluded()) {
			if(data.getPicture() != null)
				table.addCell(Image.getInstance(data.getPicture()));
			else
				table.addCell("No map available");
			}*/
		}
		document.add(table);
	}
}