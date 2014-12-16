package adinformer;

import java.util.Comparator;

/**
 * @author zhenya mogsev@gmail.com
 */
public class SortMember implements Comparator<AdMember> {

    private AdMember.listAttribute nameAttribute;

    /**
     * Set default value listAttribute sort - name
     */
    public SortMember() {
        nameAttribute = AdMember.listAttribute.name;
    }

    /**
     * Set value listAttribute sort
     *
     * @param nameAttribute
     */
    public SortMember(AdMember.listAttribute nameAttribute) {
        this.nameAttribute = nameAttribute;
    }

    @Override
    public int compare(AdMember o1, AdMember o2) {
        switch (nameAttribute) {
            case title:
                return o1.getTitle().compareTo(o2.getTitle());
            case department:
                return o1.getDepartment().compareTo(o2.getDepartment());
            case sAMAccountName:
                return o1.getsAMAccountName().compareTo(o2.getsAMAccountName());
        }
        return o1.getName().compareTo(o2.getName());
    }
}
