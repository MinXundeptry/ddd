/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Admin
 */
public class ProviderModel {
    private int providerId;
    private String providerName;
    private String contactName;
    private String phone;
    private String email;
    private String address;

    public ProviderModel() {
    }

    public ProviderModel(int providerId, String providerName, String contactName, String phone, String email, String address) {
        this.providerId = providerId;
        this.providerName = providerName;
        this.contactName = contactName;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public int getProviderId() { return providerId; }
    public void setProviderId(int providerId) { this.providerId = providerId; }

    public String getProviderName() { return providerName; }
    public void setProviderName(String providerName) { this.providerName = providerName; }

    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
