package dpi;

import java.awt.Color;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Node;
import nu.xom.Nodes;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

import com.hp.hpl.jena.query.ResultSet;

import dpi.DPISPARQLResult;

/**
 * Class to represent the SPARQL results in a table in a new frame. 
 * @author ed
 *
 */
public class ResultsFrame extends JFrame {
	private Vector<String>headerInformation;
	private Vector<Vector<String>> resultsInformation;
	
	public ResultsFrame() throws ValidityException, ParsingException, IOException{
		setTitle("Results");
		this.setSize(1000, 1000);
		JPanel center = new JPanel( new FlowLayout(FlowLayout.LEFT) );
		center.setSize(900, 900);
		//JTable resultsTable = basicTable();
		//center.add(resultsTable);
		Document doc = readInData();
		headerInformation = getJTableColumnHeaders(doc);
		resultsInformation = getJTableData(doc);
		JTable resultsTable = new JTable(resultsInformation,headerInformation);
		resultsTable.setSize(800, 800);
	//	resultsTable.setAutoResizeMode(JTable.);
		resultsTable.setAutoCreateRowSorter(true);
		JTableHeader header = resultsTable.getTableHeader();
	    header.setBackground(Color.red);
		JScrollPane pane = new JScrollPane(resultsTable);
		center.add(pane);
		add(center);
		setVisible(true);
	}
	
	private JTable basicTable(){
		String[] columnNames = {"First Name",
                "Last Name",
                "Sport",
                "# of Years",
                "Vegetarian"};

		Object[][] data = {
			    {"Mary", "Campione",
			     "Snowboarding", new Integer(5), new Boolean(false)},
			    {"Alison", "Huml",
			     "Rowing", new Integer(3), new Boolean(true)},
			    {"Kathy", "Walrath",
			     "Knitting", new Integer(2), new Boolean(false)},
			    {"Sharon", "Zakhour",
			     "Speed reading", new Integer(20), new Boolean(true)},
			    {"Philip", "Milne",
			     "Pool", new Integer(10), new Boolean(false)}
			};
		JTable table = new JTable(data, columnNames);

		return table;
	}
	
	public  JTable getQueryResultsTable(ResultSet results){
		return null;
	}
	/**
	 * Method to read in SPARQL XML results file.
	 * @return XOM Document
	 * @throws ValidityException
	 * @throws ParsingException
	 * @throws IOException
	 */
	private Document readInData() throws ValidityException, ParsingException, IOException{
		Builder builder = new Builder();
        Document doc = builder.build("ResultOutput.xml");
        return doc;
	}
	/**
	 * Method to extract JTable column titles.
	 * @param doc - XOM Document to extract headers.
	 * @return Vector of header information.
	 */
	private Vector<String> getJTableColumnHeaders(Document doc){
		Element root = doc.getRootElement();
		Node headerInformation = root.getChild(1);
		Vector<String> columnTitles = new Vector<String>();
		//New addition of polymer name
		columnTitles.add("IUPAC Name");
		for(int i=0;i<headerInformation.getChildCount();i++){
			if( i%2  !=0){
				columnTitles.add(headerInformation.getChild(i).query("attribute::name").get(0).getValue());
			}
		}
		return columnTitles;
	}
	
	private Vector<Vector<String>> getJTableData(Document doc) throws IOException{
		HashMap<String, String> mapofIds = populatePolymerIdMap();
		Element root = doc.getRootElement();
		Node childThree = root.getChild(3);
		Vector<Vector<String>> individualRows = new Vector<Vector<String>>();
		int counter = 0;
		for(int i=0;i<childThree.getChildCount();i++){
			if(i%2 !=0){
				Nodes nd = childThree.getChild(i).query("child::*");
				DPISPARQLResult sparqlHit = new DPISPARQLResult();
				for(int j=0;j<nd.size();j++){
					Nodes v = nd.get(j).query("@name");
					if(v.get(0).getValue().toString().equals("x")){
						String[] uriValue = nd.get(j).getValue().split("#");
						sparqlHit.setPropertyName(uriValue[1]);
						//In development
						String[] polymerName = uriValue[1].split("_");
						sparqlHit.setPolymerName(mapofIds.get(polymerName[0]));
					}
					else if(v.get(0).getValue().toString().equals("hasValue")){
						sparqlHit.setPropertyValue(Double.parseDouble(nd.get(j).getValue()));
					}
					else if(v.get(0).getValue().toString().equals("condition")){
						sparqlHit.setCondition(nd.get(j).getValue());
					}
					else if(v.get(0).getValue().toString().equals("technique")){
						sparqlHit.setTechnique(nd.get(j).getValue());
					}
					else if(v.get(0).getValue().toString().equals("hasUnit")){
						sparqlHit.setUnit(nd.get(j).getValue());
					}
				}
				Vector<DPISPARQLResult> aVector = new Vector<DPISPARQLResult>();
				aVector.add(sparqlHit);
				Vector<String>aStringVector = new Vector<String>();
				aStringVector.add(sparqlHit.getPolymerName());
				aStringVector.add(sparqlHit.getPropertyName());
				aStringVector.add(Double.toString(sparqlHit.getPropertyValue()));
				aStringVector.add(sparqlHit.getTechnique());
				aStringVector.add(sparqlHit.getCondition());
				aStringVector.add(sparqlHit.getUnit());
				individualRows.add(counter, aStringVector);
				counter++;
			}
		}
//		System.out.println(individualRows.get(0).get(0).getPropertyName());
		//Need to process this into a single vector.
		return individualRows;
	}

	public Vector<String> getHeaderInformation(){
		return headerInformation;
	}
	
	public Vector<Vector<String>> getData4Table(){
		return resultsInformation;
	}
	
	private HashMap<String,String> populatePolymerIdMap() throws IOException{	
		BufferedReader br = new BufferedReader(new FileReader("UniquePolymerIds.txt"));
		HashMap<String,String> hm = new HashMap<String,String>();
		String line;
		while((line = br.readLine()) != null){
			String[] idAndName = line.split(" ");
			hm.put(idAndName[0], idAndName[1]);
		}
		return hm;
	}
	
}
