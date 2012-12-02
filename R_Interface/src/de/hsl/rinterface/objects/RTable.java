package de.hsl.rinterface.objects;

import java.util.ArrayList;

public class RTable<E> implements RObject{
	
	ArrayList<String> colTitle;
	ArrayList<String> rowTitle;
	ArrayList<ArrayList<E>> table;
	
	public RTable() {
		super();
	}

	public RTable(ArrayList<String> colTitle, ArrayList<String> rowTitle,
			ArrayList<ArrayList<E>> table) {
		super();
		this.colTitle = colTitle;
		this.rowTitle = rowTitle;
		this.table = table;
	}

	public void addRowTitle(String s){
		rowTitle.add(s);
	}
	
	public void addColTitle(String s){
		colTitle.add(s);
	}
	
	
	
	public ArrayList<String> getcolTitle() {
		return colTitle;
	}

	public void setcolTitle(ArrayList<String> colTitle) {
		this.colTitle = colTitle;
	}

	public ArrayList<String> getRowTitle() {
		return rowTitle;
	}

	public void setRowTitle(ArrayList<String> rowTitle) {
		this.rowTitle = rowTitle;
	}

	public ArrayList<ArrayList<E>> getTable() {
		return table;
	}

	public void setTable(ArrayList<ArrayList<E>> table) {
		this.table = table;
	}

	@Override
	public String toRString() {
		String result ="";
		
		
		
		
		// Tabellenkopf hinzufügen
		if(colTitle!= null){
			result += " ; colnames( ";
			result+= "\""+ colTitle.get(0) + "\"";
			for (int i = 1; i < colTitle.size(); i++) {
				result+= "; \""+ colTitle.get(i) + "\"";
			}
			result += ")";
		}
		if(rowTitle!= null){
			result += " ; rownames( ";
			result+= "\""+ rowTitle.get(0) + "\"";
			for (int i = 1; i < rowTitle.size(); i++) {
				result+= "; \""+ rowTitle.get(i) + "\"";
			}
			result += ")";
		}
			
		return result;
	}

	@Override
	public RObjectTypes getType() {
		return RObjectTypes.TABLE;
	}
	
	@Override
	public String toString(){
		String result="";
		for (String cell : this.colTitle) {
			result += cell + "\\t";
		}
		result = "\n";
		for (int i = 0; i < rowTitle.size(); i++) {
			result += rowTitle.get(i) + "\\t";
			for (E cell : table.get(i)) {
				result += cell.toString() + "\\t";
			}
			result+="\n";
		}	
		return result;
	}
	
	public static void main(String[] args) {
		RTable<String> t1 = new RTable<>();
		
		System.out.println(t1.toRString());
	}

}
