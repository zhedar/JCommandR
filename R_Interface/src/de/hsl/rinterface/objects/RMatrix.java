package de.hsl.rinterface.objects;


/** @pdOid 29978f29-da45-474b-9c60-54845abcaa61 */
public class RMatrix implements RObject {
	/** @pdOid a1d46f5d-b3f9-4b07-9894-50df3d61ac51 */
	protected String[][] matrix;
	private int rowLength;
	private int colLength;

	public RMatrix(int rowLength, int colLength){
		this.matrix= new String[rowLength][colLength];
		this.rowLength=rowLength;
		this.colLength=colLength;
	}
	
	public RMatrix(String[][] matrix){
		this.matrix=matrix;
	}
	
	public RMatrix(RMatrix matrix){
		this.matrix = matrix.getMatrix();
	}
	
	@Override
	public RObjectTypes getType() {
		return RObjectTypes.MATRIX;
	}
	
	public int getRowLength() {
		return rowLength;
	}

	public int getColLength() {
		return colLength;
	}

	@Override
	public String toRString() {
		String mat = "matrix(";
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (i != matrix.length && j != matrix[0].length)
					mat += matrix[j][i].toString() + ",";
				else
					mat += matrix[j][i].toString();
			}
		}
		mat += "), nrow=" + matrix.length + ",ncol=" + matrix[0].length + ")";
		return mat;
	}

	public String[][] getMatrix() {
		return matrix;
	}
	
	public String getMatrixAt(int indexRow, int indexCol){
		if(indexRow <= matrix.length && indexCol <= matrix[0].length)
			return matrix[indexRow][indexCol];
		else
			throw new IndexOutOfBoundsException();
	}

	public void setMatrix(String[][] matrix) {
		this.matrix = matrix;
	}
	
	public void setMatrixLine(int index, String[] line){
		if(index <= matrix.length)
			matrix[index]=line;
		else
			throw new IndexOutOfBoundsException();
	}
	
	public void setMatrixAt(int indexRow, int indexCol, String value){
		if(indexRow <= matrix.length && indexCol <= matrix[0].length)
			matrix[indexRow][indexCol]=value;
		else
			throw new IndexOutOfBoundsException();
	}

	@Override
	public String toString() {
		String result = "";
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				result += matrix[i][j] + " ";
			}
			result += "\n";
		}
		return result;
	}
}