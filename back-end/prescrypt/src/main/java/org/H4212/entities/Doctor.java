package org.H4212.entities;


import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Doctor extends Person {

    @Column(name = "IdPSdoctor")
    @NotNull
    private Long idPSdoctor;

    @Column(name="Qualification")
    @NotNull
    private String qualification;

    @Column(name="Office Address")
    @NotNull
    private String officeAddress;

    @Column(name="Telephone Number")
    @NotNull
    private String telephone;

    @Column(name = "Ethereum Address")
    private String ethAddress;

    public Doctor(long id, String lastName, String firstName, String pseudo, String password, Long idPSdoctor, String qualification, String officeAddress, String telephone) {
        super(id, lastName, firstName, pseudo, password);
        this.idPSdoctor = idPSdoctor;
        this.qualification = qualification;
        this.officeAddress = officeAddress;
        this.telephone = telephone;
    }

    public Doctor(String lastName, String firstName, String pseudo, String password, Long idPSdoctor, String qualification, String officeAddress, String telephone) {
        super(lastName, firstName, pseudo, password);
        this.idPSdoctor = idPSdoctor;
        this.qualification = qualification;
        this.officeAddress = officeAddress;
        this.telephone = telephone;
    }

    public Doctor(long id, String lastName, String firstName, Long idPSdoctor, String qualification, String officeAddress, String telephone)
    {
        super(id, lastName, firstName);
        this.idPSdoctor = idPSdoctor;
        this.qualification = qualification;
        this.officeAddress = officeAddress;
        this.telephone = telephone;
    }

    public Doctor() {

    }

    public Long getIdPSdoctor() {
        return idPSdoctor;
    }

    public void setIdPSdoctor(Long idPSdoctor) {
        this.idPSdoctor = idPSdoctor;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEthAddress() {
        return ethAddress;
    }

    public void setEthAddress(String ethAddress) {
        this.ethAddress = ethAddress;
    }

    @Override
    public JsonObjectBuilder toJsonBuilder() {
        try{
            return super.toJsonBuilder().add("idPSdoctor",idPSdoctor)
                    .add("qualification", qualification)
                    .add("officeAddress", officeAddress)
                    .add("telephone", telephone)
                    .add("ethAddress", ethAddress);
        }catch(Exception e){
            return super.toJsonBuilder().add("idPSdoctor",idPSdoctor)
                    .add("qualification", qualification)
                    .add("officeAddress", officeAddress)
                    .add("telephone", telephone)
                    .add("ethAddress", "NA");
        }
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "idPSdoctor=" + idPSdoctor +
                ", qualification='" + qualification + '\'' +
                ", officeAddress='" + officeAddress + '\'' +
                ", telephone='" + telephone + '\'' +
                "} " + super.toString();
    }
}
