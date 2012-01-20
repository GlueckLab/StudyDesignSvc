package HibernateTesting.sampletest;

public class SampleTest 
{
	private int testId;
	private String name;
	
	public SampleTest()
	{}
	
	public SampleTest(int testId, String name)
	{
		this.testId = testId;
		this.name = name;
	}

	public int getTestId() {
		return testId;
	}

	public void setTestId(int testId) {
		this.testId = testId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
