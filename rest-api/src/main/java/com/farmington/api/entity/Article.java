package com.farmington.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String topic;
    private String category;
    @Lob
    @Column
    private String description;
    private String channel;
    /*
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    /*
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
*/
    /*
    @Override
    public String toString() {
        return "Product [id=" + id + ", topc=" + topic + ", description="
                + description + ", channel=" + image + ", image="
                + image + "]";
    }*/
}
