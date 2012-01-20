package HibernateTesting.listmapping;

import java.util.List;

public class NameList 
{
	private int id;	
	private int listId;		
	private String name;	
	
	public NameList(){}

	public NameList(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public NameList(int id, int listId, String name) {
		super();
		this.id = id;
		this.listId = listId;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getListId() {
		return listId;
	}

	public void setListId(int listId) {
		this.listId = listId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
		
}
