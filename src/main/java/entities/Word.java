package entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@DatabaseTable(tableName = "word")
public class Word
{
    @DatabaseField(id=true)
    private Integer id;

    @DatabaseField
    private String text;

    @DatabaseField()
    private String explanation;

    @DatabaseField()
    private String example;

    @DatabaseField()
    private Integer familiarity = 0;

    private String shortExplanation;
    private String shortExample;

    @DatabaseField(canBeNull = false, foreignAutoRefresh = true, foreign = true)
    private Category category;

    @DatabaseField(canBeNull = false, foreignAutoRefresh = true, foreign = true)
    private Status status;

    private StringProperty explanationProperty = new SimpleStringProperty(this, "explanation", "");
    private StringProperty exampleProperty = new SimpleStringProperty(this, "example", "");
    private StringProperty textProperty = new SimpleStringProperty(this, "text", "");
    private StringProperty statusProperty = new SimpleStringProperty(this, "status", "New");
    private StudyResult studied = StudyResult.NEW;

    public Word() {
    }

    public Word(String text, String explanation) {
        this.text = text;
        this.explanation = explanation;
    }

    public StudyResult getStudied()
    {
        return studied;
    }

    public void setStudied(StudyResult studied)
    {
        this.studied = studied;
    }

    public Category getCategory()
    {
        return category;
    }

    public void setCategory(Category category)
    {
        this.category = category;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
        this.textProperty.set(text);
    }

    public String getExplanation()
    {
        return explanation;
    }

    public String getExample()
    {
        return example;
    }

    public void setExplanation(String explanation)
    {
        this.explanation = explanation;
        this.explanationProperty.set(explanation);
    }

    public void setExample(String example)
    {
        this.example = example;
        this.exampleProperty.set(example);
    }

    public String getTextProperty()
    {
        return textProperty.get();
    }

    public StringProperty textPropertyProperty()
    {
        textProperty.set(text);
        return textProperty;
    }

    public StringProperty explanationProperty()
    {
        explanationProperty.set(explanation);
        return explanationProperty;
    }

    public StringProperty getExampleProperty()
    {
        exampleProperty.set(example);
        return exampleProperty;
    }

    public void setTextProperty(String textProperty)
    {
        this.textProperty.set(textProperty);
    }



    public String getStatusProperty()
    {
        return statusProperty.get();
    }

    public StringProperty statusPropertyProperty()
    {
        return statusProperty;
    }

    public void setStatusProperty(String statusProperty)
    {
        this.statusProperty.set(statusProperty);
    }

    public String getShortExplanation() {
        if(null == explanation ) return "";
        String[] elements =  explanation.trim().split("\\n");
        return elements.length>0 ? elements[0] : "";
    }

    public String getShortExample() {
        if(null == example ) return "";
        String[] elements =  example.trim().split("\\n");
        return elements.length>0 ? elements[0] : "";
    }

    public Status getStatus()
    {
        return status;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }

    public Integer getFamiliarity()
    {
        return familiarity;
    }

    public void setFamiliarity(Integer familiarity)
    {
        this.familiarity = familiarity;
    }

    public void increaseFamiliarity()
    {
        if (familiarity < 5) {
            familiarity++;
        }
    }

    public void decreaseFamiliarity()
    {
        if (familiarity > 0) {
            familiarity--;
        }
    }

    public void syncProperties()
    {
        setText(textProperty.get());
        setExplanation(explanationProperty.get());
        setExample(exampleProperty.get());
    }
}
