package edu.ist.psu.sagnik.research.pdfbox2playground.javatest;

/**
 * Created by sagnik on 6/28/16.
 */
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.action.PDAction;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionJavaScript;
import org.apache.pdfbox.pdmodel.interactive.action.PDFormFieldAdditionalActions;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.form.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class AcroFormFieldTest {

    /**
     * This will print all the fields from the document.
     *
     * @param pdfDocument The PDF to get the fields from.
     *
     * @throws IOException If there is an error getting the fields.
     */
    public void printFields(PDDocument pdfDocument) throws IOException
    {
        PDDocumentCatalog docCatalog = pdfDocument.getDocumentCatalog();
        PDAcroForm acroForm = docCatalog.getAcroForm();
        acroForm .flatten();
        List<PDField> fields = acroForm.getFields();

        //System.out.println(fields.size() + " top-level fields were found on the form");
        for (PDField field : fields)
        {
            System.out.println(field);
            processField(field, "|--", field.getPartialName());
        }
    }

    private void processField(PDField field, String sLevel, String sParent) throws IOException
    {
        String partialName = field.getPartialName();

        if (field instanceof PDTerminalField)
        {
            PDTerminalField termField = (PDTerminalField) field;

            PDFormFieldAdditionalActions fieldActions = field.getActions();
            if (fieldActions != null)
            {
                System.out.println(field.getFullyQualifiedName() + ": " + fieldActions.getClass().getSimpleName() + " js field actionS:\n" + fieldActions.getCOSObject());
                printPossibleJS(fieldActions.getK());
                printPossibleJS(fieldActions.getC());
                printPossibleJS(fieldActions.getF());
                printPossibleJS(fieldActions.getV());
            }
            for (PDAnnotationWidget widgetAction : termField.getWidgets())
            {
                PDAction action = widgetAction.getAction();
                if (action instanceof PDActionJavaScript)
                {
                    System.out.println(field.getFullyQualifiedName() + ": " + action.getClass().getSimpleName() + " js widget action:\n" + action.getCOSObject());
                    printPossibleJS(action);
                }
            }
        }

        if (field instanceof PDNonTerminalField)
        {
            if (!sParent.equals(field.getPartialName()))
            {
                if (partialName != null)
                {
                    sParent = sParent + "." + partialName;
                }
            }
            //System.out.println(sLevel + sParent);

            for (PDField child : ((PDNonTerminalField) field).getChildren())
            {
                processField(child, "|  " + sLevel, sParent);
            }
        }
        else
        {
            String fieldValue = field.getValueAsString();
            StringBuilder outputString = new StringBuilder(sLevel);
            outputString.append(sParent);
            if (partialName != null)
            {
                outputString.append(".").append(partialName);
            }
            outputString.append(" = ").append(fieldValue);
            outputString.append(", type=").append(field.getClass().getName());
            //System.out.println(outputString);
        }
    }

    private void printPossibleJS(PDAction kAction)
    {
        if (kAction instanceof PDActionJavaScript)
        {
            PDActionJavaScript jsAction = (PDActionJavaScript) kAction;
            String jsString = jsAction.getAction();
            if (!jsString.contains("\n"))
            {
                // avoid display problems with netbeans
                jsString = jsString.replaceAll("\r", "\n").replaceAll("\n\n", "\n");
            }
            System.out.println(jsString);
            System.out.println();
        }
    }

    /**
     * This will read a PDF file and print out the form elements. <br />
     * see usage() for commandline
     *
     * @param args command line arguments
     *
     * @throws IOException If there is an error importing the FDF document.
     */
    public static void main(String[] args) throws IOException
    {
        PDDocument pdf = null;
        try
        {
            pdf = PDDocument.load(new File(new DataLocation().pdLoc));
            AcroFormFieldTest exporter = new AcroFormFieldTest();
            exporter.printFields(pdf);
        }
        finally
        {
            if (pdf != null)
            {
                pdf.close();
            }
        }
    }

}
