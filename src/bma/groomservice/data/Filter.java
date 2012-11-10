package bma.groomservice.data;

public class Filter {

	/** opérateur */
	public String op;
	/** Nom du filtre */
	public String name;
	/** Valeur à appliquer au filtre */
	public Object value;

	public Filter(String op, String name, Object value) {
		super();
		this.op = op;
		this.name = name;
		this.value = value;
	}

	public static Filter eq(String name, Object valeur) {
		return new Filter("eq", name, valeur);
	}

	public static Filter lt(String name, double borneSup) {
		return new Filter("lt", name, String.valueOf(borneSup));
	}

	public static Filter gt(String name, double borneInf) {
		return new Filter("gt", name, String.valueOf(borneInf));
	}
}
