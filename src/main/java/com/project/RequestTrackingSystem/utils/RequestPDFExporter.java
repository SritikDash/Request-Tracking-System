package com.project.RequestTrackingSystem.utils;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.project.RequestTrackingSystem.models.Requests;

public class RequestPDFExporter {
	private List<Requests> listRequests;

	public RequestPDFExporter(List<Requests> listRequests) {
		this.listRequests = listRequests;
	}

	private void writeTableHeader(PdfPTable table) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.GRAY);
		cell.setPadding(5);

		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);

		cell.setPhrase(new Phrase("Request Number", font));

		table.addCell(cell);

		cell.setPhrase(new Phrase("Request Title", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Request Description", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Assigned To", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Assigned by", font));
		table.addCell(cell);
	}

	private void writeTableData(PdfPTable table) {
		for (Requests req : listRequests) {
			table.addCell(req.getRequestNumber());
			table.addCell(req.getRequestTitle());
			table.addCell(req.getRequestDescription());
			table.addCell(req.getAssignedTo().getUserName());
			table.addCell(req.getCreatedBy().getUserName());
		}
	}

	public void export(HttpServletResponse response) throws DocumentException, IOException {
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.black);

		Paragraph p = new Paragraph("List of Requests", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(p);

		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 1.5f, 3.5f, 3.0f, 3.0f, 1.5f });
		table.setSpacingBefore(10);

		writeTableHeader(table);
		writeTableData(table);

		document.add(table);

		document.close();

	}

}
