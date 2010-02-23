package dpi;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;

public class OurClickHandler implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent arg0) {
		System.out.println("The button was clicked!");
		Model m = DPIDesktopApplication.getModel();
		String filterValue = DPIDesktopApplication.getValue();
		String condition = DPIDesktopApplication.getCondition();
		String aUnit = DPIDesktopApplication.getUnit().trim();
		String technique = DPIDesktopApplication.getTechnique().trim();
		String maximumValue = DPIDesktopApplication.getMaximum();
		String minimumValue = DPIDesktopApplication.getMinimum();
		String propertySelected = DPIDesktopApplication.getPropertySelected().trim();
		String modifierSelected = DPIDesktopApplication.getModifierSelected();
		//Can do a query based on a range in value or on one specific value.
		double exactFilterValue = 0;
		double maximumValueSelected = 0;
		double minimumValueSelected = 0;
		if(filterValue =="" && (maximumValue == "" || minimumValue == "")){
			System.out.println("Need to enter a value or range to query over!");
			throw new NullPointerException();
		}
		
		else if(filterValue !=""){
			try{
				exactFilterValue = Double.parseDouble(filterValue);				
			}
			catch(NumberFormatException e){
				System.out.println("You need to enter a valid number!");
				e.printStackTrace();
				throw new NumberFormatException();
			}
		}
		else if(filterValue == "" && maximumValue !="" && minimumValue !=""){
			try{
				maximumValueSelected = Double.parseDouble(maximumValue);
				minimumValueSelected = Double.parseDouble(minimumValue);
			}
			catch(NumberFormatException e){
				System.out.println("Either the max or minimum value is not a proper number!");
				e.printStackTrace();
				throw new NumberFormatException();
			}
		}
		
		
		
		String queryValues = 
			"PREFIX prop: <http://www.polymerinformatics.com/ChemAxiom/ChemAxiomProp.owl#> " +	
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
			"PREFIX metrology: <http://www.polymerinformatics.com/ChemAxiom/ChemAxiomMetrology.owl#>"+
			"SELECT *"+
			"WHERE {"+"?x prop:hasValue ?hasValue." +	
			"?x prop:hasMeasurementTechnique ?technique."+			
			"?x rdf:type <http://www.polymerinformatics.com/ChemAxiom/ChemAxiomProp.owl#"+propertySelected+">."+
			"?x metrology:hasCondition ?condition."+
			"?x prop:hasUnit ?hasUnit."+
			"FILTER(?hasValue >"+exactFilterValue+")"+
	//		"FILTER regex(?hasMeasurementTechnique,"+ "\""+technique+"\")"+
			//	"FILTER regex(?hasUnit, \"[C]\")"+
			"}"+
			"LIMIT 100";
		com.hp.hpl.jena.query.Query query = QueryFactory.create(queryValues);
		QueryExecution qe = QueryExecutionFactory.create(query, m);
		//Time the query
		long startTime = System.currentTimeMillis();
		ResultSet results = qe.execSelect();
		long endTime = System.currentTimeMillis();
		long totalTime =  endTime-startTime;
		// Output query results	
		ResultSetFormatter.out(System.out, results, query);
		qe.close();
		System.out.println("Total time:"+totalTime);
		System.out.println("Filter value:"+filterValue);
		System.out.println(technique+" technique");
		System.out.println(propertySelected);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

}
