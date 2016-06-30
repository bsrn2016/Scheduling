package portfolio4_v10;

/**
 * @author Zakaria EI Boujattoui, Linus Städtler and Anh Phuc Hoang
 * @since 01.06.2016 
 * @version 5.0
 */
public class Prozess {
	
	private int ankuftsZeit,rechenZeit,tempLauf;
	private int warteZeit=0;
	private String id;											//name
    private static int counter = 1;
    public final int objectId;									//primary key
	private boolean[]marks;
	private boolean check=true;									//not needed
	//
	public Prozess(int aZeit,int lZeit,String id) {
		// TODO Auto-generated constructor stub
		this.id=id;
		this.ankuftsZeit=aZeit;
		this.rechenZeit=lZeit;
		this.tempLauf=lZeit;
		objectId=counter++;
	}
	//Getter and Setter
	public int getTempLauf() {
		return tempLauf;
	}

	public void setTempLauf(int tempLauf) {
		this.tempLauf = tempLauf;
	}

	public int getWarteZeit(){
		return warteZeit;
	}
	public void setWarteZeit(int warteZeit) {
		this.warteZeit = warteZeit;
	}
	public int getAnkunftsZeit() {
		return ankuftsZeit;
	}
	public void setAnkunftsZeit(int ankuftsZeit) {
		this.ankuftsZeit = ankuftsZeit;
	}
	public int getRechenZeit() {
		return rechenZeit;
	}
	public void setRechenZeit(int rechenZeit) {
		this.rechenZeit = rechenZeit;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean[] getMarks() {
		return marks;
	}
	public void setMarks(boolean[] marks) {
		this.marks = marks;
	}
	public boolean isCheck() {
		return check;
	}
	public void setCheck(boolean check) {
		this.check = check;
	}
	public int getObjectId() {
		return objectId;
	}
	
}