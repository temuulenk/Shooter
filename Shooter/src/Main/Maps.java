package Main;

public class Maps {
	
	String name;
	String[][] map;
	int totalCols, totalRows;
	
	public Maps(String _name, int y, int x) {
		totalCols = x;
		totalRows = y;
		name = _name;
		map = new String[totalRows][totalCols];
		
		for(int row=0;row<totalRows;row++){
			for(int col=0;col<totalCols;col++){
				if(row == 0 || row == totalRows - 1 || col == 0 || col == totalCols - 1) map[row][col] = "b";
				else map[row][col] = "w";
			}
		}
		
	}
	
	public String getName(){
		return name;
	}
	
	public void setMap(String[][] m){
		map = m;
	}
	
	public String[][] map(){
		return map;
	}
	
	public String content(){
		String fin = "";
		for(int row=0;row<totalRows;row++){
			for(int col=0;col<totalCols;col++){
				fin += map[row][col];
			}
			fin += ":";
		}
		return Menu.name + "," + totalRows + "," + totalCols + "," + fin;
	}
	
	
	public int getRows(){ return totalRows; }
	public int getCols() { return totalCols; }
	
	
	

}
