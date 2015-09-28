package de.sturmm.pcollect.model;

import com.datastax.driver.mapping.annotations.UDT;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by sturmm on 18.09.15.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@UDT(name = "person_name")
public class Name {

    private String salutation;
    private String fname;
    private String lname;

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }
}
