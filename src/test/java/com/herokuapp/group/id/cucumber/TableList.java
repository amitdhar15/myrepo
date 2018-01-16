package com.herokuapp.group.id.cucumber;

import java.util.Comparator;
/**
 * 
 * @author Amit
 *
 */


public class TableList  implements Comparable<TableList>{

		private String lastName;
		private String firstName;
		private String email;
		private String salary;
		private String url;
		private String link;
				
		
		   public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getSalary() {
			return salary;
		}
		public void setSalary(String salary) {
			this.salary = salary;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getLink() {
			return link;
		}
		public void setLink(String link) {
			this.link = link;
		}
		public static Comparator<TableList> LastNameComparator = new Comparator<TableList>() {

	            @Override
	            public int compare(TableList e1, TableList e2) {
	                return e1.getLastName().compareTo(e2.getLastName());
	            }
	        };
	        
			public static Comparator<TableList> FirstNameComparator = new Comparator<TableList>() {

	            @Override
	            public int compare(TableList e1, TableList e2) {
	                return e1.getFirstName().compareTo(e2.getFirstName());
	            }
	        };
	        
	        
	        public static Comparator<TableList> SalaryComparator = new Comparator<TableList>() {

	            @Override
	            public int compare(TableList e1, TableList e2) {
	                return e1.getSalary().compareTo(e2.getSalary());
	            }
	        };

	    @Override
	    public int compareTo(TableList o) {
	        // TODO Auto-generated method stub
	        return 0;
	    }
		@Override
		public String toString() {
			return "TableList [lastName=" + lastName + ", firstName="
					+ firstName + ", email=" + email + ", salary=" + salary
					+ ", url=" + url + ", link=" + link + "]\n";
		}
		
				
	    
}
