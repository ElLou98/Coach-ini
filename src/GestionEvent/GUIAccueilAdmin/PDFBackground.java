/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionEvent.GUIAccueilAdmin;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

class PDFBackground extends PdfPageEventHelper {

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        try {
            Image background = Image.getInstance("src/GestionEvent/Icons/background.jpg");
            // This scales the image to the page,
            // use the image's width & height if you don't want to scale.
            float width = document.getPageSize().getWidth();
            float height = document.getPageSize().getHeight();
            try {
                writer.getDirectContentUnder()
                        .addImage(background, width, 0, 0, height, 0, 0);
            } catch (DocumentException ex) {
                Logger.getLogger(PDFBackground.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (BadElementException ex) {
            Logger.getLogger(PDFBackground.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PDFBackground.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}