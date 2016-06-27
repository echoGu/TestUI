package collections;

import java.util.HashMap;

public class People {
	private String name;
	private int age;

	public People(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}



	public int getAge() {
		return age;
	}



	public void setAge(int age) {
		this.age = age;
	}



	@Override
	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + age;
//		result = prime * result + ((name == null) ? 0 : name.hashCode());
//		return result;
		
		return name.hashCode()*27 + age;
	}



	@Override
	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		People other = (People) obj;
//		if (age != other.age)
//			return false;
//		if (name == null) {
//			if (other.name != null)
//				return false;
//		} else if (!name.equals(other.name))
//			return false;
//		return true;
		
		return this.name.equals(((People)obj).name) && this.age== ((People)obj).age;
	}



	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		People p1 = new People("Jack", 12);
		System.out.println(p1.hashCode());
		
		HashMap<People, Integer> hashMap = new HashMap<People, Integer>();
		hashMap.put(p1, 1);
		
//		p1.setAge(13);
		
		System.out.println(hashMap.get(p1));

	}

}
