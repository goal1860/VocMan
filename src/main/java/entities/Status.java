package entities;

import com.j256.ormlite.field.DatabaseField;

public class Status
{
    public static final int NEW = 1;
    public static final int LEARNING = 2;
    public static final int LEARNED = 3;

    @DatabaseField(id = true)
    private Integer id;

    @DatabaseField
    private String name;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
