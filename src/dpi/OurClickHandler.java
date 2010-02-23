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
		double filterValue = DPIDesktopApplication.getValue();
		String condition = DPIDesktopApplication.getCondition();
		String aUnit = DPIDesktopApplication.getUnit().trim();
		String technique = DPIDesktopApplication.getTechnique().trim();
		double maximumValue = DPIDesktopApplication.getMaximum();
		double minimumValue = DPIDesktopApplication.getMinimum();
		String propertySelected = DPIDesktopApplication.getPropertySelected().trim();
		String modifierSelected = DPIDesktopApplication.getModifierSelected();
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
			"FILTER(?hasValue >"+filterValue+")"+
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
