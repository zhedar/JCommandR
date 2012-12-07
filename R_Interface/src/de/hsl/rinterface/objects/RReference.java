package de.hsl.rinterface.objects;

/** @pdOid 2bb12943-02c9-4e96-a12a-8b89dfc4c7d4 */
public class RReference implements RObject {

	private String ref;

	public RReference(String ref) {
		this.ref = ref;
	}

	public String getRef() {
		return ref;
	}

	@Override
	public RObjectTypes getType() {
		return RObjectTypes.REFERENCE;
	}

	@Override
	public String toRString() {
		return ref;
	}

}