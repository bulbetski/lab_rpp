package com.example.laba_2;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
@JsonPropertyOrder(
        {
                "graphic",
                "name",
                "helptext"
        }
)
public class Item {
    @JsonProperty("graphic")
    private String graphic;

    @JsonProperty("name")
    private String  name;

    @JsonProperty("helptext")
    private String helptext = "";

    public Item(String graphic, String name, String helptext) {
        this.graphic = graphic;
        this.name = name;
        this.helptext = helptext;
    }

    public Item() {
        this.graphic = null;
        this.name = null;
        this.helptext = "";
    }

    @JsonProperty("graphic")
    public String getGraphic() {
        return graphic;
    }

    @JsonProperty("graphic")
    public void setGraphic( String graphic) {
        this.graphic = graphic;
    }


    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("helptext")
    public String getHelptext() {
        return helptext;
    }

    @JsonProperty("helptext")
    public void setHelptext(String helptext) {
        this.helptext = helptext;
    }
}
