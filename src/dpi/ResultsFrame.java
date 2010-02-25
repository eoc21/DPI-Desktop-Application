package dpi;

import java.awt.FlowLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

import com.hp.hpl.jena.query.ResultSet;
/**
 * Class to represent the SPARQL results in a table in a new frame. 
 * @author ed
 *
 */
public class ResultsFrame extends JFrame {

	public ResultsFrame(){
		setTitle("Results");
		this.setSize(600, 600);
		JPanel center = new JPanel( new FlowLayout(FlowLayout.LEFT) );
		center.setSize(500, 500);
		JTable resultsTable = basicTable();
		center.add(resultsTable);
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
	
	private void readInData() throws ValidityException, ParsingException, IOException{
		Builder builder = new Builder();
        Document doc = builder.build("ResultOutput.xml");
        
	}
}
