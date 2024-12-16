/**
 * D
 *
 * @author
 */

import java.io.File;
import java.text.MessageFormat;
import java.util.Scanner;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;


public class BillGenerator {
    static Scanner sc = new Scanner(System.in);

    public static void printBill(String name, double totalBill, int totalUnits, int prevUnits, int currUnits, int balanceTenantUnits, double ratePerUnit, int tenantCharge) throws IOException {
        PDDocument doc = new PDDocument();
        PDPage blankPage = new PDPage();
        doc.addPage(blankPage);
        PDPageContentStream contentStream = new PDPageContentStream(doc, blankPage);
        contentStream.beginText();
        PDType1Font font = new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN);
        contentStream.setFont(font, 12);
        contentStream.setLeading(14.5f);

        contentStream.newLineAtOffset(20, 450);
        String time = java.time.LocalDate.now().toString();
        String text = "Today is " + time + " and this is a receipt of the electricity charge of " + name;
        String text2 = "Total bill is: " + totalBill + "INR. Total no of units consumed are:" + totalUnits;
        String text3 = "Rate per units is: " + ratePerUnit + "INR. Your units consumed in last cycle were:" + prevUnits;
        String text4 = "Your units consumed in this cycle are:" + currUnits + ". So your balance units are:" + balanceTenantUnits;
        String text5 = "Your net electricity bill is: " + ratePerUnit + " * " + balanceTenantUnits + " = " + tenantCharge + "INR";

        contentStream.showText(text);
        contentStream.newLine();
        contentStream.showText(text2);
        contentStream.newLine();
        contentStream.showText(text3);
        contentStream.newLine();
        contentStream.showText(text4);
        contentStream.newLine();
        contentStream.showText(text5);
        // contentStream.newLineAtOffset(25, 460);
        contentStream.endText();
        contentStream.close();
        System.out.println("Content added");

        //Saving the document
        String path = MessageFormat.format("/Users/parvbhardwaj/Desktop/{0}_s{1}_BillReport.pdf", name, time);
        doc.save(new File(path));
        //Closing the document
        doc.close();


    }

    /**
     * generates the bill
     *
     * @param name
     */
    public static void generateBill(String name) throws IOException {

        int totalUnitsConsumed = 0;
        int tenantUnitesConsumedCurrentCycle = 0;
        int tenantUnitesConsumedLastCycle = 0;
        int diffTenantUnits = 0;
        double totalElectricityBill = 0.0;
        double elecRatePerUnit = 0.0;
        int tenantCharge = 0;

        try {
            System.out.println("Enter the total electricity bill");
            totalElectricityBill = sc.nextDouble();
            System.out.println("Enter the total units consumed");
            totalUnitsConsumed = sc.nextInt();
            System.out.println("Enter the units consumed by " + name + " in the last bill cycle");
            tenantUnitesConsumedLastCycle = sc.nextInt();
            System.out.println("Enter the units consumed by " + name + " in this bill cycle");
            tenantUnitesConsumedCurrentCycle = sc.nextInt();
            diffTenantUnits = tenantUnitesConsumedCurrentCycle - tenantUnitesConsumedLastCycle;
            System.out.println("Total number of units consumed by " + name + "are: " + diffTenantUnits);
            elecRatePerUnit = totalElectricityBill / totalUnitsConsumed;
            System.out.println("Charge per unit is: " + elecRatePerUnit);
            tenantCharge = (int) (diffTenantUnits * elecRatePerUnit);
            System.out.println("Total Charge of " + name + "is: " + tenantCharge + "INR");


        } catch (Exception e) {
            System.out.println("Invalid input entered. Please restart the application");

        }

        System.out.println("Do you want a printed version of the Bill? Type no for No or anything else to continue.");
        Scanner tx = new Scanner(System.in);
        String answer = tx.nextLine();
        if (answer.contains("no")) {
            System.out.println("Thank for using the application.");
        } else {
            printBill(name, totalElectricityBill, totalUnitsConsumed, tenantUnitesConsumedLastCycle, tenantUnitesConsumedCurrentCycle, diffTenantUnits, elecRatePerUnit, tenantCharge);
        }
    }


    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the name of the tenant\n");
        String name = sc.nextLine();
        generateBill(name);
        // printBill();

    }
}
