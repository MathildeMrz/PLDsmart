package org.H4212;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MedicineApi
{
    
	public HashSet<RomediIRI> getRomediIRI2Remove() throws IOException {
		HashSet<RomediIRI> iri2remove = new HashSet<RomediIRI>();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream in = classLoader.getResourceAsStream(ConfigRomedi.uriExcluded);
		BufferedReader br = new BufferedReader(new InputStreamReader(in, Charset.defaultCharset()));
		String line ;
		while ((line = br.readLine()) !=null) {
			RomediIRI romediIRI = new RomediIRI(line);
			iri2remove.add(romediIRI);
		}
		br.close();
		in.close();
		return(iri2remove);
	}
	
	private DetectDrug detectDrug;
	
	public DetectDrug getDetectDrug() {
		return(detectDrug);
	}
	
	/**
	 * Wanted RomediType (only retrieve the labels and code of these types)
	 */
	private RomediType[] romediTypes = {RomediType.BN, RomediType.IN, RomediType.PIN, RomediType.BNdosage};
	
	/**
	 * A Romedi SPARQL endpoint to retrieve the terminology
	 * 
	 * @param endpoint
	 * @throws IOException
	 * @throws URISyntaxException 
	 * @throws ScriptException 
	 */
	
	public MedicineApi(String endpoint) throws IOException, ScriptException, URISyntaxException {
		// Retrieve the terminology from an endpoint
		RomediTerminologySPARQL romediTerminologySPARQL = new RomediTerminologySPARQL(endpoint,romediTypes);
		RomediTerminology romediTerminology = romediTerminologySPARQL.getRomediTerminology();
		// remove unwanted URI (ambiguous words such 'eau', 'thym'...)
		romediTerminology.removeURI(getRomediIRI2Remove());
		
		Stopwords stopwordsRomedi = DetectDrug.getStopwordsRomedi();
		
		// new instance to detect drug
		detectDrug = new DetectDrug(romediTerminology, stopwordsRomedi);
		
		// Lucene Index creation to detect typo
		detectDrug.createLuceneIndex(romediTerminology, stopwordsRomedi);
		detectDrug.addLuceneTypoDetection();
	}
			
    public static void main( String[] args ) throws IOException, ScriptException, URISyntaxException, UnfoundTokenInSentence, ParseException
    {
    	String endpoint = ConfigEndpoint.chosenEndpoint; // autodetect the endpoint
    	System.out.println("ChosenEndpoint:" + endpoint);
    	MedicineApi MedicineApi = new MedicineApi(endpoint);
    	String text = "Traitement à l'admission: MTX, dompéridonne et escitalopram et donormil";
    	Set<CTcode> CTcodes = MedicineApi.getDetectDrug().getCTcodes(text);
    	for (CTcode CTcode : CTcodes) {
    		// extract whatever you want from CTcode
    		System.out.println(CTcode.getJSONobject().toString());
    	}	
		
		// Brat output example (in the console):
    	String bratType = "drug";
		BratDocumentWriter bratDocumentWriter = new BratDocumentWriter(new PrintWriter(System.out));
		BratDocument doc = new BratDocument();
		for (CTcode codes : CTcodes) {
			CTbrat ctbrat = new CTbrat(codes, bratType);
			doc.addAnnotation(ctbrat.getBratEntity());
		}
		bratDocumentWriter.write(doc);
		bratDocumentWriter.close();
		CTbrat.resetIdNumber(); // for the next document : reset the IdNumber to 1
		System.out.println("------------------ End --------------------------");
    }
}