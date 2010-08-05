package dpi;

import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
/**
 * Simple application to show SPARQL querying over the DPI ontologies.
 * @author ed
 *
 */
public class DPIDesktopApplication extends JFrame {
	private static final long serialVersionUID = 1L;
	private static Model dpiRdfModel = ModelFactory.createMemModelMaker().createModel(null);
	private static JTextField exactValueBox = new JTextField(30);
	private static JTextField minimumValueBox = new JTextField(30);
	private static JTextField maximumValueBox  = new JTextField(30);
	private static JTextField techniqueBox  = new JTextField(30);
	private static JTextField conditionBox  = new JTextField(30);
	private static JTextField unitBox  = new JTextField(30);
	private static JTextField propertySelected = new JTextField(30);
	private static JTextField modifierSelected = new JTextField(30);
	
	public DPIDesktopApplication() throws IOException {
		System.out.println("Loading owl file, this make take a minute!");
		setTitle("DPI SPARQL Demo");
		this.setSize(400, 400);
		JPanel center = new JPanel( new FlowLayout(FlowLayout.LEFT) );
		center.setSize(300, 400);
		String[] propertyData = PopulateComboBoxes.populateProperties();
		String[] equalityBox = PopulateComboBoxes.populateEqualityBox();
		final JComboBox propertyList = new JComboBox(propertyData);
		final JComboBox equalityList = new JComboBox(equalityBox);
		propertyList.setSelectedIndex(0);
		equalityList.setSelectedIndex(0);
		propertyList.addItemListener(new ItemListener(){
		    public void itemStateChanged(ItemEvent arg0) {
				 String str = (String)propertyList.getSelectedItem();
				 propertySelected.setText(str);  
			}});
		equalityList.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent arg0) {
				 String str = (String)equalityList.getSelectedItem();
				 modifierSelected.setText(str);  
			}});
		readOntologyIntoModel();
		JButton submit = new JButton("Submit");
        submit.addMouseListener(new OurClickHandler());
        JButton close = new JButton("Clear");
        close.addMouseListener(new ClearClickHandler());
        center.add(new JLabel("Select property:"));
        center.add(propertyList);
        center.add(new JLabel("Modifier"));
        center.add(equalityList);
        center.add(new JLabel("Exact value:"));
        center.add(exactValueBox);
        center.add(new JLabel("Min:"));
        center.add(minimumValueBox);
        center.add(new JLabel("Max:"));
        center.add(maximumValueBox);
        center.add(new JLabel("Technique:"));
        center.add(techniqueBox);
        center.add(new JLabel("Condition"));
        center.add(conditionBox);
        center.add(new JLabel("Unit"));
        center.add(unitBox);
        center.add(submit);
        center.add(close);
        center.setVisible(true);
        add(center);
    	setVisible(true);
	}

	/**
	 * Method to read in ontology on start up.
	 */
	private void readOntologyIntoModel(){
		try {
			InputStream in = new FileInputStream(new File("DPIDemoOntology.owl"));
			dpiRdfModel.read(in,null);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @return RDF Model
	 */
	public static Model getModel(){
		return dpiRdfModel;
	}
	/**
	 * 
	 * @return String representation of user input for the unit
	 * to query.
	 */
	public static String getUnit(){
		return unitBox.getText();
	}
	/**
	 * 
	 * @return String representation of content in property selection.
	 */
	public static String getPropertySelected(){
		return propertySelected.getText();
	}
	/**
	 * 
	 * @return String representation of modifier selected.
	 */
	public static String getModifierSelected(){
		return modifierSelected.getText();
	}
	/**
	 * 
	 * @return String representation of user input for the technique
	 * to query.
	 */
	public static String getTechnique(){
		return techniqueBox.getText();
	}
	/**
	 * 
	 * @return String representation of user input for the condition
	 * to query.
	 */
	public static String getCondition(){
		return conditionBox.getText();
	}
	/**
	 * 
	 * @return String value for exact property value search.
	 */
	public static String getValue(){
		/*double value;
		try{
			value = Double.parseDouble(exactValueBox.getText());
		}
		catch(NumberFormatException e){
			System.out.println("You need to enter a valid number!");
			e.printStackTrace();
			throw new NumberFormatException();
		}*/
		return exactValueBox.getText();
	}
	/**
	 * 
	 * @return String value for minimum value in a search by range.
	 */
	public static String getMaximum(){
		return maximumValueBox.getText();
	}
	/**
	 * 
	 * @return String value for maximum value in a search by range.
	 */
	public static String getMinimum(){
		return minimumValueBox.getText();
	}
	
	//Methods to clear content in the text box.
	/**
	 * Sets the exact value textbox.
	 * @param inputValue - String value to set textbox.
	 */
	public static void setExactTextBoxValue(String inputValue){
		exactValueBox.setText(inputValue);
	}
	/**
	 * Sets the maximum value textbox.
	 * @param inputValue - String value to set textbox.
	 */
	public static void setMaxTextBoxValue(String inputValue){
		maximumValueBox.setText(inputValue);
	}
	/**
	 * Sets the minimum value textbox.
	 * @param inputValue - String value to set textbox.
	 */
	public static void setMinTextBoxValue(String inputValue){
		minimumValueBox.setText(inputValue);
	}
	/**
	 * Sets the technique textbox.
	 * @param inputValue - String value to set textbox.
	 */
	public static void setTechniqueBoxValue(String inputValue){
		techniqueBox.setText(inputValue);
	}
	/**
	 * Sets the condition textbox.
	 * @param inputValue - String value to set textbox.
	 */
	public static void setConditionBoxValue(String inputValue){
		conditionBox.setText(inputValue);
	}
	/**
	 * Sets the unit textbox.
	 * @param inputValue - String value to set textbox.
	 */
	public static void setUnitBoxValue(String inputValue){
		unitBox.setText(inputValue);
	}
	
	/**
	 * 
	 * @param args
	 * @throws IOException
	 */
		
	public static void main (String [] args) throws IOException{
		new DPIDesktopApplication();
	}
}
