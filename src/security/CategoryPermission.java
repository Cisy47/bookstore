package security;

import java.security.Permission;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by piglet on 2017/3/19.
 */
public class CategoryPermission extends Permission {
    private String category;

    @Override
    public String getActions() {
        return category;
    }

    public CategoryPermission(String roleid, String category){
        super(roleid);
        this.category=category;
    }
    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        return getName().hashCode() + category.hashCode();


    }

    @Override
    public boolean implies(Permission other) {
        if (!(other instanceof CategoryPermission)) return false;
        CategoryPermission b = (CategoryPermission) other;
        if (b.getName().equals("2"))
        {
            return true;
        }
        for(String ct:b.categorySet()){
            if(categorySet().contains(ct)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (!getClass().equals(other.getClass()))
            return false;
        CategoryPermission b = (CategoryPermission) other;
        if (category.equals(b.getActions()) && getName().equals(b.getName()))
            return true;
        else
            return false;
    }

    public Set<String> categorySet()
    {
        Set<String> set = new HashSet<String>();
        set.add(category);
        if(category.equals("teaching")){
            set.add("java");
            set.add("finance");
            set.add("accounting");
        }else if(category.equals("literary")){
            set.add("fiction");
            set.add("poetry");
        }
        return set;
    }


}
