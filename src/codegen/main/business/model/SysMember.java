package codegen.main.business.model;

public class SysMember {

   public static final String MEMBER_TYPE_USER = "User";

   public static final String MEMBER_TYPE_PARTY = "Party";

   public static final String MEMBER_TYPE_GROUP = "Group";

   private String memberType;

   private String involvement;

   public String getMemberType() {
      return memberType;
   }

   public void setMemberType(String memberType) {
      this.memberType = memberType;
   }

   public String getInvolvement() {
      return involvement;
   }

   public void setInvolvement(String involvement) {
      this.involvement = involvement;
   }

}
