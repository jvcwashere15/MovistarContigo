package pe.com.qallarix.movistarcontigo.specials.pojos;

import java.io.Serializable;
import java.util.List;

public class SpecialBenefits implements Serializable {
     private long id;
     private String icon;
     private String title;
     private String image;
     private String description;
     private String hiperlink;
     private List<ItemList> itemList;
     private String additionalInformation;
     private String howToUse;
     private String contactPhone;
     private String contactPhoneAnexo;
     private String contactMobile;
     private String contactEmail;




     public long getId() {
          return id;
     }

     public void setId(long id) {
          this.id = id;
     }

     public String getIcon() {
          return icon;
     }

     public void setIcon(String icon) {
          this.icon = icon;
     }

     public String getTitle() {
          return title;
     }

     public void setTitle(String title) {
          this.title = title;
     }

     public String getImage() {
          return image;
     }

     public void setImage(String image) {
          this.image = image;
     }

     public String getDescription() {
          return description;
     }

     public void setDescription(String description) {
          this.description = description;
     }

     public List<ItemList> getItemList() {
          return itemList;
     }

     public void setItemList(List<ItemList> itemList) {
          this.itemList = itemList;
     }

     public String getAdditionalInformation() {
          return additionalInformation;
     }

     public void setAdditionalInformation(String additionalInformation) {
          this.additionalInformation = additionalInformation;
     }

     public String getHowToUse() {
          return howToUse;
     }

     public void setHowToUse(String howToUse) {
          this.howToUse = howToUse;
     }

     public String getContactPhone() {
          return contactPhone;
     }

     public void setContactPhone(String contactPhone) {
          this.contactPhone = contactPhone;
     }

     public String getContactMobile() {
          return contactMobile;
     }

     public void setContactMobile(String contactMobile) {
          this.contactMobile = contactMobile;
     }

     public String getContactEmail() {
          return contactEmail;
     }

     public void setContactEmail(String contactEmail) {
          this.contactEmail = contactEmail;
     }

     public String getContactPhoneAnexo() {
          return contactPhoneAnexo;
     }

     public void setContactPhoneAnexo(String contactPhoneAnexo) {
          this.contactPhoneAnexo = contactPhoneAnexo;
     }

     public String getHiperlink() {
          return hiperlink;
     }

     public void setHiperlink(String hiperlink) {
          this.hiperlink = hiperlink;
     }
}
