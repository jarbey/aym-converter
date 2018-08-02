package aym;

import java.io.File;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

import com.aspose.slides.ISlide;
import com.aspose.slides.Presentation;

public class PPTConverter {
	
	private static Options getCommandOptions() {
		// create the Options
		Options options = new Options();
		options.addOption("i", "inpuit", true, "File to convert");
		options.addOption("o", "output", true, "Archive to create with images");
		
		return options;
	}

	public static void main(String[] args) {
		try {
			// create the command line parser
			CommandLineParser parser = new DefaultParser();
			
		    // parse the command line arguments
		    CommandLine line = parser.parse(getCommandOptions(), args);

		    // validate that block-size has been set
		    if (line.hasOption('i') & line.hasOption('o')) {
		    	Presentation pres = new Presentation(line.getOptionValue('i'));

				File f = new File(line.getOptionValue('o'));
				ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f));
				
				int i = 1;
				int total = pres.getSlides().size();
				for (ISlide slide : pres.getSlides()) {
					System.out.println(i + "/" + total);
										
					ZipEntry e = new ZipEntry(i + ".jpg");
					out.putNextEntry(e);
					
					//Write the image into the file
					ImageIO.write(slide.getThumbnail(1.0f, 1.0f), "JPEG", out);
					out.closeEntry();

					i++;				
				}
				
				out.close();
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
