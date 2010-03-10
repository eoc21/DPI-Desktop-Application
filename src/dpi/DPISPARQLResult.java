package dpi;

public class DPISPARQLResult {
	private String property;
	private double propertyValue;
	private String technique;
	private String condition;
	private String unit;
	private String polymerName;
	
	public DPISPARQLResult(){
	}
	
	public DPISPARQLResult(String propertyName, double propertyVal,String techniqueName,String conditionValue,String unitValue){
		this.property = propertyName;
		this.propertyValue = propertyVal;
		this.technique = techniqueName;
		this.condition = conditionValue;
		this.unit = unitValue;
	}
	
	public String getPropertyName(){
		return property;
	}
	
	public double getPropertyValue(){
		return propertyValue;
	}
	
	public String getTechnique(){
		return technique;
	}
	
	public String getCondition(){
		return condition;
	}
	
	public String getUnit(){
		return unit;
	}
	
	public String getPolymerName(){
		return polymerName;
	}
	public void setPropertyName(String propertyName){
		this.property = propertyName;
	}
	
	public void setPropertyValue(double value){
		this.propertyValue = value;
	}
	
	public void setTechnique(String techniqueValue){
		this.technique = techniqueValue;
	}
	
	public void setCondition(String conditionValue){
		this.condition = conditionValue;
	}
	
	public void setUnit(String unitValue){
		this.unit = unitValue;
	}
	
	public void setPolymerName(String polymer){
		this.polymerName = polymer;
	}
	
}
