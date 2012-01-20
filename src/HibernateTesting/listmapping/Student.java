package HibernateTesting.listmapping;

import java.util.List;

public class Student 
{
	private int id;
	int schoolBench;
	private List<NameList> nameList;
	
	public Student()
	{}
	
	public Student(int id, int schoolBench) {
		super();
		this.id = id;
		this.schoolBench = schoolBench;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSchoolBench() {
		return schoolBench;
	}
	public void setSchoolBench(int schoolBench) {
		this.schoolBench = schoolBench;
	}
	public List<NameList> getNameList() {
		return nameList;
	}
	public void setNameList(List<NameList> nameList) {
		this.nameList = nameList;
	}
		
}
