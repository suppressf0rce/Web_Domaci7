package rest.entities;

import rest.UtilsMethods;

public class Operater extends BaseEntity{

	// properties

	
		private String username;
		private String password;
		
		// constants
		public static final String USERNAME = "username";
		public static final String PASSWORD = "password";

		private static final long serialVersionUID = 1L;
	
	public Operater() {
		super();
		this.columnsName.add(USERNAME);
		this.columnsName.add(PASSWORD);
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public void setValueForColumnName(String columnName, Object value) {
		if( USERNAME.equals(columnName) ){
			this.setUsername(UtilsMethods.saftyConversionToStr(value));
			return;
		}
		
		if( PASSWORD.equals(columnName) ){
			this.setPassword(UtilsMethods.saftyConversionToStr(value));
			return;
		}
		
		super.setValueForColumnName(columnName, value);
	}
	
	@Override
	public Object getValueForColumnName(String columnName){
		if( USERNAME.equals(columnName) ){
			return this.getUsername();
		}
		
		if( PASSWORD.equals(columnName) ){
			return this.getPassword();
		}
		
		return super.getValueForColumnName(columnName);
	}
	
}
