package pl.edu.agh.student.olemi.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Product.class,
        parentColumns = "name",
        childColumns = "parentProductId",
        onDelete = CASCADE))
public class Ingredient {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String parentProductId;

    private Double partOfTotalWeight;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Double getPartOfTotalWeight() {
        return partOfTotalWeight;
    }

    public void setPartOfTotalWeight(Double partOfTotalWeight) {
        this.partOfTotalWeight = partOfTotalWeight;
    }

    public String getParentProductId() {
        return parentProductId;
    }

    public void setParentProductId(String parentProductId) {
        this.parentProductId = parentProductId;
    }
}
