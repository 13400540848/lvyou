package com.bluesimon.wbf.modules.file;

/**
 * Created by Zz on 2021/3/21.
 */
public class UploadResp {
    private String name;

    private String originalName;

    private Long size;

    private String state;

    private String type;

    private String url;

    public UploadResp(String name,
                      String originalName,
                      Long size,
                      String state,
                      String type,
                      String url) {
        this.name = name;
        this.originalName = originalName;
        this.size = size;
        this.state = state;
        this.type = type;
        this.url = url;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
