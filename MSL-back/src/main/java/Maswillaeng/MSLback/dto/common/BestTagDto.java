package Maswillaeng.MSLback.dto.common;

import lombok.Getter;

@Getter
public class BestTagDto {

    private String TagName;

    private Long count;

    public BestTagDto(String tagName, Long count) {
        TagName = tagName;
        this.count = count;
    }
}
