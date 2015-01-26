package com.maina.formdata.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

/**
 * Created by Patrick on 10/16/2014.
 */
@DatabaseTable
public class ImagePath extends DBaseE {
    public ImagePath() {
    }

    public ImagePath(UUID id, String imagePath, String imageType, boolean sent, UUID resultId, UUID resultItemId) {
        super(id);
        ImagePath = imagePath;
        ImageType = imageType;
        Sent = sent;
        ResultId = resultId;
        ResultItemId = resultItemId;
    }

    @DatabaseField
    private String ImagePath;
    @DatabaseField
    private String ImageType;
    @DatabaseField
    private boolean Sent;
    @DatabaseField
    private UUID ResultId;
    @DatabaseField
    private UUID ResultItemId;

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getImageType() {
        return ImageType;
    }

    public void setImageType(String imageType) {
        ImageType = imageType;
    }

    public boolean isSent() {
        return Sent;
    }

    public void setSent(boolean sent) {
        Sent = sent;
    }

    public UUID getResultId() {
        return ResultId;
    }

    public void setResultId(UUID resultId) {
        ResultId = resultId;
    }

    public UUID getResultItemId() {
        return ResultItemId;
    }

    public void setResultItemId(UUID resultItemId) {
        ResultItemId = resultItemId;
    }
}
