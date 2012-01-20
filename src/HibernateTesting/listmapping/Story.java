package HibernateTesting.listmapping;

public class Story 
{
	  private int id;
	  private String info;

	  public Story(){
	  }

	  public Story(String info) {
	    this.info = info;
	  }

	  public void setId(int i) {
	    id = i;
	  }

	  public int getId() {
	    return id;
	  }

	  public void setInfo(String n) {
	    info = n;
	  }

	  public String getInfo() {
	    return info;
	  }
}
