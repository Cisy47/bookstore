package security;

import java.security.Permission;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 47 on 2017/3/19.
 */
public class BookCategoryPermission extends Permission {
    private String category;

    public BookCategoryPermission(String target, String anAction)
    {
        super(target);
        this.category=anAction;
    }

    @Override
    public String getActions(){return category;}
    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == null) return false;
        if (!getClass().equals(other.getClass())) return false;
        BookCategoryPermission b = (BookCategoryPermission) other;
        if(b.getName().equals(getName())){
            return (b.getActions().equals(category));
        }
        else return false;
    }

    @Override
    public int hashCode()
    {
        return getName().hashCode() + category.hashCode();
    }
    @Override
    public boolean implies(Permission other) {
        if (!(other instanceof BookCategoryPermission)) return false;
        BookCategoryPermission b = (BookCategoryPermission) other;
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
