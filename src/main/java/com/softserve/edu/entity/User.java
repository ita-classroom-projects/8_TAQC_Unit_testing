package com.softserve.edu.entity;

public class User {
    private long id;
    private String firstName;
    private String lastName;
    private String companyName;
    private String roleName;

    public User(String firstName, String lastName, String companyName, String roleName) {
        this.id = -1;
        this.firstName = firstName;
        this.lastName = lastName;
        this.companyName = companyName;
        this.roleName = roleName;
    }

    public User setId(long id) {
        this.id = id;
        return this;
    }
    
    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getRoleName() {
        return roleName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        // Code
//        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((firstName == null) ? 0 : firstName.toLowerCase().hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.toLowerCase().hashCode());
        result = prime * result + ((companyName == null) ? 0 : companyName.toLowerCase().hashCode());
        result = prime * result + ((roleName == null) ? 0 : roleName.toLowerCase().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }
        User other = (User) obj;
        boolean result = true;
        // Code
        if (((firstName == null) && (other.firstName != null))
            || ((firstName != null) && (other.firstName == null))
            || ((lastName == null) && (other.lastName != null))
            || ((lastName != null) && (other.lastName == null))
            || ((companyName == null) && (other.companyName != null))
            || ((companyName != null) && (other.companyName == null))
            || ((roleName == null) && (other.roleName != null))
            || ((roleName != null) && (other.roleName == null))) {
            return false;
        }
        //boolean result = true;
        if (result && (firstName != null)) {
            result = result && firstName.equalsIgnoreCase(other.firstName);
        }
        if (result && (lastName != null)) {
            result = result && lastName.equalsIgnoreCase(other.lastName);
        }
        if (result && (companyName != null)) {
            result = result && companyName.equalsIgnoreCase(other.companyName);
        }
        if (result && (roleName != null)) {
            result = result && roleName.equalsIgnoreCase(other.roleName);
        }
        return result;
    }

    @Override
    public String toString() {
        return "\nUser [id=" + id 
                + ", firstName=" + firstName 
                + ", lastName=" + lastName 
                + ", companyName=" + companyName
                + ", roleName=" + roleName + "]";
    }

}
