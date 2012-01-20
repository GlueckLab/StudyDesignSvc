package HibernateTesting.listmapping;

import java.util.List;

public class Group 
{
	  private int id;
	  private String name;
	  private List stories;

	  public Group(){
	  }

	  public Group(String name) {
	    this.name = name;
	  }

	  public void setId(int i) {
	    id = i;
	  }

	  public int getId() {
	    return id;
	  }

	  public void setName(String n) {
	    name = n;
	  }

	  public String getName() {
	    return name;
	  }

	  public void setStories(List l) {
	    stories = l;
	  }

	  public List getStories() {
	    return stories;
	  }
	}
