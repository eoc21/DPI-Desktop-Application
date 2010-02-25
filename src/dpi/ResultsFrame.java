package dpi;

import java.awt.FlowLayout;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

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
	private Vector<Vector<DPISPARQLResult>> resultsInformation;
	
	public ResultsFrame() throws ValidityException, ParsingException, IOException{
		setTitle("Results");
		this.setSize(600, 600);
		JPanel center = new JPanel( new FlowLayout(FlowLayout.LEFT) );
		center.setSize(500, 500);
		JTable resultsTable = basicTable();
		center.add(resultsTable);
		Document doc = readInData();
		headerInformation = getJTableColumnHeaders(doc);
		resultsInformation = getJTableData(doc);
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
		for(int i=0;i<headerInformation.getChildCount();i++){
			if( i%2  !=0){
				columnTitles.add(headerInformation.getChild(i).query("attribute::name").get(0).getValue());
			}
		}
		return columnTitles;
	}
	
	private Vector<Vector<DPISPARQLResult>> getJTableData(Document doc){
		Element root = doc.getRootElement();
		Node childThree = root.getChild(3);
		Vector<Vector<DPISPARQLResult>> individualRows = new Vector<Vector<DPISPARQLResult>>();
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
				individualRows.add(counter, aVector);
				counter++;
			}
		}
		System.out.println(individualRows.get(0).get(0).getPropertyName());
		return individualRows;
	}

	public Vector<String> getHeaderInformation(){
		return headerInformation;
	}
	
	public Vector<Vector<DPISPARQLResult>> getData4Table(){
		return resultsInformation;
	}
}
