package rest.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import rest.UtilsMethods;

public abstract class BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2156973367342327643L;


	// property
	private int id;
	/**
	 * Lista koja sadrzi naziv svih kolona.
	 */
	protected List<String> columnsName;
	
	// constant
	public static final String ID = "id";
	
	public BaseEntity(){
		this.columnsName = new ArrayList<String>();
		this.columnsName.add(ID);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseEntity other = (BaseEntity) obj;
		if (id != other.id)
			return false;
		return true;
	}

	/**
	 * Metoda koja vraca nazive kolona koje entity ima tabeli.
	 * @return
	 */
	public List<String> columnsName(){
		return this.columnsName;
	}
	/**
	 * Metoda koja 
	 * @param columnName
	 * @param value
	 */
	public void setValueForColumnName(String columnName, Object value){
		if( ID.equals(columnName) ){
			setId(UtilsMethods.saftyConversionInt(value));
		}
	}
	
	/**
	 * Metoda koja vraca vrednost za prosledjeni naziv kolone.
	 * @param columnName
	 */
	public Object getValueForColumnName(String columnName){
		if( ID.equals(columnName) ){
			return this.id;
		}
		
		return null;
	}

	// getter and setter
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
}
