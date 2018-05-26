package rest.entities;

import rest.UtilsMethods;

public class Recept extends BaseEntity {

	// properties

	private String name;
	private String description;

	// constants
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";

	private static final long serialVersionUID = 1L;

	public Recept() {
		super();
		this.columnsName.add(NAME);
		this.columnsName.add(DESCRIPTION);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public void setValueForColumnName(String columnName, Object value) {
		if (NAME.equals(columnName)) {
			this.setName(UtilsMethods.saftyConversionToStr(value));
			return;
		}

		if (DESCRIPTION.equals(columnName)) {
			this.setDescription(UtilsMethods.saftyConversionToStr(value));
			return;
		}

		super.setValueForColumnName(columnName, value);
	}

	@Override
	public Object getValueForColumnName(String columnName) {
		if (NAME.equals(columnName)) {
			return this.getName();
		}

		if (DESCRIPTION.equals(columnName)) {
			return this.getDescription();
		}

		return super.getValueForColumnName(columnName);
	}

}
