package adinformer;

/**
 * @author zhenya mogsev@gmail.com
 */
public class AdEnum {
    
    enum listMemberAttribute { 
        sAMAccountName, 
        name, 
        mail, 
        title,
        description,
        department,
        telephoneNumber,
        ipPhone,
        mobile,
        company;
        
        public static String[] getAttributeArray() {
            String[] result = new String[listMemberAttribute.values().length];
            int i = 0;
            for (listMemberAttribute list:listMemberAttribute.values()) {
                result[i] = list.name();
                i++;
            }
            return result;
        }        
    }
    
    enum listHostAttribute extend listMemberAttribute {
        
    }
}
