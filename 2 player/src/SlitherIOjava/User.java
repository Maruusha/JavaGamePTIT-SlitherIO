package SlitherIOjava;

import java.nio.file.DirectoryNotEmptyException;

public class User {
	private String name;
	private String diem;
	public User(String name, String diem) {
		this.name = name;
		this.diem = diem;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPoint() {
		return diem;
	}
	public void setPoint(String diem) {
		this.diem = diem;
	}
	
	public String toString() {
		return name + "   " + diem;
	}
}
