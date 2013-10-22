package codegen.main.business.model;

public class Component {

   private String type;

   private Boolean required;

   private String format;

   public String getType() {
      return type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public Boolean getRequired() {
      return required;
   }

   public void setRequired(Boolean required) {
      this.required = required;
   }

   public String getFormat() {
      return format;
   }

   public void setFormat(String format) {
      this.format = format;
   }

}
